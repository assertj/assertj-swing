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
package org.assertj.swing.fixture;

import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JList;

import org.assertj.swing.cell.JListCellReader;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JListDriver;
import org.assertj.swing.util.Range;

/**
 * <p>
 * Supports functional testing of {@code JList}s.
 * </p>
 *
 * <p>
 * The conversion between the values given in tests and the values being displayed by a {@code JList} renderer is
 * performed by a {@link JListCellReader}. This fixture uses a {@link BasicJListCellReader} by default.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 * @author Christian Rösch
 */
public class JListFixture extends AbstractJPopupMenuInvokerFixture<JListFixture, JList, JListDriver> implements
    ItemGroupFixture<JListFixture> {
  /**
   * Creates a new {@link JListFixture}.
   *
   * @param robot performs simulation of user events on a {@code JList}.
   * @param listName the name of the {@code JList} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JList} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JList} is found.
   */
  public JListFixture(@Nonnull Robot robot, @Nullable String listName) {
    super(JListFixture.class, robot, listName, JList.class);
  }

  /**
   * Creates a new {@link JListFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JList}.
   * @param target the {@code JList} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JListFixture(@Nonnull Robot robot, @Nonnull JList target) {
    super(JListFixture.class, robot, target);
  }

  @Override
  protected @Nonnull JListDriver createDriver(@Nonnull Robot robot) {
    return new JListDriver(robot);
  }

  /**
   * Returns the {@code String} representation of the value of an item in this fixture's {@code JList}, using this
   * fixture's {@link JListCellReader}.
   *
   * @param index the index of the item to return.
   * @return the {@code String} representation of the value of an item in this fixture's {@code JList}.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @see #replaceCellReader(JListCellReader)
   */
  @Override
  public @Nullable String valueAt(int index) {
    return driver().value(target(), index);
  }

  /**
   * Returns the {@code String} representation of the elements in this fixture's {@code JList}, using this fixture's
   * {@link JListCellReader}.
   *
   * @return the {@code String} representation of the elements in this fixture's {@code JList}.
   * @see #replaceCellReader(JListCellReader)
   */
  @Override
  public @Nonnull String[] contents() {
    return driver().contentsOf(target());
  }

  /**
   * Returns the {@code String} representation of the selected elements in this fixture's {@code JList}, using this
   * fixture's {@link JListCellReader}.
   *
   * @return the {@code String} representation of the selected elements in this fixture's {@code JList}.
   * @see #replaceCellReader(JListCellReader)
   */
  public @Nonnull String[] selection() {
    return driver().selectionOf(target());
  }

  /**
   * Returns a fixture that manages the list item specified by the given index.
   *
   * @param index of the item.
   * @return a fixture that manages the list item specified by the given index.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public @Nonnull JListItemFixture item(int index) {
    return new JListItemFixture(this, index);
  }

  /**
   * Returns a fixture that manages the list item specified by the given text.
   *
   * @param text the text of the item. It can be a regular expression.
   * @return a fixture that manages the list item specified by the given text.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   */
  public @Nonnull JListItemFixture item(@Nullable String text) {
    return new JListItemFixture(this, driver().indexOf(target(), text));
  }

  /**
   * Returns a fixture that manages the list item whose text matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return a fixture that manages the list item whose text matches the given regular expression pattern.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   */
  public @Nonnull JListItemFixture item(@Nonnull Pattern pattern) {
    return new JListItemFixture(this, driver().indexOf(target(), pattern));
  }

  /**
   * Clears the selection in this fixture's {@code JList}. Since this method does not simulate user input, it does not
   * verifies that this fixture's {@code JList} is enabled and showing.
   *
   * @return this fixture.
   */
  @Override
  public @Nonnull JListFixture clearSelection() {
    driver().clearSelection(target());
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's {@code JList}.
   *
   * @param index the index of the item to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @see #item(int)
   * @see JListItemFixture#select()
   */
  @Override
  public @Nonnull JListFixture selectItem(int index) {
    driver().selectItem(target(), index);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's {@code JList}.
   *
   * @param text the text of the item to select. It can be a regular expression.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @see #item(String)
   * @see JListItemFixture#select()
   * @see #replaceCellReader(JListCellReader)
   */
  @Override
  public @Nonnull JListFixture selectItem(@Nullable String text) {
    driver().selectItem(target(), text);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's {@code JList}. The value of the item to select must match the
   * given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #item(Pattern)
   * @see JListItemFixture#select()
   * @see #replaceCellReader(JListCellReader)
   */
  @Override
  public @Nonnull JListFixture selectItem(@Nonnull Pattern pattern) {
    driver().selectItem(target(), pattern);
    return this;
  }

  /**
   * Simulates a user unselecting an item in this fixture's {@code JList}.
   *
   * @param index the index of the item to unselect.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @see #item(int)
   * @see JListItemFixture#unselect()
   */
  public @Nonnull JListFixture unselectItem(int index) {
    driver().unselectItem(target(), index);
    return this;
  }

  /**
   * Verifies that the {@code String} representation of the selected item in this fixture's {@code JList} matches the
   * given text.
   *
   * @param text the text to match. It can be a regular expression pattern.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   * @see #replaceCellReader(JListCellReader)
   */
  @Override
  public @Nonnull JListFixture requireSelection(@Nullable String text) {
    driver().requireSelection(target(), text);
    return this;
  }

  /**
   * Verifies that the {@code String} representation of the selected item in this fixture's {@code JList} matches the
   * given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given regular expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #replaceCellReader(JListCellReader)
   */
  @Override
  public @Nonnull JListFixture requireSelection(@Nonnull Pattern pattern) {
    driver().requireSelection(target(), pattern);
    return this;
  }

  /**
   * Verifies that the index of the selected item in this fixture's {@code JList} is equal to the given value.
   *
   * @param index the expected selection index.
   * @return this fixture.
   * @throws AssertionError if the selected index is not equal to the given one.
   */
  @Override
  public @Nonnull JListFixture requireSelection(int index) {
    driver().requireSelection(target(), index);
    return this;
  }

  /**
   * Verifies that this fixture's {@code JList} does not have any selection.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JList} has a selection.
   */
  @Override
  public @Nonnull JListFixture requireNoSelection() {
    driver().requireNoSelection(target());
    return this;
  }

  /**
   * Verifies that this fixture's {@code JList} has the expected number of items
   *
   * @param expected the expected number of items.
   * @return this fixture.
   * @throws AssertionError if the number of items in this fixture's {@code JList} is not equal to the expected one.
   */
  @Override
  public @Nonnull JListFixture requireItemCount(int expected) {
    driver().requireItemCount(target(), expected);
    return this;
  }

  /**
   * Simulates a user selecting the items (in the specified range) in this fixture's {@code JList}.
   *
   * @param from the starting point of the selection.
   * @param to the last item to select (inclusive).
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the any index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  public @Nonnull JListFixture selectItems(@Nonnull Range.From from, @Nonnull Range.To to) {
    driver().selectItems(target(), from, to);
    return this;
  }

  /**
   * Simulates a user selecting the specified items in this fixture's {@code JList}.
   *
   * @param indices the indices of the items to select.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IndexOutOfBoundsException if any of the indices is negative or greater than the index of the last item in
   *           the {@code JList}.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   */
  public @Nonnull JListFixture selectItems(@Nonnull int... indices) {
    driver().selectItems(target(), indices);
    return this;
  }

  /**
   * Simulates a user unselecting the specified items in this fixture's {@code JList}.
   *
   * @param indices the indices of the items to unselect.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IndexOutOfBoundsException if any of the indices is negative or greater than the index of the last item in
   *           the {@code JList}.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   */
  public @Nonnull JListFixture unselectItems(@Nonnull int... indices) {
    driver().unselectItems(target(), indices);
    return this;
  }

  /**
   * Simulates a user selecting the specified items in this fixture's {@code JList}. The items to select should match
   * the given values.
   *
   * @param items the text of the items to select. Each {@code String} can be a regular expression.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the any of the given values cannot be found.
   * @see #replaceCellReader(JListCellReader)
   */
  public @Nonnull JListFixture selectItems(@Nonnull String... items) {
    driver().selectItems(target(), items);
    return this;
  }

  /**
   * Simulates a user selecting the specified items in this fixture's {@code JList}. The items to select should select
   * the given regular expression patterns.
   *
   * @param patterns the regular expression patterns to match.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws NullPointerException if any of the regular expression patterns is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the any of the given regular expression patterns cannot
   *           be found.
   * @see #replaceCellReader(JListCellReader)
   */
  public @Nonnull JListFixture selectItems(@Nonnull Pattern... patterns) {
    driver().selectItems(target(), patterns);
    return this;
  }

  /**
   * Simulates a user clicking an item in this fixture's {@code JList}.
   *
   * @param index the index of the item to clicking.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @see #item(int)
   * @see JListItemFixture#click()
   */
  public @Nonnull JListFixture clickItem(int index) {
    driver().clickItem(target(), index, LEFT_BUTTON, 1);
    return this;
  }

  /**
   * Simulates a user clicking an item in this fixture's {@code JList}.
   *
   * @param text the text of the item to select. It can be a regular expression.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @see #item(String)
   * @see JListItemFixture#select()
   * @see #replaceCellReader(JListCellReader)
   */
  public JListFixture clickItem(@Nullable String text) {
    driver().clickItem(target(), text, LEFT_BUTTON, 1);
    return this;
  }

  /**
   * Simulates a user clicking an item in this fixture's {@code JList}. The value of the item to select must match the
   * given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #item(Pattern)
   * @see JListItemFixture#select()
   * @see #replaceCellReader(JListCellReader)
   */
  public @Nonnull JListFixture clickItem(@Nonnull Pattern pattern) {
    driver().clickItem(target(), pattern, LEFT_BUTTON, 1);
    return this;
  }

  void clickItem(int index, @Nonnull MouseButton button, int times) {
    driver().clickItem(target(), index, button, times);
  }

  /**
   * Verifies that the given item indices are selected in this fixture's {@code JList}.
   *
   * @param indices the expected indices of the selected items.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws AssertionError if the selection in this fixture's {@code JList} does not match the given one.
   */
  public @Nonnull JListFixture requireSelectedItems(@Nonnull int... indices) {
    driver().requireSelectedItems(target(), indices);
    return this;
  }

  /**
   * Verifies that the {@code String} representations of the selected items in this fixture's {@code JList} match the
   * given text items.
   *
   * @param items text items to match. Each {@code String} can be a regular expression.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws AssertionError if the selected items do not match the given text items.
   * @see #replaceCellReader(JListCellReader)
   */
  public @Nonnull JListFixture requireSelectedItems(@Nonnull String... items) {
    driver().requireSelectedItems(target(), items);
    return this;
  }

  /**
   * Verifies that the {@code String} representations of the selected items in this fixture's {@code JList} match the
   * given regular expression patterns.
   *
   * @param patterns the regular expression patterns to match.
   * @return this fixture.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   * @throws NullPointerException if any of the patterns in the given array is {@code null}.
   * @throws AssertionError if the selected items do not match the given regular expression patterns.
   * @see #replaceCellReader(JListCellReader)
   */
  public @Nonnull JListFixture requireSelectedItems(@Nonnull Pattern[] patterns) {
    driver().requireSelectedItems(target(), patterns);
    return this;
  }

  /**
   * Simulates a user dragging an item from this fixture's {@code JList}.
   *
   * @param index the index of the item to drag.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  public @Nonnull JListFixture drag(int index) {
    driver().drag(target(), index);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's {@code JList}.
   *
   * @param index the index of the item to drop.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public @Nonnull JListFixture drop(int index) {
    driver().drop(target(), index);
    return this;
  }

  /**
   * Simulates a user dropping an item to this fixture's {@code JList}.
   *
   * @param index the index of the item below the item to drop.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @throws ActionFailedException if there is no drag action in effect.
   * @see #drop(int)
   */
  public @Nonnull JListFixture dropAbove(int index) {
    return drop(index - 1);
  }

  /**
   * Simulates a user dropping an item to this fixture's {@code JList}.
   *
   * @param index the index of the item above the item to drop.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   * @throws ActionFailedException if there is no drag action in effect.
   * @see #drop(int)
   */
  public @Nonnull JListFixture dropBelow(int index) {
    return drop(index + 1);
  }

  /**
   * Simulates a drag operation at the location of the first item in this fixture's {@code JList} matching the given
   * value.
   *
   * @param text the text of the item to drag. It can be a regular expression.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @return this fixture.
   */
  public @Nonnull JListFixture drag(@Nullable String text) {
    driver().drag(target(), text);
    return this;
  }

  /**
   * Ends a drag operation at the location of the first item matching the given value.
   *
   * @param text the text of the item to drop. It can be a regular expression.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public @Nonnull JListFixture drop(@Nullable String text) {
    driver().drop(target(), text);
    return this;
  }

  /**
   * Simulates a drag operation at the location of the first item in this fixture's {@code JList} matching the given
   * regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern in {@code null}.
   * @throws LocationUnavailableException if an element matching the given regular expression pattern cannot be found.
   */
  public @Nonnull JListFixture drag(@Nonnull Pattern pattern) {
    driver().drag(target(), pattern);
    return this;
  }

  /**
   * Ends a drag operation at the location of the first item matching the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern in {@code null}.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  public @Nonnull JListFixture drop(@Nonnull Pattern pattern) {
    driver().drop(target(), pattern);
    return this;
  }

  /**
   * Shows a pop-up menu at the location of the specified item in this fixture's {@code JList}.
   *
   * @param index the index of the item.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JList}.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(int index) {
    return new JPopupMenuFixture(robot(), driver().showPopupMenu(target(), index));
  }

  /**
   * Shows a pop-up menu at the location of the first item matching the given value in this fixture's {@code JList}.
   *
   * @param text the text of the item. It can be a regular expression.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nullable String text) {
    return new JPopupMenuFixture(robot(), driver().showPopupMenu(target(), text));
  }

  /**
   * Shows a pop-up menu at the location of the first item matching the given regular expression pattern in this
   * fixture's {@code JList}.
   *
   * @param pattern the regular expression pattern to match.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull Pattern pattern) {
    return new JPopupMenuFixture(robot(), driver().showPopupMenu(target(), pattern));
  }

  public void replaceCellReader(@Nonnull JListCellReader cellReader) {
    driver().replaceCellReader(cellReader);
  }
}
