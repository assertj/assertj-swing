/*
 * Created on Apr 9, 2007
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
package org.fest.swing.fixture;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComboBox;
import javax.swing.JList;

import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComboBoxDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * <p>
 * Supports functional testing of {@code JComboBox}es.
 * </p>
 *
 * <p>
 * The conversion between the values given in tests and the values being displayed by a {@code JComboBox} renderer is
 * performed by a {@link JComboBoxCellReader}. This fixture uses a {@link JComboBoxCellReader} by default.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JComboBoxFixture extends AbstractJPopupMenuInvokerFixture<JComboBoxFixture, JComboBox, JComboBoxDriver>
    implements EditableComponentFixture<JComboBoxFixture>, ItemGroupFixture<JComboBoxFixture> {
  /**
   * Creates a new {@link JComboBoxFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JComboBox}.
   * @param target the {@code JComboBox} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JComboBoxFixture(@Nonnull Robot robot, @Nonnull JComboBox target) {
    super(JComboBoxFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JComboBoxFixture}.
   *
   * @param robot performs simulation of user events on a {@code JComboBox}.
   * @param comboBoxName the name of the {@code JComboBox} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JComboBox} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JComboBox} is found.
   */
  public JComboBoxFixture(@Nonnull Robot robot, @Nullable String comboBoxName) {
    super(JComboBoxFixture.class, robot, comboBoxName, JComboBox.class);
  }

  @Override
  protected @Nonnull JComboBoxDriver createDriver(@Nonnull Robot robot) {
    return new JComboBoxDriver(robot);
  }

  /**
   * @return the {@code String} representation of the elements in this fixture's {@code JComboBox}, using this fixture's
   * {@link JComboBoxCellReader}.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @Override
  public @Nonnull String[] contents() {
    return driver().contentsOf(target());
  }

  /**
   * Clears the selection in this fixture's {@code JComboBox}. Since this method does not simulate user input, it does
   * not verifies that this fixture's {@code JComboBox} is enabled and showing.
   *
   * @return this fixture.
   * @since 1.2
   */
  @Override
  public @Nonnull JComboBoxFixture clearSelection() {
    driver().clearSelection(target());
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's {@code JComboBox}.
   *
   * @param index the index of the item to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JComboBox}.
   */
  @Override
  public @Nonnull JComboBoxFixture selectItem(int index) {
    driver().selectItem(target(), index);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's {@code JComboBox}. The text of the item to select must match
   * the given {@code String}. Such text is retrieved by this fixture's {@link JComboBoxCellReader}.
   *
   * @param text the text of the item to select. It can be a regular expression.
   * @return this fixture.
   * @throws LocationUnavailableException if an element matching the given text cannot be found.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not showing on the screen.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @Override
  public @Nonnull JComboBoxFixture selectItem(@Nullable String text) {
    driver().selectItem(target(), text);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's {@code JComboBox}. The text of the item to select must match
   * the given regular expression pattern. Such text is retrieved by this fixture's {@link JComboBoxCellReader}.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws LocationUnavailableException if an element matching the given pattern cannot be found.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #replaceCellReader(JComboBoxCellReader)
   * @since 1.2
   */
  @Override
  public @Nonnull JComboBoxFixture selectItem(@Nonnull Pattern pattern) {
    driver().selectItem(target(), pattern);
    return this;
  }

  /**
   * Returns the {@code String} representation of the value of an item in this fixture's {@code JComboBox}, using this
   * fixture's {@link JComboBoxCellReader}.
   *
   * @param index the index of the item to return.
   * @return the {@code String} representation of the value of an item in this fixture's {@code JComboBox}.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JComboBox}.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @Override
  public @Nullable String valueAt(int index) {
    return driver().value(target(), index);
  }

  /**
   * Verifies that the {@code String} representation of the selected item in this fixture's {@code JComboBox} matches
   * the given text.
   *
   * @param value the text to match. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @Override
  public @Nonnull JComboBoxFixture requireSelection(@Nullable String value) {
    driver().requireSelection(target(), value);
    return this;
  }

  /**
   * Verifies that the {@code String} representation of the selected item in this fixture's {@code JComboBox} matches
   * the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the selected item does not match the given regular expression pattern.
   * @see #replaceCellReader(JComboBoxCellReader)
   * @since 1.2
   */
  @Override
  public @Nonnull JComboBoxFixture requireSelection(@Nonnull Pattern pattern) {
    driver().requireSelection(target(), pattern);
    return this;
  }

  /**
   * Verifies that the index of the selected item in this fixture's {@code JComboBox} is equal to the given value.
   *
   * @param index the expected selection index.
   * @return this fixture.
   * @throws AssertionError if the selected index is not equal to the given one.
   * @since 1.2
   */
  @Override
  public @Nonnull JComboBoxFixture requireSelection(int index) {
    driver().requireSelection(target(), index);
    return this;
  }

  /**
   * Verifies that this fixture's {@code JComboBox} does not have any selection.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JComboBox} has a selection.
   */
  @Override
  public @Nonnull JComboBoxFixture requireNoSelection() {
    driver().requireNoSelection(target());
    return this;
  }

  /**
   * Verifies that this fixture's {@code JComboBox} has the expected number of items
   *
   * @param expected the expected number of items.
   * @return this fixture.
   * @throws AssertionError if the number of items in this fixture's {@code JComboBox} is not equal to the expected one.
   * @since 1.2
   */
  @Override
  public @Nonnull JComboBoxFixture requireItemCount(int expected) {
    driver().requireItemCount(target(), expected);
    return this;
  }

  /**
   * Returns the selected value of this fixture's {@code JComboBox} as plain text. This method returns {@code null} if
   * the {code JComboBox} does not have any selection.
   *
   * @return the selected value of this fixture's {code JComboBox} as plain text, or {@code null} if the {code
   *         JComboBox} does not have any selection.
   * @since 1.3
   */
  public @Nullable String selectedItem() {
    return driver().selectedItemOf(target());
  }

  /**
   * Asserts that this fixture's {@code JComboBox} is editable.
   *
   * @throws AssertionError if this fixture's {@code JComboBox} is not editable.
   * @return this fixture.
   */
  @Override
  @Nonnull public JComboBoxFixture requireEditable() {
    driver().requireEditable(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JComboBox} is not editable.
   *
   * @throws AssertionError if this fixture's {@code JComboBox} is editable.
   * @return this fixture.
   */
  @Override
  @Nonnull public JComboBoxFixture requireNotEditable() {
    driver().requireNotEditable(target());
    return this;
  }

  /**
   * Simulates a user entering the specified text in the {@code JComboBox}, replacing any text. This action is executed
   * only if the {@code JComboBox} is editable.
   *
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not showing on the screen.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not editable.
   */
  public @Nonnull JComboBoxFixture replaceText(@Nonnull String text) {
    driver().replaceText(target(), text);
    return this;
  }

  /**
   * Simulates a user selecting the text in the {@code JComboBox}. This action is executed only if the {@code JComboBox}
   * is editable.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not showing on the screen.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not editable.
   */
  public @Nonnull JComboBoxFixture selectAllText() {
    driver().selectAllText(target());
    return this;
  }

  /**
   * Simulates a user entering the specified text in this fixture's {@code JComboBox}. This action is executed only if
   * the {@code JComboBox} is editable.
   *
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not showing on the screen.
   * @throws IllegalStateException if this fixture's {@code JComboBox} is not editable.
   * @throws ActionFailedException if this fixture's {@code JComboBox} does not have an editor.
   */
  public @Nonnull JComboBoxFixture enterText(@Nonnull String text) {
    driver().enterText(target(), text);
    return this;
  }

  /**
   * Finds and returns the {@code JList} in the pop-up raised by this fixture's {@code JComboBox}.
   *
   * @return the {@code JList} in the pop-up raised by this fixture's {@code JComboBox}.
   * @throws ComponentLookupException if the {@code JList} in the pop-up could not be found.
   */
  public @Nonnull JList list() {
    return driver().dropDownList();
  }

  public void replaceCellReader(@Nonnull JComboBoxCellReader cellReader) {
    driver().replaceCellReader(cellReader);
  }
}
