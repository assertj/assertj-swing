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
package org.assertj.swing.jide.grids.driver;

import static javax.swing.text.DefaultEditorKit.deletePrevCharAction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.ComboBoxEditor;
import javax.swing.JComponent;

import org.assertj.core.description.Description;
import org.assertj.core.util.Strings;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.driver.JComponentDriver;
import org.assertj.swing.exception.LocationUnavailableException;
import org.assertj.swing.query.ComponentEnabledQuery;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.combobox.PopupPanel;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverter;

/**
 * A driver for an {@link com.jidesoft.combobox.AbstractComboBox}. This is loosely based
 * on the {@link org.assertj.swing.driver.JComboBoxDriver} class so a familiar usage pattern
 * will exist between Swing combo boxes and JIDE combo boxes.
 *
 * @author Peter Murray
 */
public class AbstractComboBoxDriver extends JComponentDriver {

  private static final String EDITABLE_PROPERTY = "editable";

  private static final String SELECTED_INDEX_PROPERTY = "selectedIndex";

  protected AbstractComboBoxCellReader _cellReader;

  public AbstractComboBoxDriver(org.assertj.swing.core.Robot robot) {
    super(robot);
    cellReader(new AbstractComboBoxCellReader());
  }

  /**
   * Deletes the text of the {@code AbstractComboBox}.
   *
   * @param comboBox the target {@code AbstractComboBox}.
   * @throws IllegalStateException if the {@code AbstractComboBox} is disabled.
   * @throws IllegalStateException if the {@code AbstractComboBox} is not showing on the screen.
   */
  @RunsInEDT
  public void deleteText(AbstractComboBox comboBox) {
    selectAllText(comboBox);
    Component editor = accessibleEditorOf(comboBox);
    if (!(editor instanceof JComponent)) {
      return;
    }
    focus(editor);
    invokeAction((JComponent) editor, deletePrevCharAction);
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link com.jidesoft.combobox.AbstractComboBox}</code>,
   * replacing any text. This action is executed only if the <code>{@link
   * com.jidesoft.combobox.AbstractComboBox}</code> is editable.
   *
   * @param comboBox the target <code>AbstractComboBox</code>.
   * @param text the text to enter.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is not showing on the
   *           screen.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is not editable.
   */
  @RunsInEDT
  public void replaceText(AbstractComboBox comboBox, String text) {
    checkNotNull(text);
    if (text.isEmpty()) {
      deleteText(comboBox);
    } else {
      selectAllText(comboBox);
      enterText(comboBox, text);
    }
  }

  /**
   * Simulates a user selecting the text in the <code>{@link com.jidesoft.combobox.AbstractComboBox}</code>. This
   * action is executed only if the <code>{@link com.jidesoft.combobox.AbstractComboBox}</code> is editable.
   *
   * @param comboBox the target <code>AbstractComboBox</code>.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is not showing on the
   *           screen.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is not editable.
   */
  @RunsInEDT
  public void selectAllText(AbstractComboBox comboBox) {
    Component editor = accessibleEditorOf(comboBox);
    if (!(editor instanceof JComponent)) {
      return;
    }
    focus(editor);
    invokeAction((JComponent) editor, javax.swing.text.DefaultEditorKit.selectAllAction);
  }

  @RunsInEDT
  private static Component accessibleEditorOf(final AbstractComboBox comboBox) {
    return org.assertj.swing.edt.GuiActionRunner.execute(() -> {
      AbstractComboBoxAccessibleEditorValidator.validateEditorIsAccessible(comboBox);
      return comboBox.getEditor().getEditorComponent();
    });
  }

  /**
   * We need to cater for the editor component if we are editable. When typing into an
   * editable AbstractComboBox, the focus will move to the Editor, not the ComboBox
   * instance which will result in AssertJ-Swing failing on waiting for focus to move to the
   * comboBox.
   *
   * @param c the target component.
   * @param keyCodes one or more codes of the keys to press.
   */
  @Override
  @RunsInEDT
  public void pressAndReleaseKeys(Component c, int... keyCodes) {
    Component interactionComponent = getInteractionComponent(c);
    robot.moveMouse(interactionComponent);
    robot.focus(interactionComponent);
    super.pressAndReleaseKeys(interactionComponent, keyCodes);
  }

  /**
   * We need to cater for the editor component if we are editable. When typing into an
   * editable AbstractComboBox, the focus will move to the Editor, not the ComboBox
   * instance which will result in AssertJ-Swing failing on waiting for focus to move to the
   * comboBox.
   *
   * @param c the target component.
   * @param keyPressInfo specifies the key and modifiers to press.
   */
  @Override
  @RunsInEDT
  public void pressAndReleaseKey(Component c, KeyPressInfo keyPressInfo) {
    Component interactionComponent = getInteractionComponent(c);
    super.pressAndReleaseKey(interactionComponent,
                             keyPressInfo.keyCode(),
                             keyPressInfo.modifiers());
  }

