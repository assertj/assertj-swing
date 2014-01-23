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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.util.VisibleForTesting;

/**
 * Finds the AWT or Swing {@code Component} owning the input focus.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FocusOwnerFinder {
  private static final List<FocusOwnerFinderStrategy> STRATEGIES = newArrayList();

  static {
    initializeStrategies();
  }

  @VisibleForTesting
  static void initializeStrategies() {
    replaceStrategiesWith(new ReflectionBasedFocusOwnerFinder(), new HierarchyBasedFocusOwnerFinder());
  }

  @VisibleForTesting
  static void replaceStrategiesWith(@Nonnull FocusOwnerFinderStrategy... strategies) {
    STRATEGIES.clear();
    STRATEGIES.addAll(newArrayList(strategies));
  }

  @VisibleForTesting
  static @Nonnull List<FocusOwnerFinderStrategy> strategies() {
    return newArrayList(STRATEGIES);
  }

  /**
   * @return the focus owner. This method is executed in the event dispatch thread (EDT.)
   */
  @RunsInEDT
  public static @Nullable Component inEdtFocusOwner() {
    return execute(new GuiQuery<Component>() {
      @Override
      protected @Nullable Component executeInEDT() {
        return focusOwner();
      }
    });
  }

  /**
   * <p>
   * Returns the focus owner.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @return the focus owner.
   */
  @RunsInCurrentThread
  public static @Nullable Component focusOwner() {
    for (FocusOwnerFinderStrategy strategy : STRATEGIES) {
      Component focusOwner = focusOwnerFrom(checkNotNull(strategy));
      if (focusOwner != null) {
        return focusOwner;
      }
    }
    return null;
  }

  private static @Nullable Component focusOwnerFrom(@Nonnull FocusOwnerFinderStrategy strategy) {
    try {
      return strategy.focusOwner();
    } catch (Exception e) {
      return null;
    }
  }

  private FocusOwnerFinder() {}
}
