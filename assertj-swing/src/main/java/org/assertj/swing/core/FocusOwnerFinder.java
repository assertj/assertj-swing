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
package org.assertj.swing.core;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

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

  private FocusOwnerFinder() {
  }
}