  /**
   * We need to cater for the editor component if we are editable. When typing into an
   * editable AbstractComboBox, the focus will move to the Editor, not the ComboBox
   * instance which will result in AssertJ-Swing failing on waiting for focus to move to the
   * comboBox.
   *
   * @param c the target component.
   * @param keyCode the code of the key to press.
   * @param modifiers the given modifiers.
   */
  @Override
  @RunsInEDT
  public void pressAndReleaseKey(Component c, int keyCode, int[] modifiers) {
    Component interactionComponent = getInteractionComponent(c);
    super.pressAndReleaseKey(interactionComponent, keyCode, modifiers);
  }

  @Override
  @RunsInEDT
  public void pressKey(Component c, int keyCode) {
    Component interactionComponent = getInteractionComponent(c);
    super.pressKey(interactionComponent, keyCode);
  }

  @Override
  public void pressKeyWhileRunning(Component c, int keyCode, Runnable runnable) {
    Component interactionComponent = getInteractionComponent(c);
    super.pressKeyWhileRunning(interactionComponent, keyCode, runnable);
  }

  /**
   * Simulates a user releasing the given key on the <code>{@link Component}</code>.
   *
   * @param c the target component.
   * @param keyCode the code of the key to release.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if the <code>Component</code> is disabled, or is not
   *           showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  @Override
  @RunsInEDT
  public void releaseKey(Component c, int keyCode) {
    Component interactionComponent = getInteractionComponent(c);
    super.releaseKey(interactionComponent, keyCode);
  }

  public void requireSelection(AbstractComboBox comboBox, String value) {
    String selectedItem = _cellReader.valueAsText(comboBox, comboBox.getSelectedItem());
    if (selectedItem == null) {
      fail(org.assertj.core.util.Strings.concat("[",
                                                selectedIndexProperty(comboBox),
                                                "] No selection"));
    }
    assertThat(selectedItem).as(selectedIndexProperty(comboBox)).isEqualTo(value);
  }

  /**
   * Simulates a user entering the specified text in the <code>{@link com.jidesoft.combobox.AbstractComboBox}</code>.
   * This action is executed only if the <code>{@link com.jidesoft.combobox.AbstractComboBox}</code> is editable.
   *
   * @param comboBox the target <code>AbstractComboBox</code>.
   * @param text the text to enter.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>AbstractComboBox</code> is not editable or
   *           is not showing on the screen.
   */
  @RunsInEDT
  public void enterText(AbstractComboBox comboBox, String text) {
    inEdtValidateEditorIsAccessible(comboBox);
    focus(comboBox);
    robot.enterText(text);
  }

  @RunsInEDT
  private static void inEdtValidateEditorIsAccessible(final AbstractComboBox comboBox) {
    org.assertj.swing.edt.GuiActionRunner.execute(() -> AbstractComboBoxAccessibleEditorValidator.validateEditorIsAccessible(comboBox));
  }

  /**
   * Asserts that the given <code>{@link com.jidesoft.combobox.AbstractComboBox}</code> is editable.
   *
   * @param comboBox the target <code>AbstractComboBox</code>.
   * @throws AssertionError if the <code>AbstractComboBox</code> is not editable.
   */
  @RunsInEDT
  public void requireEditable(final AbstractComboBox comboBox) {
    assertEditable(comboBox, true);
  }

  /**
   * Asserts that the given <code>{@link com.jidesoft.combobox.AbstractComboBox}</code> is not editable.
   *
   * @param comboBox the given <code>AbstractComboBox</code>.
   * @throws AssertionError if the <code>AbstractComboBox</code> is editable.
   */
  @RunsInEDT
  public void requireNotEditable(AbstractComboBox comboBox) {
    assertEditable(comboBox, false);
  }

  @RunsInEDT
  public void requireNotEnabled(AbstractComboBox comboBox) {
    assertEnabled(comboBox, false);
  }

  @RunsInEDT
  public void requireEnabled(AbstractComboBox comboBox) {
    assertEnabled(comboBox, true);
  }

