/*
 * Created on Jul 29, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.text;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Reads the text of an AWT or Swing {@code Component}.
 *
 * @param <T> the type of {@code Component} this reader supports.
 *
 * @author Alex Ruiz
 *
 * @since 1.3
 */
public abstract class TextReader<T extends Component> {
  /**
   * @return the type of AWT or Swing {@code Component} this reader supports.
   */
  public abstract @Nonnull Class<T> supportedComponent();

  /**
   * Indicates whether the given AWT or Swing {@code Component} contains or displays the given text.
   *
   * @param c the given {@code Component}.
   * @param text the given text.
   * @return {@code true} if the given {@code Component} contains or displays the given text; {@code false} otherwise.
   * @throws IllegalArgumentException if this reader does not support the type of the given {@code Component} (e.g. this
   *           reader supports {@code JButton}s and a {@code JLabel} is passed.)
   * @see #supportedComponent()
   */
  @RunsInCurrentThread
  public final boolean containsText(@Nonnull Component c, @Nonnull String text) {
    checkCorrectType(c);
    T casted = supportedComponent().cast(c);
    return checkContainsText(casted, text);
  }

  private void checkCorrectType(Component c) {
    Class<T> type = supportedComponent();
    if (!type.isAssignableFrom(c.getClass())) {
      String msg = String.format("Expecting component of type %s but got %s", type.getName(), c.getClass().getName());
      throw new IllegalArgumentException(msg);
    }
  }

  /**
   * Indicates whether the given AWT or Swing {@code Component} contains or displays the given text. Implementations
   * must ensure that they are executed in the current thread.
   *
   * @param component the given {@code Component}.
   * @param text the given text.
   * @return {@code true} if the given {@code Component} contains or displays the given text; {@code false} otherwise.
   */
  @RunsInCurrentThread
  protected abstract boolean checkContainsText(@Nonnull T component, @Nonnull String text);
}
