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

import com.jidesoft.combobox.AbstractComboBox;
import static org.fest.swing.edt.GuiActionRunner.execute;
import org.fest.swing.edt.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether a
 * <code>{@link javax.swing.JComboBox}</code> is editable or not.
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @see javax.swing.JComboBox#isEditable()
 */
public class AbstractComboBoxEditableQuery {

  static boolean isEditable(final AbstractComboBox comboBox) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return comboBox.isEditable();
      }
    });
  }

  private AbstractComboBoxEditableQuery() {
  }
}

