/*
 * Created on Oct 24, 2007
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

import static org.fest.util.Lists.emptyList;
import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.util.VisibleForTesting;

/**
 * Find children {@code Component}s in an AWT or Swing {@code Container}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class ChildrenFinder {
  private static List<ChildrenFinderStrategy> strategies = newArrayList(new JDesktopPaneChildrenFinder(),
      new JMenuChildrenFinder(), new WindowChildrenFinder());

  @RunsInCurrentThread
  @Nonnull Collection<Component> childrenOf(@Nonnull Component c) {
    if (!(c instanceof Container)) {
      return emptyList();
    }
    Container container = (Container) c;
    List<Component> children = newArrayList(container.getComponents());
    children.addAll(nonExplicitChildrenOf(container));
    return children;
  }

  private @Nonnull Collection<Component> nonExplicitChildrenOf(@Nonnull Container c) {
    Collection<Component> children = newArrayList();
    for (ChildrenFinderStrategy s : strategies) {
      children.addAll(s.nonExplicitChildrenOf(c));
    }
    return children;
  }

  @VisibleForTesting
  static @Nonnull List<ChildrenFinderStrategy> strategies() {
    return newArrayList(strategies);
  }

  @VisibleForTesting
  static void replaceStrategiesWith(@Nonnull List<ChildrenFinderStrategy> newStrategies) {
    strategies = newArrayList(newStrategies);
  }
}
