package org.fest.swing.driver;

import static javax.swing.SwingUtilities.getWindowAncestor;

import java.awt.Frame;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.swing.JToolBar;
import javax.swing.plaf.ToolBarUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import org.fest.swing.annotation.RunsInCurrentThread;

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

  private JToolBarIsFloatingQuery() {}
}