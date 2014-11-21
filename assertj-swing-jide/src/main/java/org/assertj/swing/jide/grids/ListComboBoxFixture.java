/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.grids;

import javax.swing.*;
import java.util.regex.Pattern;
import com.jidesoft.combobox.ListComboBox;
import org.fest.swing.driver.*;
import org.fest.swing.core.*;
import org.fest.swing.fixture.*;
import org.fest.swing.jide.grids.driver.AbstractComboBoxCellReader;
import org.fest.swing.jide.grids.driver.ListComboBoxDriver;
import org.fest.swing.timing.Timeout;

/**
 * A FEST fixture for driving a {@link com.jidesoft.combobox.ListComboBox}.
 * @author Peter Murray
 */
public class ListComboBoxFixture extends ComponentFixture<ListComboBox>
        implements ItemGroupFixture {

  private ListComboBoxDriver _driver;

  public ListComboBoxFixture(Robot robot, String comboBoxName) {
    super(robot, comboBoxName, ListComboBox.class);
    createDriver();
  }

  public ListComboBoxFixture(Robot robot, ListComboBox target) {
    super(robot, target);
    createDriver();
  }

  protected ComponentDriver driver() {
    return _driver;
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link ListComboBox}</code>,
   * replacing any text. This action is executed only if the <code>{@link
   * ListComboBox}</code> is editable.
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not showing
   * on the screen.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not
   * editable.
   */
  public ListComboBoxFixture replaceText(String text) {
    _driver.replaceText(target, text);
    return this;
  }

  /**
   * Simulates a user selecting the text in the <code>{@link ListComboBox}</code>. This
   * action is executed only if the <code>{@link ListComboBox}</code> is editable.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not showing
   * on the screen.
   * @throws IllegalStateException if this fixture's <code>ListComboBox</code> is not
   * editable.
   */
  public ListComboBoxFixture selectAllText() {
    _driver.selectAllText(target);
    return this;
  }

  /**
   * Simulates a user entering the specified text in this fixture's <code>{@link
   * JComboBox}</code>. This action is executed only if the <code>{@link JComboBox}</code>
   * is editable.
   * @param text the text to enter.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not
   * editable.
   */
  public ListComboBoxFixture enterText(String text) {
    _driver.enterText(target, text);
    return this;
  }

  public String editorText() {
    return _driver.getEditorText(target);
  }

  public ListComboBoxFixture requireEditorText(String text) {
    _driver.requireEditorText(target, text);
    return this;
  }

  public ListComboBoxFixture requirePopupVisible() {
    _driver.requirePopupVisible(target, true);
    return this;
  }

  public ListComboBoxFixture requirePopupNotVisible() {
    _driver.requirePopupVisible(target, false);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JComboBox}</code>.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   */
  public ListComboBoxFixture focus() {
    _driver.focus(target);
    return this;
  }

  /**
   * Finds and returns the {@link JList} in the pop-up raised by this fixture's
   * <code>{@link JComboBox}</code>.
   * @return the <code>JList</code> in the pop-up raised by this fixture's
   *         <code>JComboBox</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if the <code>JList</code>
   * in the pop-up could not be found.
   */
  public JList list() {
    return _driver.getList(target);
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's
   * <code>{@link JComboBox}</code>. Modifiers is a mask from the available <code>{@link
   * java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given <code>KeyPressInfo</code> is
   * <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @see KeyPressInfo
   */
  public ListComboBoxFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    _driver.pressAndReleaseKey(target, keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's <code>{@link
   * JComboBox}</code>.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is <code>null</code>.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @see java.awt.event.KeyEvent
   */
  public ListComboBoxFixture pressAndReleaseKeys(int... keyCodes) {
    _driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link
   * JComboBox}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @see java.awt.event.KeyEvent
   */
  public ListComboBoxFixture pressKey(int keyCode) {
    _driver.pressKey(target, keyCode);
    return this;
  }

  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link
   * JComboBox}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @see java.awt.event.KeyEvent
   */
  public ListComboBoxFixture releaseKey(int keyCode) {
    _driver.releaseKey(target, keyCode);
    return this;
  }

  @Override
  public ItemGroupFixture requireItemCount(int expected) {
    _driver.requireItemCount(target, expected);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JComboBox}</code>.
   * @param index the index of the item to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the
   * index of the last item in the <code>JComboBox</code>.
   */
  @Override
  public ListComboBoxFixture selectItem(int index) {
    _driver.selectItem(target, index);
    return this;
  }

  /**
   * Simulates a user selecting an item in this fixture's <code>{@link JComboBox}</code>.
   * Value matching is performed by this fixture's <code>{@link
   * org.fest.swing.cell.JComboBoxCellReader}</code>.
   * @param text the text of the item to select.
   * @return this fixture.
   * @throws org.fest.swing.exception.LocationUnavailableException if an element matching
   * the given text cannot be found.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if this fixture's <code>JComboBox</code> is not showing
   * on the screen.
   * @see #cellReader(org.fest.swing.jide.grids.driver.AbstractComboBoxCellReader)
   */
  @Override
  public ListComboBoxFixture selectItem(String text) {
    _driver.selectItem(target, text);
    return this;
  }

  /**
   * Returns the <code>String</code> representation of the value of an item in this
   * fixture's <code>{@link JComboBox}</code>, using this fixture's <code>{@link
   * org.fest.swing.cell.JComboBoxCellReader}</code>.
   * @param index the index of the item to return.
   * @return the <code>String</code> representation of the value of an item in this
   *         fixture's <code>JComboBox</code>.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the
   * index of the last item in the <code>JComboBox</code>.
   * @see #cellReader(org.fest.swing.jide.grids.driver.AbstractComboBoxCellReader)
   */
  @Override
  public String valueAt(int index) {
    return _driver.value(target, index);
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
   * Asserts that this fixture's <code>{@link JComboBox}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is disabled.
   */
  public ListComboBoxFixture requireEnabled() {
    _driver.requireEnabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's
   * <code>JComboBox</code> is never enabled.
   */
  public ListComboBoxFixture requireEnabled(Timeout timeout) {
    _driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is enabled.
   */
  public ListComboBoxFixture requireDisabled() {
    _driver.requireDisabled(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is not visible.
   */
  public ListComboBoxFixture requireVisible() {
    _driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is visible.
   */
  public ListComboBoxFixture requireNotVisible() {
    _driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is editable.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is not editable.
   */
  public ListComboBoxFixture requireEditable() {
    _driver.requireEditable(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JComboBox}</code> is not editable.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> is editable.
   */
  public ListComboBoxFixture requireNotEditable() {
    _driver.requireNotEditable(target);
    return this;
  }

  /**
   * Verifies that the <code>String</code> representation of the selected item in this
   * fixture's <code>{@link JComboBox}</code> matches the given text.
   * @param value the text to match.
   * @return this fixture.
   * @throws AssertionError if the selected item does not match the given text.
   * @see #cellReader(org.fest.swing.jide.grids.driver.AbstractComboBoxCellReader)
   */
  @Override
  public ListComboBoxFixture requireSelection(String value) {
    _driver.requireSelection(target, value);
    return this;
  }

  public ListComboBoxFixture requireText(String value) {
    _driver.requireText(target, value);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JComboBox}</code> does not have any
   * selection.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JComboBox</code> has a selection.
   */
  @Override
  public ListComboBoxFixture requireNoSelection() {
    _driver.requireNoSelection(target);
    return this;
  }

  /**
   * Updates the implementation of <code>{@link org.fest.swing.jide.grids.driver.AbstractComboBoxCellReader}</code>
   * to use when comparing internal values of this fixture's <code>{@link ListComboBox}</code>
   * and the values expected in a test. The default implementation to
   * use is <code>{@link org.fest.swing.jide.grids.driver.AbstractComboBoxCellReader}</code>.
   * @param cellReader the new <code>AbstractComboBoxCellReader </code> to use.
   * @return this fixture.
   * @throws NullPointerException if <code>cellReader</code> is <code>null</code>.
   */
  public ListComboBoxFixture cellReader(AbstractComboBoxCellReader cellReader) {
    _driver.cellReader(cellReader);
    return this;
  }

  @Override
  public String[] contents() {
    return _driver.getContents(target);
  }

  private void createDriver() {
    _driver = new ListComboBoxDriver(robot);
  }

  public ListComboBoxFixture clickPopupButton() {
    _driver.clickPopupButton(target);
    return this;
  }
}
