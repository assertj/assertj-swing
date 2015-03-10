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

import static javax.swing.SwingUtilities.getWindowAncestor;

import java.awt.Frame;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.swing.JToolBar;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import org.assertj.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Indicates whether a {@code JToolBar} is floating.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JToolBarIsFloatingQuery {
  @RunsInCurrentThread
  static boolean isJToolBarFloating(@Nonnull JToolBar toolBar) {
    ToolBarUI ui = toolBar.getUI();
    if (ui instanceof BasicToolBarUI) {
      return ((BasicToolBarUI) ui).isFloating();
    }
    // Have to guess; probably ought to check for sibling components
    Window w = getWindowAncestor(toolBar);
    return !(w instanceof Frame) && toolBar.getParent().getComponentCount() == 1;
  }

  private JToolBarIsFloatingQuery() {
  }
}