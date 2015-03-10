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
package org.assertj.swing.driver;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.assertj.core.util.Maps.newHashMap;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.awt.Insets;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JSplitPane;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Calculates a valid position of a {@code JSplitPane}'s divider, while respecting the minimum sizes of the right and
 * left component inside the {@code JSplitPane}.
 * 
 * @author Alex Ruiz
 */
final class JSplitPaneLocationCalculator {
  private static Map<Integer, LocationFinder> FINDERS = newHashMap();

  static {
    add(new VerticalOrientationLocationFinder(), new HorizontalOrientationLocationFinder());
  }

  private static void add(@Nonnull LocationFinder... finders) {
    for (LocationFinder finder : finders) {
      FINDERS.put(finder.orientation(), finder);
    }
  }

  @RunsInEDT
  static int locationToMoveDividerTo(final @Nonnull JSplitPane splitPane, final int desiredLocation) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected @Nullable Integer executeInEDT() {
        return FINDERS.get(splitPane.getOrientation()).locationToMoveDividerTo(splitPane, desiredLocation);
      }
    });
    return checkNotNull(result);
  }

  private JSplitPaneLocationCalculator() {
  }

  private static abstract class LocationFinder {
    abstract int locationToMoveDividerTo(@Nonnull JSplitPane splitPane, int desiredLocation);

    abstract int orientation();
  }

  private static class VerticalOrientationLocationFinder extends LocationFinder {
    @RunsInCurrentThread
    @Override
    int locationToMoveDividerTo(@Nonnull JSplitPane splitPane, int desiredLocation) {
      int minimum = calculateMinimum(splitPane);
      int maximum = calculateMaximum(splitPane);
      if (maximum < minimum) {
        minimum = maximum = 0;
      }
      return min(maximum, max(minimum, desiredLocation));
    }

    @RunsInCurrentThread
    private int calculateMinimum(@Nonnull JSplitPane splitPane) {
      Component left = splitPane.getLeftComponent();
      if (left == null || !left.isVisible()) {
        return 0;
      }
      int minimum = left.getMinimumSize().height;
      Insets insets = splitPane.getInsets();
      if (insets != null) {
        minimum += insets.top;
      }
      return minimum;
    }

    @RunsInCurrentThread
    private int calculateMaximum(@Nonnull JSplitPane splitPane) {
      Component rightComponent = splitPane.getRightComponent();
      if (splitPane.getLeftComponent() == null || rightComponent == null) {
        return -1; // Don't allow dragging.
      }
      Insets insets = splitPane.getInsets();
      int dividerSize = splitPane.getDividerSize();
      int bottom = (insets != null) ? insets.bottom : 0;
      int splitPaneHeight = splitPane.getSize().height;
      if (!rightComponent.isVisible()) {
        return max(0, splitPaneHeight - (dividerSize + bottom));
      }
      return max(0, splitPaneHeight - (dividerSize + bottom) - rightComponent.getMinimumSize().height);
    }

    @Override
    int orientation() {
      return VERTICAL_SPLIT;
    }
  }

  private static class HorizontalOrientationLocationFinder extends LocationFinder {
    @RunsInCurrentThread
    @Override
    int locationToMoveDividerTo(@Nonnull JSplitPane splitPane, int desiredLocation) {
      int minimum = calculateMinimum(splitPane);
      int maximum = calculateMaximum(splitPane);
      if (maximum < minimum) {
        minimum = maximum = 0;
      }
      return min(maximum, max(minimum, desiredLocation));
    }

    @RunsInCurrentThread
    private int calculateMinimum(@Nonnull JSplitPane splitPane) {
      Component left = splitPane.getLeftComponent();
      if (left == null || !left.isVisible()) {
        return 0;
      }
      int minimum = left.getMinimumSize().width;
      Insets insets = splitPane.getInsets();
      if (insets != null) {
        minimum += insets.left;
      }
      return minimum;
    }

    @RunsInCurrentThread
    private int calculateMaximum(@Nonnull JSplitPane splitPane) {
      Component rightComponent = splitPane.getRightComponent();
      if (splitPane.getLeftComponent() == null || rightComponent == null) {
        return -1; // Don't allow dragging.
      }
      Insets insets = splitPane.getInsets();
      int dividerSize = splitPane.getDividerSize();
      int right = (insets != null) ? insets.right : 0;
      int splitPaneWidth = splitPane.getSize().width;
      if (!rightComponent.isVisible()) {
        return max(0, splitPaneWidth - (dividerSize + right));
      }
      return max(0, splitPaneWidth - (dividerSize + right) - rightComponent.getMinimumSize().width);
    }

    @Override
    int orientation() {
      return HORIZONTAL_SPLIT;
    }
  }
}
