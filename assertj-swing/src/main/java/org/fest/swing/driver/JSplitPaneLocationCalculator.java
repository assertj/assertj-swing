/*
 * Created on Nov 25, 2009
 * 
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Insets;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JSplitPane;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

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

  private static void add(@Nonnull LocationFinder...finders) {
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

  private JSplitPaneLocationCalculator() {}

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
      if (splitPane.getLeftComponent() == null || rightComponent == null)
      {
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
