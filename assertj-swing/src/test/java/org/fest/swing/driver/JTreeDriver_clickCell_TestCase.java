/*
 * Created on Jul 23, 2009
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

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Point;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Test case for {@link JTreeDriver} that involves clicking/double-clicking a cell.
 * 
 * @author Alex Ruiz
 */
public abstract class JTreeDriver_clickCell_TestCase extends JTreeDriver_TestCase {
  @RunsInEDT
  final int rowAt(Point p) {
    return rowAtPoint(tree, p);
  }

  @RunsInEDT
  private static int rowAtPoint(final JTree tree, final Point p) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return tree.getRowForLocation(p.x, p.y);
      }
    });
  }

  @RunsInEDT
  static String pathAtPoint(final JTree tree, final Point p, final String separator) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        TreePath path = tree.getPathForLocation(p.x, p.y);
        return pathText(path, separator);
      }
    });
  }
}
