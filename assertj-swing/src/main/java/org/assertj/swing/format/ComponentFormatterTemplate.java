/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.format;

import static org.assertj.core.util.Preconditions.checkNotNull;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.assertj.swing.annotation.RunsInCurrentThread;

/**
 * Template for implementations of {@link ComponentFormatter}.
 *
 * @author Yvonne Wang
 */
public abstract class ComponentFormatterTemplate implements ComponentFormatter {
  /**
   * Returns the {@code String} representation of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code Component}.
   * @throws NullPointerException if the given {@code Component} is {@code null}.
   * @throws IllegalArgumentException if the type of the given {@code Component} is not supported by this formatter.
   */
  @RunsInCurrentThread
  @Override
  public final @Nonnull String format(@Nonnull Component c) {
    checkTypeOf(c);
    return doFormat(c);
  }

  /**
   * Returns the {@code String} representation of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code Component}.
   */
  @RunsInCurrentThread
  protected abstract @Nonnull String doFormat(@Nonnull Component c);

  private void checkTypeOf(@Nonnull Component c) {
    checkNotNull(c);
    if (!targetType().isAssignableFrom(c.getClass())) {
      String msg = String.format("This formatter only supports components of type %s", targetType().getName());
      throw new IllegalArgumentException(msg);
    }
  }

  protected String getRealClassName(Component c) {
    return getRealClassName(c.getClass());
  }

  private String getRealClassName(Class<?> type) {
    if (type.isAnonymousClass()) {
      return type.getName() + "(" + getRealClassName(type.getSuperclass()) + ")";
    }
    return type.getName();
  }
}
