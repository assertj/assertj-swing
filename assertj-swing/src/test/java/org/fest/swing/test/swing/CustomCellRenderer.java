/*
 * Created on Apr 13, 2008
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
package org.fest.swing.test.swing;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * Cell renderer that returns the specified {@code Component} as its renderer component. Used only for testing.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class CustomCellRenderer implements ListCellRenderer, TableCellRenderer, TreeCellRenderer {
  private final Component rendererComponent;

  public CustomCellRenderer(Component rendererComponent) {
    this.rendererComponent = rendererComponent;
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus) {
    return rendererComponent;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, int row,
      int column) {
    return rendererComponent;
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
      boolean leaf, int row, boolean focus) {
    return rendererComponent;
  }
}