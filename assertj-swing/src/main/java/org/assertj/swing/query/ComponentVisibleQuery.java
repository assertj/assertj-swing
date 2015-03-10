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
package org.assertj.swing.query;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Indicates whether an AWT or Swing {@code Component} is visible. This query is executed in the event dispatch thread
 * (EDT.)
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ComponentVisibleQuery {
  /**
   * Indicates whether the given AWT or Swing {@code Component} is visible or not. This query is executed in the event
   * dispatch thread (EDT.)
   * 
   * @param component the given {@code Component}.
   * @return {@code true} if the given {@code Component} is visible, {@code false} otherwise.
   * @see Component#isVisible()
   */
  @RunsInEDT
  public static boolean isVisible(final @Nonnull Component component) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected @Nullable Boolean executeInEDT() {
        return component.isVisible();
      }
    });
    return checkNotNull(result);
  }

  private ComponentVisibleQuery() {
  }
}