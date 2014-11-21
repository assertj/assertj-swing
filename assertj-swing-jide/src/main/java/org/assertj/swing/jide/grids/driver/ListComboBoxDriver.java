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

package org.fest.swing.jide.grids.driver;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import com.jidesoft.combobox.*;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverter;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentStateValidator;
import org.fest.swing.driver.JListDriver;
import org.fest.swing.edt.*;
import org.fest.swing.exception.LocationUnavailableException;

import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

/**
 * A driver to allow us to interact with a {@link com.jidesoft.combobox.ListComboBox}.
 * @author Peter Murray
 */
public class ListComboBoxDriver extends AbstractComboBoxDriver {

  private final JListDriver _listDriver;

  private AbstractComboBoxDropDownListFinder _listFinder;

  public ListComboBoxDriver(Robot robot) {
    super(robot);
    _listDriver = new JListDriver(robot);
    _listFinder = new AbstractComboBoxDropDownListFinder(robot);
  }

  public JList getList(ListComboBox comboBox) {
    PopupPanel panel = showPopup(comboBox);
    return _listFinder.findDropDownList(panel);
  }

  public void requireItemCount(final ListComboBox comboBox, int expected) {
    GuiQuery<Integer> query = new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() throws Throwable {
        return comboBox.getModel().getSize();
      }
    };
    Integer count = GuiActionRunner.execute(query);
    assertThat(count).as("ItemCount").isEqualTo(expected);
  }

  /**
   * Selects the item under the given index in the <code>{@link ListComboBox}</code>.
   * @param comboBox the target <code>ListComboBox</code>.
   * @param index the given index.
   * @throws IllegalStateException if the <code>JComboBox</code> is disabled.
   * @throws IllegalStateException if the <code>JComboBox</code> is not showing on the
   * screen.
   * @throws IndexOutOfBoundsException if the given index is negative or greater than the
   * index of the last item in the <code>JComboBox</code>.
   */
  @RunsInEDT
  public void selectItem(final ListComboBox comboBox, int index) {
    validateCanSelectItem(comboBox, index);
    selectItemAtIndex(comboBox, index);
    hidePopupIfVisible(comboBox);
  }

  public void requireText(final ListComboBox comboBox, String value) {
    GuiQuery<String> query = new GuiQuery<String>() {
      @Override
      protected String executeInEDT() throws Throwable {
        Component editorComp = comboBox.getEditor().getEditorComponent();
        if (editorComp instanceof JTextComponent) {
          return ((JTextComponent)editorComp).getText();
        }
        return null;
      }
    };
    String actualValue = GuiActionRunner.execute(query);
    assertThat(actualValue).isEqualTo(value);
  }

  /**
   * Selects the first item matching the given text in the <code>{@link
   * ListComboBox}</code>.
   * @param comboBox the target <code>ListComboBox</code>.
   * @param toSelect the text to match
   * @throws LocationUnavailableException if an element matching the given text cannot be
   * found.
   */
  public void selectItem(ListComboBox comboBox, String toSelect) {
    if (areEqual(comboBox,
                 convertElementToString(comboBox, comboBox.getSelectedItem()),
                 toSelect)) {
      return;
    }

    int itemCount = size(comboBox);
    for (int i = 0; i < itemCount; i++) {
      if (areEqual(comboBox,
                   convertElementToString(comboBox, itemAt(comboBox, i)),
                   toSelect)) {
        selectItem(comboBox, i);
        return;
      }
    }
    // While actions are supposed to represent real user actions, it's possible that the current environment does not
    // match sufficiently, so we need to throw an appropriate exception that can be used to diagnose the problem.
    throw new LocationUnavailableException(concat("Unable to find item ",
                                                  quote(toSelect),
                                                  " among the ListComboBox contents (",
                                                  format(contentsOf(comboBox)), ")"));
  }

  /**
   * Returns the <code>String</code> representation of the element under the given index,
   * using this driver's <code>{@link AbstractComboBoxCellReader}</code>.
   * @param comboBox the target <code>AbstractComboBox</code>.
   * @param index the given index.
   * @return the value of the element under the given index.
   * @throws org.fest.swing.exception.LocationUnavailableException if the given index is
   * negative or greater than the index of the last item in the
   * <code>AbstractComboBox</code>.
   * @see #cellReader(AbstractComboBoxCellReader)
   */
  public String value(AbstractComboBox comboBox, int index) {
    validateIndex(comboBox, index);
    return _cellReader.valueAt(comboBox, index);
  }

  /**
   * Returns an array of <code>String</code>s that represent the <code>{@link
   * ListComboBox}</code> list. Note that the current selection might not be included,
   * since it's possible to have a custom (edited) entry there that is not included in the
   * default contents.
   * @param comboBox the target <code>ListComboBox</code>.
   * @return an array of <code>String</code>s that represent the <code>ListComboBox</code>
   *         list.
   */
  public String[] contentsOf(ListComboBox comboBox) {
    int itemCount = size(comboBox);
    String[] items = new String[itemCount];
    for (int i = 0; i < itemCount; i++) {
      items[i] = itemAt(comboBox, i).toString();
    }
    return items;
  }

  public String[] getContents(ListComboBox target) {
    ComboBoxModel model = target.getModel();
    ConverterContext context = target.getConverterContext();
    ObjectConverter converter = target.getConverter();

    java.util.List<String> contents = new ArrayList<String>(model.getSize());
    for (int i = 0; i < model.getSize(); i++) {
      if (converter == null) {
        contents.add(String.valueOf(model.getElementAt(i)));
      } else {
        contents.add(converter.toString(model.getElementAt(i), context));
      }
    }
    return contents.toArray(new String[contents.size()]);
  }

  public String getValueAsString(ListComboBox target, int index) {
    Object o = target.getModel().getElementAt(index);
    ObjectConverter converter = target.getConverter();
    if (converter == null) {
      return String.valueOf(o);
    } else {
      return converter.toString(o, target.getConverterContext());
    }
  }

  @RunsInEDT
  private void selectItemAtIndex(final ListComboBox comboBox, final int index) {
    if (!comboBox.isPopupVisible()) {
      clickPopupButton(comboBox);
    }
    PopupPanel panel = showPopup(comboBox);
    JList dropDownList = _listFinder.findDropDownList(panel);
    if (dropDownList != null) {
      int size = dropDownList.getModel().getSize();
      _listDriver.selectItem(dropDownList, index);
      return;
    }
    ListComboBoxSetSelectedIndexTask.setSelectedIndex(comboBox, index);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void validateCanSelectItem(final AbstractComboBox comboBox,
                                            final int index) {
    GuiActionRunner.execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        ComponentStateValidator.validateIsEnabledAndShowing(comboBox);
        AbstractComboBoxItemIndexValidator.validateIndex(comboBox, index);
      }
    });
  }

  @RunsInEDT
  private void hidePopupIfVisible(AbstractComboBox comboBox) {
    dropDownVisibleThroughComponent(comboBox, false);
  }
}
