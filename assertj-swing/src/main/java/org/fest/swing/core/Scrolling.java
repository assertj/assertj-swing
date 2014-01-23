/*
 * Created on Jan 13, 2009
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
package org.fest.swing.core;

import static javax.swing.SwingUtilities.convertRectangle;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JViewport;

import org.fest.swing.edt.GuiTask;

/**
 * Utility methods related to scrolling.
 *
 * @author Juhos Csaba-Zsolt
 *
 * @since 1.2
 */
public final class Scrolling {
  /**
   * Scrolls a {@code JComponent} into view within a container.
   *
   * @param robot simulates user input.
   * @param c the given {@code JComponent}.
   */
  public static void scrollToVisible(@Nonnull Robot robot, @Nonnull JComponent c) {
    JComponent root = findClosestValidatingRootAncestor(c);
    // scroll the component to view within each validating root ancestor, starting from the nearest
    while (root != null) {
      scrollToVisible(robot, root, c);
      // find the next validating root
      root = findClosestValidatingRootAncestor(root);
    }
  }

  /**
   * Returns a {@code JComponent}'s closest validating root ancestor in the AWT containment hierarchy.
   *
   * @param c the given {@code JComponent}.
   * @return the found ancestor or {@code null} if there isn't one.
   */
  private static @Nullable JComponent findClosestValidatingRootAncestor(@Nonnull JComponent c) {
    // the candidate validating root at every iteration (candidate = not necessarily a root)
    Container root = c;
    // we go up to the top of the hierarchy
    while (root != null) {
      Container parent = root.getParent();
      // the new candidate root becomes the parent of the previous one
      root = parent;
      // if the candidate isn't a JComponent, we're not interested in it (we need JComponent#scrollRectToVisible)
      if (!(root instanceof JComponent)) {
        continue;
      }
      // we don't have to take JFrame into account, it's not a JComponent (ant it's a top-level container anyway)
      if (root instanceof JViewport || root instanceof JInternalFrame) {
        return (JComponent) root;
      }
    }
    return null;
  }

  /**
   * Scrolls an AWT or Swing {@code Component} into view within a container.
   *
   * @param robot simulates user input.
   * @param container the given container.
   * @param target the given {@code Component}.
   */
  private static void scrollToVisible(@Nonnull Robot robot, @Nonnull JComponent container, @Nonnull Component target) {
    Rectangle r = convertRectangle(target.getParent(), target.getBounds(), container);
    scrollToVisible(robot, container, checkNotNull(r));
  }

  /**
   * Scrolls a rectangular region of a {@code JComponent} into view.
   *
   * @param robot simulates user input.
   * @param c the {@code JComponent}.
   * @param rectangle the rectangular region.
   */
  private static void scrollToVisible(@Nonnull Robot robot, final @Nonnull JComponent c,
      final @Nonnull Rectangle rectangle) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        c.scrollRectToVisible(rectangle);
      }
    });
    robot.waitForIdle();
  }

  private Scrolling() {}
}
