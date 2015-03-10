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

import static java.util.Arrays.sort;
import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.driver.JTreeMatchingPathQuery.matchingPathWithRootIfInvisible;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.util.Arrays.format;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.assertj.core.description.Description;
import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;

/**
 * Verifies that a {@code JTree} has the expected selection.
 * 
 * @author Alex Ruiz
 */
final class JTreeVerifySelectionTask {
  @RunsInEDT
  static void checkHasSelection(final @Nonnull JTree tree, final @Nonnull int[] selection,
      final @Nonnull Description errMsg) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkSelection(tree, selection, errMsg);
      }
    });
  }

  @RunsInCurrentThread
  private static void checkSelection(@Nonnull JTree tree, @Nonnull int[] selection, @Nonnull Description errMsg) {
    int[] selectionRows = tree.getSelectionRows();
    if (selectionRows == null || selectionRows.length == 0) {
      failNoSelection(errMsg);
      return;
    }
    sort(selection);
    if (Arrays.equals(selectionRows, selection)) {
      return;
    }
    failNotEqualSelection(errMsg, selection, selectionRows);
  }

  private static void failNotEqualSelection(@Nonnull Description errMsg, @Nonnull int[] expected, @Nonnull int[] actual) {
    String format = "[%s] expecting selection:<%s> but was:<%s>";
    String msg = String.format(format, errMsg.value(), format(expected), format(actual));
    fail(msg);
  }

  @RunsInEDT
  static void checkHasSelection(final @Nonnull JTree tree, final @Nonnull String[] selection,
      final @Nonnull JTreePathFinder pathFinder, final @Nonnull Description errMsg) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkSelection(tree, selection, pathFinder, errMsg);
      }
    });
  }

  @RunsInCurrentThread
  private static void checkSelection(@Nonnull JTree tree, @Nonnull String[] selection,
      @Nonnull JTreePathFinder pathFinder, @Nonnull Description errMsg) {
    TreePath[] selectionPaths = tree.getSelectionPaths();
    if (selectionPaths == null || selectionPaths.length == 0) {
      failNoSelection(errMsg);
      return;
    }
    int selectionCount = selection.length;
    if (selectionCount != selectionPaths.length) {
      failNotEqualSelection(errMsg, selection, selectionPaths);
    }
    for (int i = 0; i < selectionCount; i++) {
      TreePath expected = matchingPathWithRootIfInvisible(tree, checkNotNull(selection[i]), pathFinder);
      TreePath actual = selectionPaths[i];
      if (!areEqual(expected, actual)) {
        failNotEqualSelection(errMsg, selection, selectionPaths);
      }
    }
  }

  private static void failNotEqualSelection(@Nonnull Description errMsg, @Nonnull String[] expected,
      @Nonnull TreePath[] actual) {
    String format = "[%s] expecting selection:<%s> but was:<%s>";
    String msg = String.format(format, errMsg.value(), format(expected), format(actual));
    fail(msg);
  }

  private static void failNoSelection(final @Nonnull Description errMessage) {
    fail(String.format("[%s] No selection", errMessage.value()));
  }

  @RunsInEDT
  static void checkNoSelection(final @Nonnull JTree tree, final @Nonnull Description errMsg) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (tree.getSelectionCount() == 0) {
          return;
        }
        String format = "[%s] expected no selection but was:<%s>";
        String message = String.format(format, errMsg.value(), format(tree.getSelectionPaths()));
        fail(message);
      }
    });
  }

  private JTreeVerifySelectionTask() {
  }
}
