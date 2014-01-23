/*
 * Created on Oct 31, 2007
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

import static java.awt.AWTEvent.COMPONENT_EVENT_MASK;
import static java.awt.AWTEvent.WINDOW_EVENT_MASK;
import static org.fest.swing.listener.WeakEventListener.attachAsWeakEventListener;
import static org.fest.util.Lists.emptyList;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.util.ToolkitProvider;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Isolates a {@link ComponentHierarchy} to limit to only those components created during the lifetime of this
 * hierarchy. Existing AWT or Swing {@code Component}s (and any subsequently generated sub-windows) are ignored by
 * default.
 * </p>
 *
 * <p>
 * Implicitly auto-filters {@code Window}s which are disposed (i.e. generates a {@code WindowEvent#WINDOW_CLOSED}
 * event), but also implicitly un-filters them if they should be shown again. Any {@code Window} explicitly disposed by
 * the calling {@link ComponentHierarchy#dispose(java.awt.Window)} will be ignored permanently.
 * </p>
 *
 * @author Alex Ruiz
 */
public class NewHierarchy extends ExistingHierarchy {
  private final WindowFilter filter;
  private final TransientWindowListener transientWindowListener;

  /**
   * Creates a new {@link NewHierarchy} which does not contain any existing AWT or Swing {@code Component}s.
   *
   * @return the created hierarchy.
   */
  public static @Nonnull NewHierarchy ignoreExistingComponents() {
    return new NewHierarchy(true);
  }

  /**
   * Creates a new {@link NewHierarchy} which contains existing AWT or Swing {@code Component}s.
   *
   * @return the created hierarchy.
   */
  public static @Nonnull NewHierarchy includeExistingComponents() {
    return new NewHierarchy(false);
  }

  private NewHierarchy(boolean ignoreExisting) {
    this(ToolkitProvider.instance().defaultToolkit(), ignoreExisting);
  }

  private NewHierarchy(@Nonnull Toolkit toolkit, boolean ignoreExisting) {
    this.filter = new WindowFilter(parentFinder(), childrenFinder());
    transientWindowListener = new TransientWindowListener(filter);
    setUp(toolkit, ignoreExisting);
  }

  @VisibleForTesting
  NewHierarchy(@Nonnull Toolkit toolkit, @Nonnull WindowFilter filter, boolean ignoreExisting) {
    this.filter = filter;
    transientWindowListener = new TransientWindowListener(filter);
    setUp(toolkit, ignoreExisting);
  }

  @RunsInCurrentThread
  private void setUp(@Nonnull Toolkit toolkit, boolean ignoreExisting) {
    if (ignoreExisting) {
      ignoreExisting();
    }
    attachAsWeakEventListener(toolkit, transientWindowListener, WINDOW_EVENT_MASK | COMPONENT_EVENT_MASK);
  }

  /**
   * <p>
   * Makes all currently existing AWT and Swing {@code Component} invisible to this hierarchy, without affecting their
   * current state.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   */
  @RunsInCurrentThread
  public void ignoreExisting() {
    for (Container c : roots()) {
      if (c != null) {
        filter.ignore(c);
      }
    }
  }

  /**
   * <p>
   * Make the given AWT or Swing {@code Component} visible to this hierarchy.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   */
  @RunsInCurrentThread
  public void recognize(@Nonnull Component c) {
    filter.recognize(c);
  }

  /**
   * <p>
   * Returns all the children of the given AWT or Swing {@code Component}, omitting those which are currently filtered.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @return all the children of the given {@code Component}, omitting those which are currently filtered.
   */
  @RunsInCurrentThread
  @Override
  public @Nonnull Collection<Component> childrenOf(@Nonnull Component c) {
    if (filter.isIgnored(c)) {
      return emptyList();
    }
    Collection<Component> children = super.childrenOf(c);
    // this only removes those components which are directly filtered, not necessarily those which have a filtered
    // ancestor.
    children.removeAll(filter.filtered());
    return children;
  }

  /**
   * <p>
   * Returns {@code true} if the given AWT or Swing {@code Component} is not ignored.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @return {@code true} if the given {@code Component} is not ignored, {@code false} otherwise.
   */
  @RunsInCurrentThread
  @Override
  public boolean contains(@Nonnull Component c) {
    return super.contains(c) && !filter.isIgnored(c);
  }

  /**
   * <p>
   * Disposes the given {@code Window}, but only if it currently exists within the hierarchy. It will no longer appear
   * in this hierarchy or be reachable in a hierarchy walk.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param w the {@code Window} to dispose.
   */
  @RunsInCurrentThread
  @Override
  public void dispose(@Nonnull Window w) {
    if (!contains(w)) {
      return;
    }
    super.dispose(w);
    filter.ignore(w);
  }

  /**
   * @return all available root containers, excluding those which have been filtered.
   */
  @Override
  public @Nonnull Collection<Container> roots() {
    Collection<Container> roots = super.roots();
    roots.removeAll(filter.filtered());
    return roots;
  }
}
