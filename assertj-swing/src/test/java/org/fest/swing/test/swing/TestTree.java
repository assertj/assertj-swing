/*
 * Created on Aug 26, 2007
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
package org.fest.swing.test.swing;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * <p>
 * {@code JTree} that requires a name and supports drag and drop.
 * </p>
 *
 * <p>
 * Adapted from the tutorial <a href="http://java.sun.com/docs/books/tutorial/uiswing/dnd/intro.html"
 * target="_blank">Introduction to Drag and Drop and Data Transfer</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public final class TestTree extends JTree {
  public TestTree(String name) {
    this(name, null);
  }

  public TestTree(TreeModel model) {
    this(null, model);
  }

  public TestTree(String name, TreeModel model) {
    setDragEnabled(true);
    setModel(model);
    setName(name);
    setTransferHandler(new TreeTransferHandler());
  }

  private static class TreeTransferHandler extends StringTransferHandler<JTree> {
    TreeTransferHandler() {
      super(JTree.class);
    }

    @Override
    protected String exportString(JTree tree) {
      rows = tree.getSelectionRows();
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < rows.length; i++) {
        TreePath path = tree.getPathForRow(rows[i]);
        Object val = path.getLastPathComponent();
        b.append(val == null ? "" : val.toString());
        if (i != rows.length - 1) {
          b.append("\n");
        }
      }
      return b.toString();
    }

    @Override
    protected void importString(JTree target, String s) {
      DefaultTreeModel model = (DefaultTreeModel) target.getModel();
      int index = target.getRowForPath(target.getSelectionPath());
      // Prevent the user from dropping data back on itself.
      if (rows != null && index >= rows[0] - 1 && index <= rows[rows.length - 1]) {
        rows = null;
        return;
      }
      int max = target.getRowCount();
      if (index < 0) {
        index = max;
      } else if (index > max) {
        index = max;
      }
      addIndex = index;
      String[] values = s.split("\n");
      addCount = values.length;
      for (String value : values) {
        TreePath path = target.getPathForRow(index++);
        model.insertNodeInto(new DefaultMutableTreeNode(value), (MutableTreeNode) path.getLastPathComponent(), 0);
      }
    }

    // not working perfectly right, but good enough for testing.
    @Override
    protected void cleanup(JTree source, boolean remove) {
      if (remove && rows != null) {
        DefaultTreeModel model = (DefaultTreeModel) source.getModel();
        // If we are moving items around in the same table, we need to adjust the rows accordingly, since those after
        // the
        // insertion point have moved.
        if (addCount > 0) {
          for (int i = 0; i < rows.length; i++) {
            if (rows[i] > addIndex) {
              rows[i] += addCount;
            }
          }
        }
        for (int i = rows.length - 1; i >= 0; i--) {
          TreePath path = source.getPathForRow(rows[i]);
          if (path == null) {
            continue;
          }
          model.removeNodeFromParent((MutableTreeNode) path.getLastPathComponent());
        }
      }
    }
  }
}
