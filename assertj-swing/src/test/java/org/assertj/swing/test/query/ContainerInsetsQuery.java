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
package org.assertj.swing.test.query;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Container;
import java.awt.Insets;

import javax.annotation.Nonnull;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Returns the insets of a {@code Container}. This query is executed in the event dispatch thread (EDT).
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class ContainerInsetsQuery {
  /**
   * Returns the insets of the given {@code Container}. This query is executed in the event dispatch thread (EDT).
   * 
   * @param container the given {@code Container}.
   * @return the insets of the given {@code Container}.
   * @see Container#getInsets()
   */
  @RunsInEDT
  public static @Nonnull Insets insetsOf(final @Nonnull Container container) {
    Insets result = execute(() -> container.getInsets());
    return checkNotNull(result);
  }

  private ContainerInsetsQuery() {
  }
}