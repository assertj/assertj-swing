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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.AbstractButton;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Returns the text of a Swing {@code AbstractButton}.
 * 
 * @see AbstractButton#getText()
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class AbstractButtonTextQuery {
  /**
   * Returns the text of the given Swing {@code AbstractButton}.
   * 
   * @param button the given {@code AbstractButton}.
   * @return the text of the given Swing {@code AbstractButton}.
   */
  @RunsInEDT
  public static @Nullable String textOf(final @Nonnull AbstractButton button) {
    return execute(() -> button.getText());
  }

  private AbstractButtonTextQuery() {
  }
}