  @RunsInEDT
  private void assertEditable(AbstractComboBox comboBox, boolean expected) {
    assertThat(AbstractComboBoxEditableQuery.isEditable(comboBox))
                                                                  .as(editableProperty(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private void assertEnabled(AbstractComboBox comboBox, boolean expected) {
    assertThat(ComponentEnabledQuery.isEnabled(comboBox))
                                                         .as(editableProperty(comboBox)).isEqualTo(expected);
  }

  @RunsInEDT
  private static Description editableProperty(AbstractComboBox comboBox) {
    return propertyName(comboBox, EDITABLE_PROPERTY);
  }

  public String getStringRepresentation(AbstractComboBox box, Object o) {
    ObjectConverter converter = box.getConverter();
    ConverterContext context = box.getConverterContext();

    if (converter == null) {
      return String.valueOf(o);
    }
    return converter.toString(o, context);
  }

  AbstractComboBox.EditorComponent getEditor(final AbstractComboBox comboBox) {
    return execute(() -> (AbstractComboBox.EditorComponent) comboBox.getEditor());
  }

  public String getEditorText(final AbstractComboBox comboBox) {
    return execute(() -> ((AbstractComboBox.EditorComponent) comboBox.getEditor()).getText());
  }

  public void requireEditorText(final AbstractComboBox comboBox, String requiredText) {
    robot.waitForIdle();
    String currentText = getEditorText(comboBox);
    assertThat(currentText).as("Editor Text Value").isEqualTo(requiredText);
  }

  @RunsInEDT
  PopupPanel showPopup(final AbstractComboBox comboBox) {
    return comboBox.getPopupPanel();
  }

  public void requireNoSelection(AbstractComboBox comboBox) {
    Object selectedItem = comboBox.getSelectedItem();
    if (selectedItem != null) {
      fail(Strings.concat("No selection is required, but selection value is, ",
                          selectedItem));
    }
  }

  @RunsInEDT
  void dropDownVisibleThroughComponent(AbstractComboBox comboBox, boolean visible) {
    if (visible) {
      if (!comboBox.isPopupVisible()) {
        clickPopupButton(comboBox);
      }
    } else {
      if (comboBox.isPopupVisible()) {
        comboBox.hidePopup();
      }
    }
    robot.waitForIdle();
  }

  int validateIndex(AbstractComboBox comboBox, int index) {
    int itemCount = size(comboBox);
    if (index >= 0 && index < itemCount) {
      return index;
    }
    throw new LocationUnavailableException(Strings.concat("Item index (",
                                                          java.lang.String.valueOf(index),
                                                          ") should be between [0] and [",
                                                          String.valueOf(itemCount - 1),
                                                          "] (inclusive)"));
  }

  /**
   * Converts an object that may be present in the ComboBox into a String representation.
   *
   * @param comboBox The comboBox to convert the object for.
   * @param o The object to convert into a String.
   * @return The object passed in converted in to a String so that it would appear as it
   *         would to a user selecting the value in the ComboBox.
   */
  protected String convertElementToString(AbstractComboBox comboBox, Object o) {
    if (o == null) {
      return null;
    }

    if (o instanceof String) {
      return (String) o;
    }

    ObjectConverter converter = comboBox.getConverter();
    if (converter != null) {
      return converter.toString(o, comboBox.getConverterContext());
    }
    return o.toString();
  }

  protected boolean isDropDownVisible(AbstractComboBox comboBox) {
    return comboBox.isPopupVisible();
  }

  protected int size(AbstractComboBox comboBox) {
    return comboBox.getModel().getSize();
  }

  protected Object itemAt(AbstractComboBox comboBox, int index) {
    return comboBox.getModel().getElementAt(index);
  }

  protected boolean areEqual(AbstractComboBox combo, Object o1, Object o2) {
    String s1 = getStringRepresentation(combo, o1);
    String s2 = getStringRepresentation(combo, o2);

    return s1.equals(s2);
  }

  protected String selectedIndexProperty(AbstractComboBox comboBox) {
    return propertyName(comboBox, SELECTED_INDEX_PROPERTY).toString();
  }

  /**
   * Updates the implementation of <code>{@link AbstractComboBoxCellReader}</code> to use
   * when comparing internal values of a <code>{@link com.jidesoft.combobox.ListComboBox}</code> and the values expected
   * in a test.
   *
   * @param newCellReader the new <code>ListComboBoxCellValueReader</code> to use.
   * @throws IllegalArgumentException if <code>newCellReader</code> is <code>null</code>.
   */
  public void cellReader(AbstractComboBoxCellReader newCellReader) {
    // TODO move this back to CommonConditions.validateCellReader()
    // validateCellReader(newCellReader);
    if (newCellReader == null) {
      throw new NullPointerException("Cell reader should not be null");
    }
    _cellReader = newCellReader;
  }

  @RunsInEDT
  public void clickPopupButton(final AbstractComboBox comboBox) {
    AbstractButton button = comboBox.getPopupButton();
    robot.moveMouse(button);
    robot.click(button);
    robot.waitForIdle();
  }

  /**
   * Obtains the component that the user will interact with, which could be the actual
   * combo box, or the editor component, depending upon the state of the combo box.
   *
   * @param c The Component to obtain the interactive component from.
   * @return The {@link java.awt.Component} that the user is to interact with.
   */
  @RunsInEDT
  protected Component getInteractionComponent(Component c) {
    if (c instanceof AbstractComboBox) {
      AbstractComboBox combo = (AbstractComboBox) c;
      if (combo.isEditable()) {
        ComboBoxEditor editor = combo.getEditor();
        return editor.getEditorComponent();
      }
    }
    return c;
  }

  @RunsInEDT
  public void requirePopupVisible(final AbstractComboBox target, boolean visible) {
    Boolean isVisible = execute(() -> target.isPopupVisible());
    assertThat(isVisible).as("Popup Visibility").isEqualTo(visible);
  }
}
