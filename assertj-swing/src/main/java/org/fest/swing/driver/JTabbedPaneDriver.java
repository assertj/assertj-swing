/*
 * Created on Jan 27, 2008
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
import static org.fest.swing.driver.JTabbedPaneSelectTabTask.setSelectedTab;
import static org.fest.swing.driver.JTabbedPaneTabTitlesQuery.tabTitlesOf;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Point;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTabbedPane;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.data.Index;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.PatternTextMatcher;
import org.fest.swing.util.StringTextMatcher;
import org.fest.swing.util.TextMatcher;
import org.fest.util.InternalApi;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Supports functional testing of {@code JTabbedPane}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public class JTabbedPaneDriver extends JComponentDriver {
  private final JTabbedPaneLocation location;

  /**
   * Creates a new {@link JTabbedPaneDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JTabbedPaneDriver(@Nonnull Robot robot) {
    this(robot, new JTabbedPaneLocation());
  }

  /**
   * Creates a new {@link JTabbedPaneDriver}.
   *
   * @param robot the robot to use to simulate user input.
   * @param location knows how to find the location of a tab.
   */
  @VisibleForTesting
  JTabbedPaneDriver(@Nonnull Robot robot, @Nonnull JTabbedPaneLocation location) {
    super(robot);
    this.location = location;
  }

  /**
   * Returns the titles of all the tabs.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @return the titles of all the tabs.
   */
  @RunsInEDT
  public @Nonnull String[] tabTitles(@Nonnull JTabbedPane tabbedPane) {
    return tabTitlesOf(tabbedPane);
  }

  /**
   * Simulates a user selecting the tab containing the given title.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param title the given text to match. It can be a regular expression.
   * @throws IllegalStateException if the {@code JTabbedPane} is disabled.
   * @throws IllegalStateException if the {@code JTabbedPane} is not showing on the screen.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  @RunsInEDT
  public void selectTab(@Nonnull JTabbedPane tabbedPane, @Nullable String title) {
    selectTab(tabbedPane, new StringTextMatcher(title));
  }

  /**
   * Simulates a user selecting the tab whose title matches the given regular expression pattern.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param pattern the regular expression pattern to match.
   * @throws IllegalStateException if the {@code JTabbedPane} is disabled.
   * @throws IllegalStateException if the {@code JTabbedPane} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws LocationUnavailableException if a tab matching the given regular expression pattern could not be found.
   * @since 1.2
   */
  @RunsInEDT
  public void selectTab(@Nonnull JTabbedPane tabbedPane, @Nonnull Pattern pattern) {
    selectTab(tabbedPane, new PatternTextMatcher(pattern));
  }

  @RunsInEDT
  private void selectTab(@Nonnull JTabbedPane tabbedPane, @Nonnull TextMatcher matcher) {
    Pair<Integer, Point> tabToSelectInfo = tabToSelectInfo(location(), tabbedPane, matcher);
    Point target = tabToSelectInfo.second;
    if (target != null) {
      try {
        click(tabbedPane, target);
      } catch (ActionFailedException e) {
        // On Mac it may be necessary to scroll the tabs to find the one to click.
        setTabDirectly(tabbedPane, tabToSelectInfo.first);
      }
      return;
    }
    setTabDirectly(tabbedPane, tabToSelectInfo.first);
  }

  @RunsInEDT
  private static @Nonnull Pair<Integer, Point> tabToSelectInfo(final @Nonnull JTabbedPaneLocation location,
      final @Nonnull JTabbedPane tabbedPane, final @Nonnull TextMatcher matcher) {
    Pair<Integer, Point> result = execute(new GuiQuery<Pair<Integer, Point>>() {
      @Override
      protected Pair<Integer, Point> executeInEDT() {
        checkEnabledAndShowing(tabbedPane);
        int index = location.indexOf(tabbedPane, matcher);
        location.checkIndexInBounds(tabbedPane, index);
        Point point = null;
        try {
          point = location.pointAt(tabbedPane, index);
        } catch (LocationUnavailableException e) {
        }
        return Pair.of(index, point);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param index the index of the tab to select.
   * @throws IllegalStateException if the {@code JTabbedPane} is disabled.
   * @throws IllegalStateException if the {@code JTabbedPane} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
   */
  public void selectTab(@Nonnull JTabbedPane tabbedPane, int index) {
    try {
      Point p = pointAtTabWhenShowing(location(), tabbedPane, index);
      click(tabbedPane, p);
    } catch (LocationUnavailableException e) {
      setTabDirectly(tabbedPane, index);
    } catch (ActionFailedException e) {
      setTabDirectly(tabbedPane, index);
    }
  }

  @RunsInEDT
  private static @Nonnull Point pointAtTabWhenShowing(final @Nonnull JTabbedPaneLocation location,
      final @Nonnull JTabbedPane tabbedPane, final int index) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        location.checkIndexInBounds(tabbedPane, index);
        checkEnabledAndShowing(tabbedPane);
        return location.pointAt(tabbedPane, index);
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  @VisibleForTesting
  void setTabDirectly(@Nonnull JTabbedPane tabbedPane, int index) {
    setSelectedTab(tabbedPane, index);
    robot.waitForIdle();
    moveMouseToTab(tabbedPane, index);
  }

  private void moveMouseToTab(@Nonnull JTabbedPane tabbedPane, int index) {
    try {
      Point p = pointAtTab(location(), tabbedPane, index);
      robot.moveMouse(tabbedPane, p);
      robot.waitForIdle();
    } catch (LocationUnavailableException ignored) {
    }
  }

  @RunsInEDT
  private static @Nonnull Point pointAtTab(final @Nonnull JTabbedPaneLocation location,
      final @Nonnull JTabbedPane tabbedPane, final int index) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        return location.pointAt(tabbedPane, index);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Returns the currently selected component for the given {@code JTabbedPane}.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @return the currently selected component for the given {@code JTabbedPane}.
   */
  @RunsInEDT
  public @Nullable Component selectedComponentOf(@Nonnull JTabbedPane tabbedPane) {
    return selectedComponent(tabbedPane);
  }

  @RunsInEDT
  private static @Nullable Component selectedComponent(final JTabbedPane tabbedPane) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected Component executeInEDT() {
        return tabbedPane.getSelectedComponent();
      }
    });
  }

  /**
   * Asserts that the title of the tab at the given index matches the given value.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param title the expected title. It can be a regular expression.
   * @param index the index of the tab.
   * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
   * @throws AssertionError if the title of the tab at the given index does not match the given one.
   */
  @RunsInEDT
  public void requireTabTitle(@Nonnull JTabbedPane tabbedPane, @Nullable String title, @Nonnull Index index) {
    String actualTitle = titleAt(tabbedPane, index);
    verifyThat(actualTitle).as(titleAtProperty(tabbedPane)).isEqualOrMatches(title);
  }

  /**
   * Asserts that the title of the tab at the given index matches the given regular expression pattern.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param pattern the regular expression pattern to match.
   * @param index the index of the tab.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
   * @throws AssertionError if the title of the tab at the given index does not match the given one.
   * @since 1.2
   */
  @RunsInEDT
  public void requireTabTitle(@Nonnull JTabbedPane tabbedPane, @Nonnull Pattern pattern, @Nonnull Index index) {
    String actualTitle = titleAt(tabbedPane, index);
    verifyThat(actualTitle).as(titleAtProperty(tabbedPane)).matches(pattern);
  }

  @RunsInEDT
  private Description titleAtProperty(@Nonnull JTabbedPane tabbedPane) {
    return propertyName(tabbedPane, "titleAt");
  }

  @RunsInEDT
  private static @Nullable String titleAt(final @Nonnull JTabbedPane tabbedPane, final @Nonnull Index index) {
    return execute(new GuiQuery<String>() {
      @Override
      protected String executeInEDT() {
        return tabbedPane.getTitleAt(index.value);
      }
    });
  }

  /**
   * Asserts that the tabs of the given {@code JTabbedPane} have the given titles. The tab titles are evaluated by index
   * order, for example, the first tab is expected to have the first title in the given array, and so on.
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param titles the expected titles.
   * @throws AssertionError if the title of any of the tabs is not equal to the expected titles.
   */
  @RunsInEDT
  public void requireTabTitles(@Nonnull JTabbedPane tabbedPane, @Nonnull String[] titles) {
    String[] actualTitles = allTabTitlesIn(tabbedPane);
    assertThat(actualTitles).as(propertyName(tabbedPane, "tabTitles")).isEqualTo(titles);
  }

  @RunsInEDT
  private static @Nonnull String[] allTabTitlesIn(final @Nonnull JTabbedPane tabbedPane) {
    String[] result = execute(new GuiQuery<String[]>() {
      @Override
      protected String[] executeInEDT() {
        List<String> allTitles = newArrayList();
        int tabCount = tabbedPane.getTabCount();
        for (int i = 0; i < tabCount; i++) {
          allTitles.add(tabbedPane.getTitleAt(i));
        }
        return allTitles.toArray(new String[allTitles.size()]);
      }
    });
    return checkNotNull(result);
  }

  private @Nonnull JTabbedPaneLocation location() {
    return location;
  }
}
