/*
 * Created on Dec 22, 2007
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

import static javax.swing.SwingUtilities.isDescendingFrom;
import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A {@link ComponentHierarchy} created with a specified AWT or Swing {@code Component} as root.
 *
 * @author Alex Ruiz
 */
public final class SingleComponentHierarchy implements ComponentHierarchy {
  private final Container root;
  private final List<Container> list = newArrayList();
  private final ComponentHierarchy hierarchy;

  /**
   * Creates a new {@link SingleComponentHierarchy}.
   *
   * @param root the root {@code Component} for this hierarchy.
   * @param hierarchy the base {@link ComponentHierarchy}.
   */
  public SingleComponentHierarchy(@Nonnull Container root, @Nonnull ComponentHierarchy hierarchy) {
    this.root = root;
    this.hierarchy = hierarchy;
    list.add(root);
  }

  /**
   * @return the root {@code Component} in this hierarchy.
   */
  public Container root() {
    return root;
  }

  /**
   * Returns the parent for the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return the parent component for the given {@code Component}.
   */
  @Override
  public @Nullable Container parentOf(@Nonnull Component c) {
    return hierarchy.parentOf(c);
  }

  /**
   * @return a collection containing only the root {@code Component} in this hierarchy.
   */
  @Override
  public @Nonnull Collection<Container> roots() {
    return list;
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull Collection<Component> childrenOf(@Nonnull Component c) {
    return hierarchy.childrenOf(c);
  }

  /** {@inheritDoc} */
  @Override
  public boolean contains(@Nonnull Component c) {
    return hierarchy.contains(c) && isDescendingFrom(c, root);
  }

  /** {@inheritDoc} */
  @Override
  public void dispose(@Nonnull Window w) {
    hierarchy.dispose(w);
  }
}
