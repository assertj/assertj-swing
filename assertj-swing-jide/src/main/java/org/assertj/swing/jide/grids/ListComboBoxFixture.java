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
package org.assertj.swing.jide.grids;

import java.util.regex.Pattern;

import javax.swing.JList;

import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.AbstractComponentFixture;
import org.assertj.swing.fixture.ItemGroupFixture;
import org.assertj.swing.jide.grids.driver.AbstractComboBoxCellReader;
import org.assertj.swing.jide.grids.driver.ListComboBoxDriver;

import com.jidesoft.combobox.ListComboBox;

/**
 * A FEST fixture for driving a {@link com.jidesoft.combobox.ListComboBox}.
 * 
 * @author Peter Murray
 */
public class ListComboBoxFixture extends
    AbstractComponentFixture<ListComboBoxFixture, ListComboBox, ListComboBoxDriver>
    implements ItemGroupFixture {

  public ListComboBoxFixture(Robot robot, String comboBoxName) {
	super(ListComboBoxFixture.class, robot, comboBoxName, ListComboBox.class);
	createDriver(robot);
  }

  public ListComboBoxFixture(Robot robot, ListComboBox target) {
	super(ListComboBoxFixture.class, robot, target);
	createDriver(robot);
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link ListComboBox}</code>,
   * replacing any text. This action is executed only if the <code>{@link
   * ListComboBox}</code> is editable.
   * 
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not showing
   *           on the screen.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not
   *           editable.
   */
  public ListComboBoxFixture replaceText(String text) {
	driver().replaceText(target(), text);
	return this;
  }

  /**
   * Simulates a user selecting the text in the <code>{@link ListComboBox}</code>. This
   * action is executed only if the <code>{@link ListComboBox}</code> is editable.
   * 
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not showing
   *           on the screen.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not
   *           editable.
   */
  public ListComboBoxFixture selectAllText() {
	driver().selectAllText(target());
	return this;
  }

  /**
   * Simulates a user entering the specified text in this fixture's <code>{@link
   * JComboBox}</code>. This action is executed only if the <code>{@link JComboBox}</code> is editable.
   * 
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   *           on the screen.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not
   *           editable.
   */
  public ListComboBoxFixture enterText(String text) {
	driver().enterText(target(), text);
	return this;
  }

  public String editorText() {
	return driver().getEditorText(target());
  }

  public ListComboBoxFixture requireEditorText(String text) {
	driver().requireEditorText(target(), text);
	return this;
  }

  public ListComboBoxFixture requirePopupVisible() {
	driver().requirePopupVisible(target(), true);
	return this;
  }

  public ListComboBoxFixture requirePopupNotVisible() {
	driver().requirePopupVisible(target(), false);
	return this;
  }

  /**
   * Finds and returns the {@link JList} in the pop-up raised by this fixture's <code>{@link JComboBox}</code>.
   * 
   * @return the <code>JList</code> in the pop-up raised by this fixture's <code>JComboBox</code>.
   * @throws org.assertj.swing.exception.ComponentLookupException if the <code>JList</code> in the pop-up could not be
   *           found.
   */
  public JList list() {
	return driver().getList(target());
  }

  @Override
  public ItemGroupFixture requireItemCount(int expected) {
	driver().requireItemCount(target(), expected);
	return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JComboBox}</code>.
   * 
   * @param index the index of the item to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   *           on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the
   *           index of the last item in the <code>JComboBox</code>.
   */
  @Override
  public ListComboBoxFixture selectItem(int index) {
	driver().selectItem(target(), index);
	return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JComboBox}</code>.
   * Value matching is performed by this fixture's <code>{@link
   * org.assertj.swing.cell.JComboBoxCellReader}</code>.
   * 
   * @param text the text of the item to select.
   * @return this fixture.
   * @throws org.assertj.swing.exception.LocationUnavailableException if an element matching
   *           the given text cannot be found.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   *           on the screen.
   * @see #cellReader(org.assertj.swing.jide.grids.driver.AbstractComboBoxCellReader)
   */
  @Override
  public ListComboBoxFixture selectItem(String text) {
	driver().selectItem(target(), text);
	return this;
  }

  /**
   * Returns the <code>String</code> representation of the value of an item in this
   * fixture's <code>{@link JComboBox}</code>, using this fixture's <code>{@link
   * org.assertj.swing.cell.JComboBoxCellReader}</code>.
   * 
   * @param index the index of the item to return.
   * @return the <code>String</code> representation of the value of an item in this
   *         fixture's <code>JComboBox</code>.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the
   *           index of the last item in the <code>JComboBox</code>.
   * @see #cellReader(org.assertj.swing.jide.grids.driver.AbstractComboBoxCellReader)
   */
  @Override
  public String valueAt(int index) {
	return driver().value(target(), index);
  }

  @Override
  public ItemGroupFixture clearSelection() {
	// Set the selection to -1
	throw new UnsupportedOperationException("Not Yet Implemented");
  }

  @Override
  public ItemGroupFixture selectItem(Pattern pattern) {
	throw new UnsupportedOperationException("Not Yet Implemented");
  }

  @Override
  public ItemGroupFixture requireSelection(Pattern pattern) {
	throw new UnsupportedOperationException("Not Yet Implemented");
  }

  @Override
  public ItemGroupFixture requireSelection(int index) {
	throw new UnsupportedOperationException("Not Yet Implemented");
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is editable.
   * 
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is not editable.
   */
  public ListComboBoxFixture requireEditable() {
	driver().requireEditable(target());
	return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is not editable.
   * 
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is editable.
   */
  public ListComboBoxFixture requireNotEditable() {
	driver().requireNotEditable(target());
	return this;
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in this
   * fixture's <code>{@link JComboBox}</code> matches the given text.
   * 
   * @param value the text to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   * @see #cellReader(org.assertj.swing.jide.grids.driver.AbstractComboBoxCellReader)
   */
  @Override
  public ListComboBoxFixture requireSelection(String value) {
	driver().requireSelection(target(), value);
	return this;
  }

  public ListComboBoxFixture requireText(String value) {
	driver().requireText(target(), value);
	return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JComboBox}</code> does not have any
   * selection.
   * 
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> has a selection.
   */
  @Override
  public ListComboBoxFixture requireNoSelection() {
	driver().requireNoSelection(target());
	return this;
  }

  /**
   * Updates the implementation of <code>{@link org.assertj.swing.jide.grids.driver.AbstractComboBoxCellReader}</code>
   * to use when comparing internal values of this fixture's <code>{@link ListComboBox}</code> and the values expected
   * in a test. The default implementation to
   * use is <code>{@link org.assertj.swing.jide.grids.driver.AbstractComboBoxCellReader}</code>.
   * 
   * @param cellReader the new <code>AbstractComboBoxCellReader </code> to use.
   * @return this fixture.
   * @throws NullPointerException if <code>cellReader</code> is <code>null</code>.
   */
  public ListComboBoxFixture cellReader(AbstractComboBoxCellReader cellReader) {
	driver().cellReader(cellReader);
	return this;
  }

  @Override
  public String[] contents() {
	return driver().getContents(target());
  }

  @Override
  protected ListComboBoxDriver createDriver(Robot robot) {
	return new ListComboBoxDriver(robot);
  }

  public ListComboBoxFixture clickPopupButton() {
	driver().clickPopupButton(target());
	return this;
  }
}
