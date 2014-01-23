/*
 * Created on Mar 30, 2008
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
package org.fest.swing.core;

import static org.fest.swing.awt.AWT.invokerOf;

import java.awt.Component;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.MenuElement;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;

/**
 * Looks up an AWT or Swing {@code Component}'s {@code Window} ancestor.
 *
 * @author Yvonne Wang
 */
public final class WindowAncestorFinder {
  private static ComponentHierarchy hierarchy = new ExistingHierarchy();

  /**
   * <p>
   * Similar to {@code javax.swing.SwingUtilities#getWindowAncestor(Component)}, but returns the AWT or Swing
   * {@code Component} itself if it is a {@code Window}, or the invoker's {@code Window} if on a pop-up.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the {@code Component} to get the {@code Window} ancestor of.
   * @return the {@code Window} ancestor of the given {@code Component}, the {@code Component} itself if it is a
   *         {@code Window}, or the invoker's {@code Window} if on a pop-up.
   */
  @RunsInCurrentThread
  public static @Nullable Window windowAncestorOf(@Nonnull Component c) {
    return findWindowAncestor(c);
  }

  @RunsInCurrentThread
  private static @Nullable Window findWindowAncestor(@Nullable Component c) {
    if (c == null) {
      return null;
    }
    if (c instanceof Window) {
      return (Window) c;
    }
    if (c instanceof MenuElement) {
      Component invoker = invokerOf(c);
      if (invoker != null) {
        return windowAncestorOf(invoker);
      }
    }
    return findWindowAncestor(hierarchy.parentOf(c));
  }

  private WindowAncestorFinder() {}
}
