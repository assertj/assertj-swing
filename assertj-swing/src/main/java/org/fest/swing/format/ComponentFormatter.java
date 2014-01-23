/*
 * Created on Dec 22, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.format;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Returns a {@code String} representation of a given AWT or Swing {@code Component}.
 *
 * @author Alex Ruiz
 */
public interface ComponentFormatter {
  /**
   * Returns a {@code String} representation of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return a {@code String} representation of the given {@code Component}.
   */
  @RunsInCurrentThread
  @Nonnull String format(@Nonnull Component c);

  /**
   * Returns the type of AWT or Swing {@code Component} this formatter supports.
   *
   * @return the type of {@code Component} this formatter supports.
   */
  @Nonnull Class<? extends Component> targetType();
}
