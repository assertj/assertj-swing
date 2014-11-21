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
import com.jidesoft.combobox.ListComboBox;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.driver.ComponentStateValidator;
import org.fest.swing.format.Formatting;
import org.fest.util.Strings;

/**
 * Understands an action that validates that the editor of a <code>{@link
 * ListComboBox}</code> is accessible or not. To be accessible, a
 * <code>ListComboBox</code> needs to be enabled and editable. <p> <b>Note:</b> Methods in
 * this class are <b>not</b> executed in the event dispatch thread (EDT.) Clients are
 * responsible for invoking them in the EDT. </p>
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @see ListComboBox#isEditable()
 * @see java.awt.Component#isEnabled()
 */
public class AbstractComboBoxAccessibleEditorValidator {

  @RunsInCurrentThread
  public static void validateEditorIsAccessible(AbstractComboBox comboBox) {
    ComponentStateValidator.validateIsEnabledAndShowing(comboBox);
    if (!comboBox.isEditable()) {
      throw new IllegalStateException(Strings.concat("Expecting component ",
                                                     Formatting.format(comboBox),
                                                     " to be editable"));
    }
  }

  private AbstractComboBoxAccessibleEditorValidator() {
  }
}

