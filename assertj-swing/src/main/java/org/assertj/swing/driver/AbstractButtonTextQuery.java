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
package org.assertj.swing.driver;

import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.AbstractButton;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Returns the text of a Swing {@code AbstractButton}. This query is executed in the event dispatch thread (EDT).
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class AbstractButtonTextQuery {
  @RunsInEDT
  static @Nonnull String textOf(final @Nonnull AbstractButton button) {
    String result = execute(() -> button.getText());
    return result == null ? "" : result;
  }

  private AbstractButtonTextQuery() {
  }
}