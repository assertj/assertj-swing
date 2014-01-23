/*
 * Created on Aug 28, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.test.builder;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.isNullOrEmpty;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JTree}s.
 *
 * @author Alex Ruiz
 */
public final class JTrees {
  private JTrees() {}

  public static JTreeFactory tree() {
    return new JTreeFactory();
  }

  public static class JTreeFactory {
    String name;
    TreeNode root;
    Object[] values;

    public JTreeFactory withRoot(TreeNode newRoot) {
      root = newRoot;
      return this;
    }

    public JTreeFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JTreeFactory withValues(Object... newValues) {
      values = newValues;
      return this;
    }

    @RunsInEDT
    public JTree createNew() {
      return execute(new GuiQuery<JTree>() {
        @Override
        protected JTree executeInEDT() {
          if (root != null && !isNullOrEmpty(values)) {
            throw new IllegalStateException("Either set root or values, but not both");
          }
          JTree tree = null;
          if (root != null) {
            tree = new JTree(root);
          } else if (!isNullOrEmpty(values)) {
            tree = new JTree(values);
          } else {
            tree = new JTree();
          }
          tree.setName(name);
          return tree;
        }
      });
    }
  }
}