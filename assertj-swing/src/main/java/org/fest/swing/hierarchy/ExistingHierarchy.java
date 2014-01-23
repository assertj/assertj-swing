/*
 * Created on Oct 19, 2007
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

import static org.fest.swing.awt.AWT.isAppletViewer;
import static org.fest.swing.awt.AWT.isSharedInvisibleFrame;
import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.monitor.WindowMonitor;
import org.fest.util.VisibleForTesting;

/**
 * Provides access to the current AWT hierarchy.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ExistingHierarchy implements ComponentHierarchy {
  private static WindowMonitor windowMonitor = WindowMonitor.instance();

  private final ParentFinder parentFinder;
  private final ChildrenFinder childrenFinder;

  public ExistingHierarchy() {
    this(new ParentFinder(), new ChildrenFinder());
  }

  @VisibleForTesting
  ExistingHierarchy(@Nonnull ParentFinder parentFinder, @Nonnull ChildrenFinder childrenFinder) {
    this.parentFinder = parentFinder;
    this.childrenFinder = childrenFinder;
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull
  Collection<Container> roots() {
    List<Container> roots = newArrayList();
    for (Window w : windowMonitor.rootWindows()) {
      roots.add(w);
    }
    return roots;
  }

  /**
   * <p>
   * Returns the parent for the given AWT or Swing {@code Component}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @return the parent for the given {@code Component}.
   */
  @RunsInCurrentThread
  @Override
  public Container parentOf(@Nonnull Component c) {
    return parentFinder.parentOf(c);
  }

  /**
   * Returns whether the given AWT or Swing {@code Component} is reachable from any of the root windows. The default is
   * to consider all components to be contained in the hierarchy, whether they are reachable or not.
   *
   * @param c the given {@code Component}.
   * @return {@code true}.
   */
  @Override
  public boolean contains(@Nonnull Component c) {
    return true;
  }

  /**
   * <p>
   * Returns all descendants of interest of the given AWT or Swing {@code Component}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @return all descendants of interest of the given {@code Component}.
   */
  @RunsInCurrentThread
  @Override
  public @Nonnull Collection<Component> childrenOf(@Nonnull Component c) {
    return childrenFinder.childrenOf(c);
  }

  /**
   * <p>
   * Properly disposes of the given {@code Window}, making it and its native resources available for garbage collection.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param w the {@code Window} to dispose.
   */
  @Override
  @RunsInCurrentThread
  public void dispose(@Nonnull Window w) {
    if (isAppletViewer(w)) {
      return;
    }
    for (Window owned : w.getOwnedWindows()) {
      if (owned != null) {
        dispose(owned);
      }
    }
    if (isSharedInvisibleFrame(w)) {
      return;
    }
    w.dispose();
  }

  @Nonnull ParentFinder parentFinder() {
    return parentFinder;
  }

  @Nonnull ChildrenFinder childrenFinder() {
    return childrenFinder;
  }
}
