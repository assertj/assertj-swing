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
package org.assertj.swing.hierarchy;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Container;

import javax.annotation.Nonnull;
import javax.swing.JFrame;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Returns the content pane object of a given {@code JFrame}. This query is executed in the event dispatch thread (EDT).
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunsInEDT
final class JFrameContentPaneQuery {
  static @Nonnull Container contentPaneOf(final @Nonnull JFrame frame) {
    Container result = execute(() -> frame.getContentPane());
    return checkNotNull(result);
  }

  private JFrameContentPaneQuery() {
  }
}