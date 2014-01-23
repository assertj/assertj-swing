/*
 * Created on Jan 21, 2008
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

import static javax.swing.text.DefaultEditorKit.selectAllAction;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JComboBoxContentQuery.contents;
import static org.fest.swing.driver.JComboBoxEditableQuery.isEditable;
import static org.fest.swing.driver.JComboBoxItemCountQuery.itemCountIn;
import static org.fest.swing.driver.JComboBoxItemIndexPreconditions.checkItemIndexInBounds;
import static org.fest.swing.driver.JComboBoxMatchingItemQuery.matchingItemIndex;
import static org.fest.swing.driver.JComboBoxSelectedIndexQuery.selectedIndexOf;
import static org.fest.swing.driver.JComboBoxSelectionValueQuery.selection;
import static org.fest.swing.driver.JComboBoxSetSelectedIndexTask.setSelectedIndex;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Arrays.format;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.quote;

import java.awt.Component;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.PatternTextMatcher;
import org.fest.swing.util.StringTextMatcher;
import org.fest.swing.util.TextMatcher;
import org.fest.util.InternalApi;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Supports functional testing of {@code JComboBox}es.
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
public class JComboBoxDriver extends JComponentDriver {
  private static final String EDITABLE_PROPERTY = "editable";
  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  private final JListDriver listDriver;
  private final JComboBoxDropDownListFinder dropDownListFinder;

  private JComboBoxCellReader cellReader;

  /**
   * Creates a new {@link JComboBoxDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public JComboBoxDriver(@Nonnull Robot robot) {
    super(robot);
    listDriver = new JListDriver(robot);
    dropDownListFinder = new JComboBoxDropDownListFinder(robot);
    replaceCellReader(new BasicJComboBoxCellReader());
  }

  /**
   * Returns an array of {@code String}s that represents the contents of the given {@code JComboBox} list. The
   * {@code String} representation of each element is performed using this driver's {@link JComboBoxCellReader}.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @return an array of {@code String}s that represent the contents of the given {@code JComboBox} list.
   * @see #value(JComboBox, int)
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @RunsInEDT
  public @Nonnull String[] contentsOf(@Nonnull JComboBox comboBox) {
    return contents(comboBox, cellReader());
  }

  /**
   * Selects the first item matching the given text in the {@code JComboBox}. The text of the {@code JComboBox} items is
   * obtained by this fixture's {@link JComboBoxCellReader}.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param value the value to match. It can be a regular expression.
   * @throws LocationUnavailableException if an element matching the given value cannot be found.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @RunsInEDT
  public void selectItem(@Nonnull JComboBox comboBox, @Nullable String value) {
    selectItem(comboBox, new StringTextMatcher(value));
  }

  /**
   * Selects the first item matching the given regular expression pattern in the {@code JComboBox}. The text of the
   * {@code JComboBox} items is obtained by this fixture's {@link JComboBoxCellReader}.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param pattern the regular expression pattern to match.
   * @throws LocationUnavailableException if an element matching the given pattern cannot be found.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #replaceCellReader(JComboBoxCellReader)
   * @since 1.2
   */
  @RunsInEDT
  public void selectItem(@Nonnull JComboBox comboBox, @Nonnull Pattern pattern) {
    selectItem(comboBox, new PatternTextMatcher(pattern));
  }

  @RunsInEDT
  private void selectItem(@Nonnull JComboBox comboBox, @Nonnull TextMatcher matcher) {
    int index = matchingItemIndex(comboBox, matcher, cellReader());
    if (index < 0) {
      String format = "Unable to find item matching %s among the JComboBox contents: ";
      String msg = String.format(format, matcher.description(), format(contentsOf(comboBox)));
      throw new LocationUnavailableException(msg);
    }
    selectItem(comboBox, index);
  }

  /**
   * Verifies that the {@code String} representation of the selected item in the {@code JComboBox} matches the given
   * text.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param value the text to match. It can be a regular expression.
   * @throws AssertionError if the selected item does not match the given value.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  @RunsInEDT
  public void requireSelection(@Nonnull JComboBox comboBox, @Nullable String value) {
    String selection = requiredSelectionOf(comboBox);
    verifyThat(selection).as(selectedIndexProperty(comboBox)).isEqualOrMatches(value);
  }

  /**
   * Verifies that the {@code String} representation of the selected item in the {@code JComboBox} matches the given
   * regular expression pattern.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param pattern the regular expression pattern to match.
   * @throws AssertionError if the selected item does not match the given regular expression pattern.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @see #replaceCellReader(JComboBoxCellReader)
   * @since 1.2
   */
  @RunsInEDT
  public void requireSelection(@Nonnull JComboBox comboBox, @Nonnull Pattern pattern) {
    String selection = requiredSelectionOf(comboBox);
    verifyThat(selection).as(selectedIndexProperty(comboBox)).matches(pattern);
  }

  @RunsInEDT
  private @Nullable String requiredSelectionOf(@Nonnull JComboBox comboBox) throws AssertionError {
    Pair<Boolean, String> selection = selection(comboBox, cellReader());
    boolean hasSelection = selection.first;
    if (!hasSelection) {
      throw failNoSelection(comboBox);
    }
    return selection.second;
  }

  /**
   * Verifies that the index of the selected item in the {@code JComboBox} is equal to the given value.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param index the expected selection index.
   * @throws AssertionError if the selection index is not equal to the given value.
   * @since 1.2
   */
  @RunsInEDT
  public void requireSelection(@Nonnull JComboBox comboBox, int index) {
    int selectedIndex = selectedIndexOf(comboBox);
    if (selectedIndex == -1) {
      failNoSelection(comboBox);
    }
    assertThat(selectedIndex).as(selectedIndexProperty(comboBox)).isEqualTo(index);
  }

  private @Nonnull AssertionError failNoSelection(@Nonnull JComboBox comboBox) {
    throw fail(String.format("[%s] No selection", selectedIndexProperty(comboBox).value()));
  }

  /**
   * Verifies that the {@code JComboBox} does not have any selection.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @throws AssertionError if the {@code JComboBox} has a selection.
   */
  @RunsInEDT
  public void requireNoSelection(@Nonnull JComboBox comboBox) {
    Pair<Boolean, String> selection = selection(comboBox, cellReader());
    boolean hasSelection = selection.first;
    if (!hasSelection) {
      return;
    }
    String format = "[%s] Expecting no selection, but found:<%s>";
    fail(String.format(format, selectedIndexProperty(comboBox).value(), quote(selection.second)));
  }

  /**
   * Returns the {@code String} representation of the element under the given index, using this driver's
   * {@link JComboBoxCellReader}.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JComboBox}.
   * @see #replaceCellReader(JComboBoxCellReader)
   */
  public @Nullable String value(@Nonnull JComboBox comboBox, int index) {
    return valueAsText(comboBox, index, cellReader());
  }

  @RunsInEDT
  private static @Nullable String valueAsText(final @Nonnull JComboBox comboBox, final int index,
      final @Nonnull JComboBoxCellReader cellReader) {
    return execute(new GuiQuery<String>() {
      @Override
      protected @Nullable String executeInEDT() {
        checkItemIndexInBounds(comboBox, index);
        return cellReader.valueAt(comboBox, index);
      }
    });
  }

  private @Nonnull Description selectedIndexProperty(@Nonnull JComboBox comboBox) {
    return propertyName(comboBox, SELECTED_INDEX_PROPERTY);
  }

  /**
   * Clears the selection in the given {@code JComboBox}. Since this method does not simulate user input, it does not
   * verifies that the {@code JComboBox} is enabled and showing.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @since 1.2
   */
  public void clearSelection(@Nonnull JComboBox comboBox) {
    setSelectedIndex(comboBox, -1);
    robot.waitForIdle();
  }

  /**
   * Selects the item under the given index in the {@code JComboBox}.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param index the given index.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the index of the last item in the
   *           {@code JComboBox}.
   */
  @RunsInEDT
  public void selectItem(final @Nonnull JComboBox comboBox, int index) {
    validateCanSelectItem(comboBox, index);
    showDropDownList(comboBox);
    selectItemAtIndex(comboBox, index);
    hideDropDownListIfVisible(comboBox);
  }

  @RunsInEDT
  private static void validateCanSelectItem(final @Nonnull JComboBox comboBox, final int index) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(comboBox);
        checkItemIndexInBounds(comboBox, index);
      }
    });
  }

  @VisibleForTesting
  @RunsInEDT
  void showDropDownList(@Nonnull JComboBox comboBox) {
    // Location of pop-up button activator is LAF-dependent
    dropDownVisibleThroughUIDelegate(comboBox, true);
  }

  @RunsInEDT
  private void selectItemAtIndex(@Nonnull final JComboBox comboBox, final int index) {
    JList dropDownList = dropDownListFinder.findDropDownList();
    if (dropDownList != null) {
      listDriver.selectItem(dropDownList, index);
      return;
    }
    setSelectedIndex(comboBox, index);
    robot.waitForIdle();
  }

  @RunsInEDT
  private void hideDropDownListIfVisible(@Nonnull JComboBox comboBox) {
    dropDownVisibleThroughUIDelegate(comboBox, false);
  }

  @RunsInEDT
  private void dropDownVisibleThroughUIDelegate(@Nonnull final JComboBox comboBox, final boolean visible) {
    execute(new GuiTask() {
      @Override protected void executeInEDT() {
        comboBox.setPopupVisible(visible);
      }
    });
    robot.waitForIdle();
  }

  /**
   * Simulates a user entering the specified text in the {@code JComboBox}, replacing any text. This action is executed
   * only if the {@code JComboBox} is editable.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param text the text to enter.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @throws IllegalStateException if the {@code JComboBox} is not editable.
   */
  @RunsInEDT
  public void replaceText(@Nonnull JComboBox comboBox, @Nonnull String text) {
    selectAllText(comboBox);
    enterText(comboBox, text);
  }

  /**
   * Simulates a user selecting the text in the {@code JComboBox}. This action is executed only if the {@code JComboBox}
   * is editable.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @throws IllegalStateException if the {@code JComboBox} is not editable.
   */
  @RunsInEDT
  public void selectAllText(@Nonnull JComboBox comboBox) {
    Component editor = accessibleEditorOf(comboBox);
    if (!(editor instanceof JComponent)) {
      return;
    }
    focus(editor);
    invokeAction((JComponent) editor, selectAllAction);
  }

  @RunsInEDT
  private static @Nullable Component accessibleEditorOf(final @Nonnull JComboBox comboBox) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected @Nullable Component executeInEDT() {
        checkAccessibleEditor(comboBox);
        return editorComponentOf(comboBox);
      }
    });
  }

  /**
   * Simulates a user entering the specified text in the {@code JComboBox}. This action is executed only if the
   * {@code JComboBox} is editable.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param text the text to enter.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @throws IllegalStateException if the {@code JComboBox} is not editable.
   * @throws ActionFailedException if the {@code JComboBox} does not have an editor.
   */
  @RunsInEDT
  public void enterText(final @Nonnull JComboBox comboBox, @Nonnull String text) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkAccessibleEditor(comboBox);
      }
    });
    Component editor = editorComponentOf(comboBox);
    // this will never happen...at least in Sun's JVM
    if (editor == null) {
      throw actionFailure("JComboBox does not have an editor");
    }
    focusAndWaitForFocusGain(editor);
    robot.enterText(text);
  }

  /**
   * Simulates a user pressing and releasing the given keys on the {@code JComboBox}.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param keyCodes one or more codes of the keys to press.
   * @throws NullPointerException if the given array of codes is {@code null}.
   * @throws IllegalStateException if the {@code JComboBox} is disabled.
   * @throws IllegalStateException if the {@code JComboBox} is not showing on the screen.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see java.awt.event.KeyEvent
   */
  @RunsInEDT
  public void pressAndReleaseKeys(@Nonnull JComboBox comboBox, @Nonnull int... keyCodes) {
    checkNotNull(keyCodes);
    checkInEdtEnabledAndShowing(comboBox);
    Component target = editorIfEditable(comboBox);
    if (target == null) {
      target = comboBox;
    }
    focusAndWaitForFocusGain(target);
    robot.pressAndReleaseKeys(keyCodes);
  }

  @RunsInEDT
  private static Component editorIfEditable(final JComboBox comboBox) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected Component executeInEDT() {
        if (!comboBox.isEditable()) {
          return null;
        }
        return editorComponent(comboBox);
      }
    });
  }

  @RunsInCurrentThread
  private static void checkAccessibleEditor(@Nonnull JComboBox comboBox) {
    checkEnabledAndShowing(comboBox);
    if (!comboBox.isEditable()) {
      String msg = String.format("Expecting component %s to be editable", format(comboBox));
      throw new IllegalStateException(msg);
    }
  }

  @RunsInEDT
  private static @Nullable Component editorComponentOf(final @Nonnull JComboBox comboBox) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected @Nullable Component executeInEDT() {
        return editorComponent(comboBox);
      }
    });
  }

  @RunsInCurrentThread
  private static @Nullable Component editorComponent(@Nonnull JComboBox comboBox) {
    ComboBoxEditor editor = comboBox.getEditor();
    if (editor == null) {
      return null;
    }
    return editor.getEditorComponent();
  }

  /**
   * Find the {@code JList} in the pop-up raised by the {@code JComboBox}, if the LAF actually uses one.
   * 
   * @return the found {@code JList}.
   * @throws ComponentLookupException if the {@code JList} in the pop-up could not be found.
   */
  @RunsInEDT
  public @Nonnull JList dropDownList() {
    JList list = dropDownListFinder.findDropDownList();
    if (list == null) {
      throw new ComponentLookupException("Unable to find the pop-up list for the JComboBox");
    }
    return list;
  }

  /**
   * Asserts that the given {@code JComboBox} is editable.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @throws AssertionError if the {@code JComboBox} is not editable.
   */
  @RunsInEDT
  public void requireEditable(final @Nonnull JComboBox comboBox) {
    checkEditable(comboBox, true);
  }

  /**
   * Asserts that the given {@code JComboBox} is not editable.
   * 
   * @param comboBox the given {@code JComboBox}.
   * @throws AssertionError if the {@code JComboBox} is editable.
   */
  @RunsInEDT
  public void requireNotEditable(@Nonnull JComboBox comboBox) {
    checkEditable(comboBox, false);
  }

  @RunsInEDT
  private void checkEditable(@Nonnull JComboBox comboBox, boolean expected) {
    assertThat(isEditable(comboBox)).as(editableProperty(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private static Description editableProperty(@Nonnull JComboBox comboBox) {
    return propertyName(comboBox, EDITABLE_PROPERTY);
  }

  /**
   * Updates the implementation of {@link JComboBoxCellReader} to use when comparing internal values of a
   * {@code JComboBox} and the values expected in a test.
   * 
   * @param newCellReader the new {@code JComboBoxCellValueReader} to use.
   * @throws NullPointerException if {@code newCellReader} is {@code null}.
   */
  public void replaceCellReader(@Nonnull JComboBoxCellReader newCellReader) {
    cellReader = checkNotNull(newCellReader);
  }

  /**
   * Verifies that number of items in the given {@code JComboBox} is equal to the expected one.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @param expected the expected number of items.
   * @throws AssertionError if the number of items in the given {@code JComboBox} is not equal to the expected one.
   * @since 1.2
   */
  @RunsInEDT
  public void requireItemCount(@Nonnull JComboBox comboBox, int expected) {
    int actual = itemCountIn(comboBox);
    assertThat(actual).as(propertyName(comboBox, "itemCount")).isEqualTo(expected);
  }

  /**
   * Returns the selected value of the given {@code JComboBox} as plain text. This method returns {@code null} if the
   * {code JComboBox} does not have any selection.
   * 
   * @param comboBox the target {@code JComboBox}.
   * @return the selected value of the given {code JComboBox} as plain text, or {@code null} if the {code JComboBox}
   *         does not have any selection.
   * @since 1.3
   */
  public @Nullable String selectedItemOf(@Nonnull JComboBox comboBox) {
    return selection(comboBox, cellReader()).second;
  }

  private @Nonnull JComboBoxCellReader cellReader() {
    return cellReader;
  }
}
