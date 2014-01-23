/*
 * Created on Nov 1, 2007
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
package org.fest.swing.hierarchy;

import static org.fest.swing.hierarchy.JInternalFrameDesktopPaneQuery.desktopPaneOf;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Finds the parent of an AWT or Swing {@code Component}. This method returns the most likely parent based on the type
 * of a given {@code Component}.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 */
class ParentFinder {
  /**
   * Return the parent for the given AWT or Swing {@code Component}.
   * 
   * @param c the given {@code Component}.
   * @return the parent for the given {@code Component}.
   */
  @RunsInCurrentThread
  @Nullable Container parentOf(@Nonnull Component c) {
    Container p = c.getParent();
    if (p == null && c instanceof JInternalFrame) {
      p = parentOf((JInternalFrame) c);
    }
    return p;
  }

  @RunsInCurrentThread
  private @Nullable Container parentOf(@Nonnull JInternalFrame internalFrame) {
    // From Abbot: workaround for bug in JInternalFrame: COMPONENT_HIDDEN is sent before the desktop icon is set, so
    // JInternalFrame.getDesktopPane will throw a NPE if called while dispatching that event. Reported against 1.4.x.
    return desktopPaneOf(internalFrame);
  }

  /**
   * Similar to {@code SwingUtilities.windowForComponent(Component)}, but returns the {@code Component} itself if it is
   * a {@code Window}, or the invoker's window if on a pop-up.
   * 
   * @param c the AWT or Swing {@code Component} whose {@code Window} ancestor we are looking for.
   * @return the window ancestor of the given {@code Component}, or given {@code Component} itself it is a
   *         {@code Window}.
   */
  @Nullable Window windowFor(@Nullable Component c) {
    if (c == null) {
      return null;
    }
    if (c instanceof Window) {
      return (Window) c;
    }
    if (c instanceof MenuElement) {
      Component invoker = invokerFor(c);
      if (invoker != null) {
        return windowFor(invoker);
      }
    }
    return windowFor(parentOf(c));
  }

  /**
   * Returns the invoker, if any, of the given AWT or Swing {@code Component}. Returns {@code null} if the
   * {@code Component} is not on a pop-up of any sort.
   * 
   * @param c the given {@code Component}.
   * @return the invoker of the given {@code Component} if found. Otherwise, {@code null}.
   */
  @RunsInCurrentThread
  @Nullable Component invokerFor(@Nonnull Component c) {
    if (c instanceof JPopupMenu) {
      return ((JPopupMenu) c).getInvoker();
    }
    Component parent = c.getParent();
    if (parent == null) {
      return null;
    }
    return invokerFor(parent);
  }
}
