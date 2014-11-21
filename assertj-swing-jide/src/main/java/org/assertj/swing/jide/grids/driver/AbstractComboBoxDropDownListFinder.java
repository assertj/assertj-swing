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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.*;
import org.fest.swing.core.Robot;

/**
 * Understands look up of the <code>{@link javax.swing.JList}</code> in the pop-up raised
 * by a <code>{@link com.jidesoft.combobox.AbstractComboBox}</code>, if the implementation
 * actually uses one.
 */
class AbstractComboBoxDropDownListFinder {

  private static final ComponentMatcher LIST_MATCHER = new TypeMatcher(JList.class);

  private final Robot _robot;

  AbstractComboBoxDropDownListFinder(Robot robot) {
    _robot = robot;
  }

  /**
   * Finds the <code>{@link javax.swing.JList}</code> in the pop-up raised by a
   * <code>{@link com.jidesoft.combobox.AbstractComboBox}</code>, if the implementation
   * actually uses one.
   * <p>
   * <b>Note:</b> this method is <b>not</b> executed in the event dispatch thread. Callers
   * are responsible for calling this method in the event dispatch thread.
   * @return the found <code>JList</code>, or <code>null</code> if a drop-down list
   * cannot be found.
   */
  @RunsInEDT
  JList findDropDownList(Container startFrom) {
    return findListIn(startFrom);
  }

  private JList findListIn(Container parent) {
    List<Component> found = new ArrayList<Component>(_robot.finder().findAll(parent, LIST_MATCHER));
    if (found.size() != 1) {
      return null;
    }
    final Component c = found.get(0);
    if (c instanceof JList) {
      return (JList)c;
    }
    return null;
  }
}