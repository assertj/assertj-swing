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
package org.assertj.swing.driver;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Objects.HASH_CODE_PRIME;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Objects.hashCodeFor;
import static org.assertj.swing.driver.AbstractButtonTextQuery.textOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Rule;

/**
 * Base test case for {@link JOptionPaneDriver}.
 * 
 * @author Alex Ruiz
 */
public abstract class JOptionPaneDriver_TestCase extends RobotBasedTestCase {
  private static final Icon ICON = null;
  private static final String MESSAGE = "Message";

  @Rule
  public ExpectedException thrown = none();

  JOptionPaneDriver driver;

  @Override
  protected final void onSetUp() {
    driver = new JOptionPaneDriver(robot);
  }

  final String title() {
    return getClass().getSimpleName();
  }

  @RunsInEDT
  static JOptionPane confirmMessage() {
    return execute(new GuiQuery<JOptionPane>() {
      @Override
      protected JOptionPane executeInEDT() {
        return new JOptionPane(MESSAGE, QUESTION_MESSAGE, YES_NO_CANCEL_OPTION);
      }
    });
  }

  @RunsInEDT
  final void assertThatButtonHasText(JButton button, String textKey) {
    String expected = UIManager.getString(textKey);
    assertThat(textOf(button)).isEqualTo(expected);
  }

  @RunsInEDT
  static JOptionPane inputMessage() {
    return execute(new GuiQuery<JOptionPane>() {
      @Override
      protected JOptionPane executeInEDT() {
        JOptionPane optionPane = new JOptionPane(MESSAGE, QUESTION_MESSAGE, OK_CANCEL_OPTION);
        optionPane.setWantsInput(true);
        return optionPane;
      }
    });
  }

  @RunsInEDT
  static JOptionPane messageWithOptions(final Object... options) {
    return execute(new GuiQuery<JOptionPane>() {
      @Override
      protected JOptionPane executeInEDT() {
        Object initialValue = options[0];
        JOptionPane optionPane = new JOptionPane(MESSAGE, QUESTION_MESSAGE, YES_NO_OPTION, ICON, options, initialValue);
        optionPane.setInitialValue(initialValue);
        return optionPane;
      }
    });
  }

  static class Person {
    private final String name;

    Person(String name) {
      this.name = name;
    }

    @Override
    public int hashCode() {
      final int prime = HASH_CODE_PRIME;
      int result = 1;
      result = prime * result + hashCodeFor(name);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Person other = (Person) obj;
      return areEqual(name, other.name);
    }

    @Override
    public String toString() {
      return name;
    }
  }

  @RunsInEDT
  static JOptionPane messageWithValue(final Object message) {
    return execute(new GuiQuery<JOptionPane>() {
      @Override
      protected JOptionPane executeInEDT() {
        return new JOptionPane(message);
      }
    });
  }

  @RunsInEDT
  static JOptionPane informationMessage() {
    return messageOfType(INFORMATION_MESSAGE);
  }

  @RunsInEDT
  static JOptionPane errorMessage() {
    return messageOfType(ERROR_MESSAGE);
  }

  @RunsInEDT
  static JOptionPane messageOfType(final int type) {
    return execute(new GuiQuery<JOptionPane>() {
      @Override
      protected JOptionPane executeInEDT() {
        return new JOptionPane(MESSAGE, type);
      }
    });
  }
}
