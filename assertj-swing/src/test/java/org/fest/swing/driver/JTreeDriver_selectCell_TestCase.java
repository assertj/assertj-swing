/*
 * Created on Jul 16, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.JTreeSelectRowTask.selectRow;
import static org.fest.util.Arrays.array;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;

/**
 * Test case for {@link JTreeDriver} that involves selecting cells in a {@code JTree}.
 * 
 * @author Alex Ruiz
 */
public abstract class JTreeDriver_selectCell_TestCase extends JTreeDriver_TestCase {
  @RunsInEDT
  final void requireNoSelection() {
    assertThat(selectedRowsOf(tree)).isNullOrEmpty();
  }

  @RunsInEDT
  final void requireSelectedRows(int... rows) {
    assertThat(selectedRowsOf(tree)).isEqualTo(rows);
  }

  @RunsInEDT
  private static int[] selectedRowsOf(final JTree tree) {
    return execute(new GuiQuery<int[]>() {
      @Override
      protected int[] executeInEDT() {
        return tree.getSelectionRows();
      }
    });
  }

  @RunsInEDT
  final void clearTreeSelection() {
    clearSelectionOf(tree);
    robot.waitForIdle();
    requireNoSelection();
  }

  @RunsInEDT
  private static void clearSelectionOf(final JTree tree) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        tree.clearSelection();
      }
    });
  }

  @RunsInEDT
  final void updateTreeWithDefaultSelectionModel() {
    setDefaultSelectionModelTo(tree);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setDefaultSelectionModelTo(final JTree tree) {
    final DefaultTreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        tree.setSelectionModel(selectionModel);
      }
    });
  }

  @RunsInEDT
  final void requireSelectedPaths(String... paths) {
    TreePath[] actualSelection = selectionPathsOf(tree);
    assertThat(actualSelection).hasSize(paths.length);
    for (int i = 0; i < actualSelection.length; i++) {
      assertThat(textOf(actualSelection[i])).isEqualTo(paths[i]);
    }
  }

  @RunsInEDT
  private static TreePath[] selectionPathsOf(final JTree tree) {
    return execute(new GuiQuery<TreePath[]>() {
      @Override
      protected TreePath[] executeInEDT() {
        return tree.getSelectionPaths();
      }
    });
  }

  @RunsInEDT
  final void selectFirstChildOfRoot() {
    DefaultMutableTreeNode root = rootOf(tree);
    select(new TreePath(array(root, firstChildOf(root))));
  }

  @RunsInEDT
  final void select(TreePath path) {
    setSelectionPath(tree, path);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setSelectionPath(final JTree tree, final TreePath path) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        tree.expandPath(path);
        tree.setSelectionPath(path);
      }
    });
  }

  @RunsInEDT
  final void select(TreePath... paths) {
    setSelectionPath(tree, paths);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setSelectionPath(final JTree tree, final TreePath[] paths) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        tree.setSelectionPaths(paths);
      }
    });
  }

  @RunsInEDT
  final void select(int row) {
    selectRow(tree, row);
    robot.waitForIdle();
  }
}
