/*
 * Created on Feb 2, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JScrollBarSetValueTask.setValue;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import java.awt.Point;

import javax.annotation.Nonnull;
import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.util.GenericRange;
import org.fest.swing.util.Pair;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JScrollBar}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JScrollBarDriver extends JComponentDriver {
  private static final String VALUE_PROPERTY = "value";

  private final JScrollBarLocation location = new JScrollBarLocation();

  /**
   * Creates a new {@link JScrollBarDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public JScrollBarDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Scrolls up (or left) one unit (usually a line).
   * 
   * @param scrollBar the target {@code JScrollBar}.
   */
  public void scrollUnitUp(@Nonnull JScrollBar scrollBar) {
    scrollUnitUp(scrollBar, 1);
  }

  /**
   * Scrolls up (or left) one unit (usually a line,) the given number of times.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param times the number of times to scroll up one unit.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   */
  public void scrollUnitUp(@Nonnull JScrollBar scrollBar, int times) {
    checkPositive(times, "scroll up one unit");
    Pair<Point, Integer> scrollInfo = findScrollUnitInfo(scrollBar, location(), times * -1);
    scroll(scrollBar, scrollInfo);
  }

  /**
   * Scrolls down (or right) one unit (usually a line).
   * 
   * @param scrollBar the target {@code JScrollBar}.
   */
  public void scrollUnitDown(@Nonnull JScrollBar scrollBar) {
    scrollUnitDown(scrollBar, 1);
  }

  /**
   * Scrolls down (or right) one unit (usually a line,) the given number of times.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param times the number of times to scroll down one unit.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   */
  public void scrollUnitDown(@Nonnull JScrollBar scrollBar, int times) {
    checkPositive(times, "scroll down one unit");
    Pair<Point, Integer> scrollInfo = findScrollUnitInfo(scrollBar, location(), times);
    scroll(scrollBar, scrollInfo);
  }

  @RunsInEDT
  private static @Nonnull Pair<Point, Integer> findScrollUnitInfo(final @Nonnull JScrollBar scrollBar,
      final @Nonnull JScrollBarLocation location, final int times) {
    Pair<Point, Integer> result = execute(new GuiQuery<Pair<Point, Integer>>() {
      @Override
      protected Pair<Point, Integer> executeInEDT() {
        checkEnabledAndShowing(scrollBar);
        return scrollUnitInfo(scrollBar, location, times);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static @Nonnull  Pair<Point, Integer> scrollUnitInfo(@Nonnull JScrollBar scrollBar,
      @Nonnull JScrollBarLocation location, int times) {
    Point where = blockLocation(scrollBar, location, times);
    int count = times * scrollBar.getUnitIncrement();
    return Pair.of(where, scrollBar.getValue() + count);
  }

  /**
   * Scrolls up (or left) one block (usually a page).
   * 
   * @param scrollBar the target {@code JScrollBar}.
   */
  @RunsInEDT
  public void scrollBlockUp(@Nonnull JScrollBar scrollBar) {
    scrollBlockUp(scrollBar, 1);
  }

  /**
   * Scrolls up (or left) one block (usually a page,) the given number of times.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param times the number of times to scroll up one block.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   */
  @RunsInEDT
  public void scrollBlockUp(@Nonnull JScrollBar scrollBar, int times) {
    checkPositive(times, "scroll up one block");
    Pair<Point, Integer> scrollInfo = validateAndFindScrollBlockInfo(scrollBar, location(), times * -1);
    scroll(scrollBar, scrollInfo);
  }

  /**
   * Scrolls down (or right) one block (usually a page).
   * 
   * @param scrollBar the target {@code JScrollBar}.
   */
  @RunsInEDT
  public void scrollBlockDown(@Nonnull JScrollBar scrollBar) {
    scrollBlockDown(scrollBar, 1);
  }

  /**
   * Scrolls down (or right) one block (usually a page,) the given number of times.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param times the number of times to scroll down one block.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   */
  @RunsInEDT
  public void scrollBlockDown(@Nonnull JScrollBar scrollBar, int times) {
    checkPositive(times, "scroll down one block");
    Pair<Point, Integer> scrollInfo = validateAndFindScrollBlockInfo(scrollBar, location(), times);
    scroll(scrollBar, scrollInfo);
  }

  private void checkPositive(int times, @Nonnull String action) {
    if (times > 0) {
      return;
    }
    String msg = String.format("The number of times to %s should be greater than zero, but was <%d>", action, times);
    throw new IllegalArgumentException(msg);
  }

  @RunsInEDT
  private static @Nonnull Pair<Point, Integer> validateAndFindScrollBlockInfo(final @Nonnull JScrollBar scrollBar,
      final @Nonnull JScrollBarLocation location, final int times) {
    Pair<Point, Integer> result = execute(new GuiQuery<Pair<Point, Integer>>() {
      @Override
      protected Pair<Point, Integer> executeInEDT() {
        checkEnabledAndShowing(scrollBar);
        return scrollBlockInfo(scrollBar, location, times);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static Pair<Point, Integer> scrollBlockInfo(@Nonnull JScrollBar scrollBar,
      @Nonnull JScrollBarLocation location, int times) {
    Point where = blockLocation(scrollBar, location, times);
    int count = times * scrollBar.getBlockIncrement();
    return Pair.of(where, scrollBar.getValue() + count);
  }

  @RunsInCurrentThread
  private static @Nonnull Point blockLocation(@Nonnull JScrollBar scrollBar, @Nonnull JScrollBarLocation location,
      int times) {
    return times > 0 ? location.blockLocationToScrollDown(scrollBar) : location.blockLocationToScrollUp(scrollBar);
  }

  @RunsInEDT
  private void scroll(@Nonnull JScrollBar scrollBar, @Nonnull Pair<Point, Integer> scrollInfo) {
    // For now, do it programmatically, faking the mouse movement and clicking
    robot.moveMouse(scrollBar, checkNotNull(scrollInfo.first));
    setValueProperty(scrollBar, scrollInfo.second);
  }

  /**
   * Scrolls to the maximum position of the given {@code JScrollBar}.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   */
  @RunsInEDT
  public void scrollToMaximum(@Nonnull JScrollBar scrollBar) {
    Pair<Integer, GenericRange<Point>> scrollInfo = findScrollToMaximumInfo(scrollBar, location());
    scroll(scrollBar, scrollInfo.first, checkNotNull(scrollInfo.second));
  }

  @RunsInEDT
  private static @Nonnull Pair<Integer, GenericRange<Point>> findScrollToMaximumInfo(
      final @Nonnull JScrollBar scrollBar, final @Nonnull JScrollBarLocation location) {
    Pair<Integer, GenericRange<Point>> result = execute(new GuiQuery<Pair<Integer, GenericRange<Point>>>() {
      @Override
      protected Pair<Integer, GenericRange<Point>> executeInEDT() {
        checkEnabledAndShowing(scrollBar);
        int position = scrollBar.getMaximum();
        GenericRange<Point> scrollInfo = scrollInfo(scrollBar, location, position);
        return Pair.of(position, scrollInfo);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Scrolls to the minimum position of the given {@code JScrollBar}.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   */
  @RunsInEDT
  public void scrollToMinimum(@Nonnull JScrollBar scrollBar) {
    Pair<Integer, GenericRange<Point>> scrollInfo = findScrollToMinimumInfo(scrollBar, location);
    scroll(scrollBar, scrollInfo.first, checkNotNull(scrollInfo.second));
  }

  @RunsInEDT
  private static @Nonnull Pair<Integer, GenericRange<Point>> findScrollToMinimumInfo(
      final @Nonnull JScrollBar scrollBar, final JScrollBarLocation location) {
    Pair<Integer, GenericRange<Point>> result = execute(new GuiQuery<Pair<Integer, GenericRange<Point>>>() {
      @Override
      protected Pair<Integer, GenericRange<Point>> executeInEDT() {
        checkEnabledAndShowing(scrollBar);
        int position = scrollBar.getMinimum();
        GenericRange<Point> scrollInfo = scrollInfo(scrollBar, location, position);
        return Pair.of(position, scrollInfo);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Scrolls to the given position.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param position the position to scroll to.
   * @throws IllegalStateException if the {@code JScrollBar} is disabled.
   * @throws IllegalStateException if the {@code JScrollBar} is not showing on the screen.
   * @throws IllegalArgumentException if the given position is not within the {@code JScrollBar} bounds.
   */
  @RunsInEDT
  public void scrollTo(@Nonnull JScrollBar scrollBar, int position) {
    GenericRange<Point> scrollInfo = validateAndFindScrollInfo(scrollBar, location(), position);
    scroll(scrollBar, position, scrollInfo);
  }

  @RunsInEDT
  private static @Nonnull GenericRange<Point> validateAndFindScrollInfo(final @Nonnull JScrollBar scrollBar,
      final @Nonnull JScrollBarLocation location, final int position) {
    GenericRange<Point> result = execute(new GuiQuery<GenericRange<Point>>() {
      @Override
      protected GenericRange<Point> executeInEDT() {
        checkPositionInBounds(scrollBar, position);
        checkEnabledAndShowing(scrollBar);
        return scrollInfo(scrollBar, location, position);
      }
    });
    return checkNotNull(result);
  }

  @RunsInCurrentThread
  private static void checkPositionInBounds(@Nonnull JScrollBar scrollBar, int position) {
    int min = scrollBar.getMinimum();
    int max = scrollBar.getMaximum();
    if (position >= min && position <= max) {
      return;
    }
    throw new IllegalArgumentException(concat("Position <", position, "> is not within the JScrollBar bounds of <",
        min, "> and <", max, ">"));
  }

  @RunsInCurrentThread
  private static @Nonnull GenericRange<Point> scrollInfo(@Nonnull JScrollBar scrollBar,
      @Nonnull JScrollBarLocation location, int position) {
    Point from = location.thumbLocation(scrollBar, scrollBar.getValue());
    Point to = location.thumbLocation(scrollBar, position);
    return new GenericRange<Point>(from, to);
  }

  private void scroll(@Nonnull JScrollBar scrollBar, int position, @Nonnull GenericRange<Point> points) {
    simulateScrolling(scrollBar, points);
    setValueProperty(scrollBar, position);
  }

  @RunsInEDT
  private void simulateScrolling(@Nonnull JScrollBar scrollBar, @Nonnull GenericRange<Point> points) {
    robot.moveMouse(scrollBar, points.from());
    robot.moveMouse(scrollBar, points.to());
  }

  @RunsInEDT
  private void setValueProperty(@Nonnull JScrollBar scrollBar, int value) {
    setValue(scrollBar, value);
    robot.waitForIdle();
  }

  /**
   * Asserts that the value of the {@code JScrollBar} is equal to the given one.
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param value the expected value.
   * @throws AssertionError if the value of the {@code JScrollBar} is not equal to the given one.
   */
  @RunsInEDT
  public void requireValue(@Nonnull JScrollBar scrollBar, int value) {
    assertThat(valueOf(scrollBar)).as(propertyName(scrollBar, VALUE_PROPERTY)).isEqualTo(value);
  }

  private @Nonnull JScrollBarLocation location() {
    return location;
  }
}
