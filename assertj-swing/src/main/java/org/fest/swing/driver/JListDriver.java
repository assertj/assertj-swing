/*
 * Created on Jan 19, 2008
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

import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.util.Arrays.sort;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.awt.AWT.visibleCenterOf;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.JListContentQuery.contents;
import static org.fest.swing.driver.JListItemCountQuery.itemCountIn;
import static org.fest.swing.driver.JListItemPreconditions.checkIndicesInBounds;
import static org.fest.swing.driver.JListItemValueQuery.itemValue;
import static org.fest.swing.driver.JListMatchingItemQuery.centerOfMatchingItemCell;
import static org.fest.swing.driver.JListMatchingItemQuery.matchingItemIndex;
import static org.fest.swing.driver.JListMatchingItemQuery.matchingItemIndices;
import static org.fest.swing.driver.JListMatchingItemQuery.matchingItemValues;
import static org.fest.swing.driver.JListScrollToItemTask.ITEM_NOT_FOUND;
import static org.fest.swing.driver.JListScrollToItemTask.scrollToItem;
import static org.fest.swing.driver.JListScrollToItemTask.scrollToItemIfNotSelectedYet;
import static org.fest.swing.driver.JListSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.driver.JListSelectionIndicesQuery.selectedIndices;
import static org.fest.swing.driver.JListSelectionValueQuery.NO_SELECTION_VALUE;
import static org.fest.swing.driver.JListSelectionValueQuery.singleSelectionValue;
import static org.fest.swing.driver.JListSelectionValuesQuery.selectionValues;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.util.ArrayPreconditions.checkNotNullOrEmpty;
import static org.fest.util.Arrays.format;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JList;
import javax.swing.JPopupMenu;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.PatternTextMatcher;
import org.fest.swing.util.Range.From;
import org.fest.swing.util.Range.To;
import org.fest.swing.util.StringTextMatcher;
import org.fest.swing.util.TextMatcher;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JList}s.
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
public class JListDriver extends JComponentDriver {
  private static final String SELECTED_INDICES_PROPERTY = "selectedIndices";
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  private JListCellReader cellReader;

  /**
   * Creates a new {@link JListDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JListDriver(@Nonnull Robot robot) {
    super(robot);
    replaceCellReader(new BasicJListCellReader());
  }

  /**
   * Returns an array of {@code String}s that represents the contents of the given {@code JList}.
   *
   * @param list the target {@code JList}.
   * @return an array of {@code String}s that represents the contents of the given {@code JList}.
   * @see #replaceCellReader(JListCellReader)
   */
  @RunsInEDT
  public @Nonnull String[] contentsOf(@Nonnull JList list) {
    return contents(list, cellReader());
  }

  /**
   * Selects the items matching the given values.
   *
   * @param list the target {@code JList}.
   * @param values the values to match. Each {@code String} can be a regular expression.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the any of the given values cannot be found.
   */
  @RunsInEDT
  public void selectItems(@Nonnull JList list, @Nonnull String[] values) {
    selectItems(list, new StringTextMatcher(values));
  }

  /**
   * Selects the items matching the given regular expression patterns.
   *
   * @param list the target {@code JList}.
   * @param patterns the regular expression patterns to match.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws NullPointerException if any of the regular expression patterns is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the any of the given regular expression patterns cannot
   *           be found.
   * @since 1.2
   */
  @RunsInEDT
  public void selectItems(@Nonnull JList list, @Nonnull Pattern[] patterns) {
    selectItems(list, new PatternTextMatcher(patterns));
  }

  @RunsInEDT
  private void selectItems(final @Nonnull  JList list, final @Nonnull TextMatcher matcher) {
    final List<Integer> indices = matchingItemIndices(list, matcher, cellReader());
    if (indices.isEmpty()) {
      throw failMatchingNotFound(list, matcher);
    }
    clearSelection(list);
    new MultipleSelectionTemplate(robot) {
      @Override
      int elementCount() {
        return indices.size();
      }

      @Override
      void selectElement(int index) {
        selectItem(list, indices.get(index));
      }
    }.multiSelect();
  }

  /**
   * Selects the item in the given {@code JList} whose value matches the given one.
   *
   * @param list the target {@code JList}.
   * @param value the value to match.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public void selectItem(@Nonnull JList list, @Nullable String value) {
    selectItem(list, new StringTextMatcher(value));
  }

  /**
   * Selects the item in the given {@code JList} whose value matches the given regular expression pattern.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression to match.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  public void selectItem(@Nonnull JList list, @Nonnull Pattern pattern) {
    selectItem(list, new PatternTextMatcher(pattern));
  }

  @RunsInEDT
  private void selectItem(@Nonnull JList list, @Nonnull TextMatcher matcher) {
    Pair<Integer, Point> scrollInfo = scrollToItemIfNotSelectedYet(list, matcher, cellReader());
    robot.waitForIdle();
    checkItemFound(list, scrollInfo, matcher);
    if (scrollInfo.second == null) {
      return; // already selected cell.
    }
    robot.click(list, cellCenterIn(scrollInfo));
  }

  /**
   * Clicks the first item matching the given value, using the specified mouse button, the given number times.
   *
   * @param list the target {@code JList}.
   * @param value the value to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public void clickItem(@Nonnull JList list, @Nullable String value, @Nonnull MouseButton button, int times) {
    clickItem(list, new StringTextMatcher(value), button, times);
  }

  /**
   * Clicks the first item matching the given regular expression pattern, using the specified mouse button, the given
   * number times.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression pattern to match.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws LocationUnavailableException if an element matching the given regular expression pattern cannot be found.
   * @since 1.2
   */
  public void clickItem(@Nonnull JList list, @Nonnull Pattern pattern, @Nonnull MouseButton button, int times) {
    clickItem(list, new PatternTextMatcher(pattern), button, times);
  }

  private void clickItem(@Nonnull JList list, @Nonnull TextMatcher matcher, @Nonnull MouseButton button, int times) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, matcher, cellReader());
    robot.waitForIdle();
    checkItemFound(list, scrollInfo, matcher);
    robot.click(list, cellCenterIn(scrollInfo), button, times);
  }

  /**
   * Selects the items under the given indices.
   *
   * @param list the target {@code JList}.
   * @param indices the indices of the items to select.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if any of the indices is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  public void selectItems(final @Nonnull  JList list, final @Nonnull int[] indices) {
    checkNotNullOrEmpty(indices);
    clearSelection(list);
    new MultipleSelectionTemplate(robot) {
      @Override
      int elementCount() {
        return indices.length;
      }

      @Override
      void selectElement(int index) {
        selectItem(list, indices[index]);
      }
    }.multiSelect();
  }

  /**
   * Clears the selection in the given {@code JList}. Since this method does not simulate user input, it does not
   * verifies that the {@code JList} is enabled and showing.
   *
   * @param list the target {@code JList}.
   * @since 1.2
   */
  public void clearSelection(@Nonnull JList list) {
    clearSelectionOf(list);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void clearSelectionOf(final @Nonnull JList list) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        list.clearSelection();
      }
    });
  }

  /**
   * Selects the items in the specified range.
   *
   * @param list the target {@code JList}.
   * @param from the starting point of the selection.
   * @param to the last item to select.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  @RunsInEDT
  public void selectItems(@Nonnull JList list, @Nonnull From from, @Nonnull To to) {
    selectItems(list, from.value, to.value);
  }

  /**
   * Selects the items in the specified range.
   *
   * @param list the target {@code JList}.
   * @param start the starting point of the selection.
   * @param end the last item to select (inclusive.)
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  @RunsInEDT
  public void selectItems(@Nonnull JList list, int start, int end) {
    validateIndicesAndClearSelection(list, start, end);
    selectItem(list, start);
    robot.pressKey(VK_SHIFT);
    try {
      clickItem(list, end, LEFT_BUTTON, 1);
    } finally {
      robot.releaseKey(VK_SHIFT);
    }
  }

  @RunsInEDT
  private static void validateIndicesAndClearSelection(final @Nonnull JList list, final @Nonnull int... indices) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkIndicesInBounds(list, indices);
        list.clearSelection();
      }
    });
  }

  /**
   * Selects the item under the given index using left mouse button once.
   *
   * @param list the target {@code JList}.
   * @param index the index of the item to click.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  @RunsInEDT
  public void selectItem(@Nonnull JList list, int index) {
    Point cellCenter = scrollToItemIfNotSelectedYet(list, index);
    robot.waitForIdle();
    if (cellCenter == null) {
      return; // cell already selected
    }
    robot.click(list, cellCenter);
  }

  /**
   * Clicks the item under the given index, using the specified mouse button, the given number times.
   *
   * @param list the target {@code JList}.
   * @param index the index of the item to click.
   * @param button the button to use.
   * @param times the number of times to click.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  @RunsInEDT
  public void clickItem(@Nonnull JList list, int index, @Nonnull MouseButton button, int times) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    robot.click(list, cellCenter, button, times);
  }

  /**
   * Verifies that the selected item in the {@code JList} matches the given value.
   *
   * @param list the target {@code JList}.
   * @param value the value to match. It can be a regular expression pattern.
   * @throws AssertionError if the selected item does not match the value.
   * @see #replaceCellReader(JListCellReader)
   */
  @RunsInEDT
  public void requireSelection(final @Nonnull JList list, @Nullable String value) {
    String selection = requiredSelection(list);
    verifyThat(selection).as(selectedIndexProperty(list)).isEqualOrMatches(value);
  }

  /**
   * Verifies that the selected item in the {@code JList} matches the given regular expression pattern.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression pattern to match.
   * @throws AssertionError if the selected item does not match the given regular expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #replaceCellReader(JListCellReader)
   * @since 1.2
   */
  @RunsInEDT
  public void requireSelection(@Nonnull JList list, @Nonnull Pattern pattern) {
    String selection = requiredSelection(list);
    verifyThat(selection).as(selectedIndexProperty(list)).matches(pattern);
  }

  private @Nullable String requiredSelection(final @Nonnull JList list) {
    Object selection = singleSelectionValue(list, cellReader());
    if (NO_SELECTION_VALUE == selection) {
      failNoSelection(list);
    }
    return (String) selection;
  }

  /**
   * Verifies that the selected index in the {@code JList} matches the given value.
   *
   * @param list the target {@code JList}.
   * @param index the selection index to match.
   * @throws AssertionError if the selected index does not match the value.
   * @since 1.2
   */
  @RunsInEDT
  public void requireSelection(final @Nonnull JList list, int index) {
    int selectedIndex = selectedIndexOf(list);
    if (selectedIndex == -1) {
      failNoSelection(list);
    }
    assertThat(selectedIndex).as(selectedIndexProperty(list)).isEqualTo(index);
  }

  /**
   * Returns an array of {@code String}s that represents the selection in the given {@code JList}, using this driver's
   * {@link JListCellReader}.
   *
   * @param list the target {@code JList}.
   * @return an array of {@code String}s that represents the selection in the given {@code JList}.
   * @see #replaceCellReader(JListCellReader)
   */
  @RunsInEDT
  public @Nonnull String[] selectionOf(@Nonnull JList list) {
    List<String> selection = selectionValues(list, cellReader());
    return selection.toArray(new String[selection.size()]);
  }

  /**
   * Verifies that the selected items in the {@code JList} match the given values.
   *
   * @param list the target {@code JList}.
   * @param items the values to match. Each value can be a regular expression pattern.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws AssertionError if the selected items do not match the given values.
   */
  @RunsInEDT
  public void requireSelectedItems(@Nonnull JList list, @Nonnull String... items) {
    requireSelectedItems(list, new StringTextMatcher(items));
  }

  /**
   * Verifies that the selected items in the {@code JList} match the given regular expression patterns.
   *
   * @param list the target {@code JList}.
   * @param patterns the regular expression patterns to match.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws NullPointerException if any of the patterns in the array is {@code null}.
   * @throws AssertionError if the selected items do not match the given values.
   * @see #replaceCellReader(JListCellReader)
   * @since 1.2
   */
  @RunsInEDT
  public void requireSelectedItems(@Nonnull JList list, @Nonnull Pattern... patterns) {
    requireSelectedItems(list, new PatternTextMatcher(patterns));
  }

  @RunsInEDT
  private void requireSelectedItems(@Nonnull JList list, @Nonnull TextMatcher matcher) {
    List<String> matchingValues = matchingItemValues(list, matcher, cellReader());
    assertThat(selectionValues(list, cellReader())).as(propertyName(list, SELECTED_INDICES_PROPERTY))
    .isEqualTo(matchingValues);
  }

  /**
   * Verifies that the given item indices are selected in the {@code JList}.
   *
   * @param list the target {@code JList}.
   * @param indices the expected indices of the selected items.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws AssertionError if the selection in the {@code JList} does not match the given one.
   */
  @RunsInEDT
  public void requireSelectedItems(@Nonnull JList list, @Nonnull int... indices) {
    checkNotNullOrEmpty(indices);
    sort(indices);
    assertThat(selectedIndices(list)).as(propertyName(list, SELECTED_INDICES_PROPERTY)).isEqualTo(indices);
  }

  /**
   * Verifies that the {@code JList} does not have a selection.
   *
   * @param list the target {@code JList}.
   * @throws AssertionError if the {@code JList} has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(@Nonnull JList list) {
    assertThat(selectedIndexOf(list)).as(selectedIndexProperty(list)).isEqualTo(-1);
  }

  @RunsInEDT
  private void failNoSelection(@Nonnull JList list) {
    fail(String.format("[%s] No selection", selectedIndexProperty(list).value()));
  }

  @RunsInEDT
  private Description selectedIndexProperty(@Nonnull JList list) {
    return propertyName(list, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Starts a drag operation at the location of the first item matching the given value.
   *
   * @param list the target {@code JList}.
   * @param value the value to match. It can be a regular expression.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @see #replaceCellReader(JListCellReader)
   */
  @RunsInEDT
  public void drag(@Nonnull JList list, @Nullable String value) {
    drag(list, new StringTextMatcher(value));
  }

  /**
   * Starts a drag operation at the location of the first item matching the given regular expression pattern.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression pattern to match.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws NullPointerException if the regular expression pattern is {@code null}.
   * @throws LocationUnavailableException if an element matching the given regular expression pattern cannot be found.
   * @see #replaceCellReader(JListCellReader)
   * @since 1.2
   */
  @RunsInEDT
  public void drag(@Nonnull JList list, @Nonnull Pattern pattern) {
    drag(list, new PatternTextMatcher(pattern));
  }

  private void drag(@Nonnull JList list, @Nonnull TextMatcher matcher) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, matcher, cellReader());
    robot.waitForIdle();
    checkItemFound(list, scrollInfo, matcher);
    super.drag(list, cellCenterIn(scrollInfo));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   *
   * @param list the target {@code JList}.
   * @param value the value to match. It can be a regular expression.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(@Nonnull JList list, @Nullable String value) {
    drop(list, new StringTextMatcher(value));
  }

  /**
   * Ends a drag operation at the location of the first item matching the given regular expression pattern.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression pattern to match.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   * @since 1.2
   */
  public void drop(@Nonnull JList list, @Nonnull Pattern pattern) {
    drop(list, new PatternTextMatcher(pattern));
  }

  private void drop(@Nonnull JList list, @Nonnull TextMatcher matcher) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, matcher, cellReader());
    robot.waitForIdle();
    checkItemFound(list, scrollInfo, matcher);
    super.drop(list, cellCenterIn(scrollInfo));
  }

  private void checkItemFound(@Nonnull JList list, @Nonnull Pair<Integer, Point> scrollInfo,
      @Nonnull TextMatcher matcher) {
    if (ITEM_NOT_FOUND == scrollInfo) {
      throw failMatchingNotFound(list, matcher);
    }
  }

  /**
   * Starts a drag operation at the location of the given index.
   *
   * @param list the target {@code JList}.
   * @param index the given index.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  @RunsInEDT
  public void drag(@Nonnull JList list, int index) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    super.drag(list, cellCenter);
  }

  /**
   * Ends a drag operation at the location of the given index.
   *
   * @param list the target {@code JList}.
   * @param index the given index.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(@Nonnull JList list, int index) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    super.drop(list, cellCenter);
  }

  /**
   * Ends a drag operation at the center of the {@code JList}.
   *
   * @param list the target {@code JList}.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @RunsInEDT
  public void drop(@Nonnull JList list) {
    checkInEdtEnabledAndShowing(list);
    super.drop(list, visibleCenterOf(list));
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the {@code JList}.
   *
   * @param list the target {@code JList}.
   * @param value the value to match. It can be a regular expression pattern.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public JPopupMenu showPopupMenu(@Nonnull JList list, @Nullable String value) {
    return showPopupMenu(list, new StringTextMatcher(value));
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the {@code JList}.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression pattern to match.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws NullPointerException if the regular expression pattern is {@code null}.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @since 1.2
   */
  @RunsInEDT
  public JPopupMenu showPopupMenu(@Nonnull JList list, @Nonnull Pattern pattern) {
    return showPopupMenu(list, new PatternTextMatcher(pattern));
  }

  @RunsInEDT
  private @Nonnull JPopupMenu showPopupMenu(@Nonnull JList list, @Nonnull TextMatcher matcher) {
    Pair<Integer, Point> scrollInfo = scrollToItem(list, matcher, cellReader());
    robot.waitForIdle();
    checkItemFound(list, scrollInfo, matcher);
    return robot.showPopupMenu(list, cellCenterIn(scrollInfo));
  }

  private @Nonnull Point cellCenterIn(@Nonnull Pair<Integer, Point> scrollInfo) {
    return checkNotNull(scrollInfo.second);
  }

  /**
   * Shows a pop-up menu at the location of the specified item in the {@code JList}.
   *
   * @param list the target {@code JList}.
   * @param index the index of the item.
   * @return a driver that manages the displayed pop-up menu.
   * @throws IllegalStateException if the {@code JList} is disabled.
   * @throws IllegalStateException if the {@code JList} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  @RunsInEDT
  public @Nonnull JPopupMenu showPopupMenu(@Nonnull JList list, int index) {
    Point cellCenter = scrollToItem(list, index);
    robot.waitForIdle();
    return robot.showPopupMenu(list, cellCenter);
  }

  /**
   * Returns the coordinates of the first item matching the given value.
   *
   * @param list the target {@code JList}.
   * @param value the value to match.
   * @return the coordinates of the item at the given item.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public @Nonnull Point pointAt(@Nonnull JList list, @Nullable String value) {
    return centerOfMatchingItemCell(list, value, cellReader());
  }

  /**
   * Returns the index of the first item matching the given value.
   *
   * @param list the target {@code JList}
   * @param value the value to match. It can be a regular expression.
   * @return the index of the first item matching the given value.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  @RunsInEDT
  public int indexOf(@Nonnull JList list, @Nullable String value) {
    return indexOf(list, new StringTextMatcher(value));
  }

  /**
   * Returns the index of the first item matching the given regular expression pattern.
   *
   * @param list the target {@code JList}.
   * @param pattern the regular expression pattern to match.
   * @return the index of the first item matching the given regular expression pattern.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  public int indexOf(@Nonnull JList list, @Nonnull Pattern pattern) {
    return indexOf(list, new PatternTextMatcher(pattern));
  }

  @RunsInEDT
  private int indexOf(@Nonnull JList list, @Nonnull TextMatcher matcher) {
    int index = itemIndex(list, matcher, cellReader());
    if (index >= 0) {
      return index;
    }
    throw failMatchingNotFound(list, matcher);
  }

  @RunsInEDT
  private static int itemIndex(final @Nonnull JList list, final @Nonnull TextMatcher matcher,
      final @Nonnull JListCellReader cellReader) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return matchingItemIndex(list, matcher, cellReader);
      }
    });
    return checkNotNull(result);
  }

  private @Nonnull LocationUnavailableException failMatchingNotFound(@Nonnull JList list,
      @Nonnull TextMatcher matcher) {
    String format = "Unable to find item matching the %s %s among the JList contents %s";
    String msg = String.format(format, matcher.description(), matcher.formattedValues(),
        format(contents(list, cellReader())));
    return new LocationUnavailableException(msg);
  }

  /**
   * Returns the {@code String} representation of the element under the given index, using this driver's
   * {@link JListCellReader}.
   *
   * @param list the target {@code JList}.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @see #replaceCellReader(JListCellReader)
   */
  @RunsInEDT
  public @Nullable String value(@Nonnull JList list, int index) {
    return itemValue(list, index, cellReader());
  }

  /**
   * Updates the implementation of {@link JListCellReader} to use when comparing internal values of a {@code JList} and
   * the values expected in a test.
   *
   * @param newCellReader the new {@code JListCellValueReader} to use.
   * @throws NullPointerException if {@code newCellReader} is {@code null}.
   */
  public void replaceCellReader(@Nonnull JListCellReader newCellReader) {
    cellReader = checkNotNull(newCellReader);
  }

  /**
   * Verifies that number of items in the given {@code JList} is equal to the expected one.
   *
   * @param list the target {@code JList}.
   * @param expected the expected number of items.
   * @throws AssertionError if the number of items in the given {@code JList} is not equal to the expected one.
   * @since 1.2
   */
  @RunsInEDT
  public void requireItemCount(@Nonnull JList list, int expected) {
    int actual = itemCountIn(list);
    assertThat(actual).as(propertyName(list, "itemCount")).isEqualTo(expected);
  }

  private @Nonnull JListCellReader cellReader() {
    return cellReader;
  }
}
