/*
 * Created on Mar 10, 2010
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
package org.fest.swing.driver;

import static java.util.Arrays.sort;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.driver.JTreeMatchingPathQuery.matchingPathWithRootIfInvisible;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.format;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;

/**
 * Verifies that a {@code JTree} has the expected selection.
 *
 * @author Alex Ruiz
 *
 * @since 1.2
 */
final class JTreeVerifySelectionTask {
  @RunsInEDT
  static void checkHasSelection(final @Nonnull JTree tree, final @Nonnull int[] selection,
      final @Nonnull Description errMsg) {
    execute(new GuiTask() {
      @Override protected void executeInEDT() {
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
    throw failNotEqualSelection(errMsg, selection, selectionRows);
  }

  private static @Nonnull AssertionError failNotEqualSelection(@Nonnull Description errMsg, @Nonnull int[] expected,
      @Nonnull int[] actual) {
    String format = "[%s] expecting selection:<%s> but was:<%s>";
    String msg = String.format(format, errMsg.value(), format(expected), format(actual));
    throw fail(msg);
  }

  @RunsInEDT
  static void checkHasSelection(final @Nonnull JTree tree, final @Nonnull String[] selection,
      final @Nonnull JTreePathFinder pathFinder, final @Nonnull Description errMsg) {
    execute(new GuiTask() {
      @Override protected void executeInEDT() {
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
      throw failNotEqualSelection(errMsg, selection, selectionPaths);
    }
    for (int i = 0; i < selectionCount; i++) {
      TreePath expected = matchingPathWithRootIfInvisible(tree, checkNotNull(selection[i]), pathFinder);
      TreePath actual = selectionPaths[i];
      if (!areEqual(expected, actual)) {
        throw failNotEqualSelection(errMsg, selection, selectionPaths);
      }
    }
  }

  private static @Nonnull AssertionError failNotEqualSelection(@Nonnull Description errMsg, @Nonnull String[] expected,
      @Nonnull TreePath[] actual) {
    String format = "[%s] expecting selection:<%s> but was:<%s>";
    String msg = String.format(format, errMsg.value(), format(expected), format(actual));
    throw fail(msg);
  }

  private static void failNoSelection(final @Nonnull Description errMessage) {
    fail(String.format("[%s] No selection", errMessage.value()));
  }


  @RunsInEDT
  static void checkNoSelection(final @Nonnull JTree tree, final @Nonnull Description errMsg) {
    execute(new GuiTask() {
      @Override protected void executeInEDT() {
        if (tree.getSelectionCount() == 0) {
          return;
        }
        String format = "[%s] expected no selection but was:<%s>";
        String message = String.format(format, errMsg.value(), format(tree.getSelectionPaths()));
        fail(message);
      }
    });
  }

  private JTreeVerifySelectionTask() {}
}
