/*
 * Created on Jun 6, 2009
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
package org.fest.swing.test.swing;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * Factory of {@link javax.swing.tree.TreeNode}s.
 * 
 * @author Alex Ruiz
 * 
 */
public final class TreeNodeFactory {
  /**
   * Creates a new tree node with the given text and the given children.
   * 
   * @param text the text of the tree node to create.
   * @param children the children of the tree node to create.
   * @return the created tree node.
   */
  public static DefaultMutableTreeNode node(String text, MutableTreeNode... children) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
    for (MutableTreeNode child : children) {
      node.add(child);
    }
    return node;
  }

  private TreeNodeFactory() {}
}
