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
package org.assertj.swing.format;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.builder.JButtons.button;

import javax.swing.JButton;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link IntrospectionComponentFormatter#format(java.awt.Component)}.
 *
 * @author Alex Ruiz
 */
public class IntrospectionComponentFormatter_format_Test extends EDTSafeTestCase {
  private JButton button;

  @Before
  public void setUp() {
    button = button().withName("button").withText("Click Me").createNew();
  }

  @Test
  public void should_Format_Component() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "name", "text");
    assertThat(formatter.format(button)).isEqualTo("javax.swing.JButton[name='button', text='Click Me']");
  }

  @Test
  public void should_Format_Even_With_Invalid_Property_Names() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "lastName", "text");
    String formatted = formatter.format(button);
    assertThat(formatted).contains("lastName=<Unable to read property").contains("text='Click Me'");
  }

  @Test
  public void should_Format_Property_Showing() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "showing");
    assertThat(formatter.format(button)).isEqualTo("javax.swing.JButton[showing=false]");
  }

  @Test
  public void should_Format_One_Dimensional_Array_Properties() {
    MyButton myButton = MyButton.newButton(array("Luke", "Leia"));
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(MyButton.class, "names", "text");
    String formatted = formatter.format(myButton);
    assertThat(formatted).contains("names=[\"Luke\", \"Leia\"]");
  }

  @Test
  public void should_Additionally_Show_Name_Of_Superclass_When_Having_Anynomous_Class_Inside() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "text");
    button = execute(() -> new JButton() {
      /** Generated serial version UID. */
      private static final long serialVersionUID = -6097882709760432679L;
    });
    assertThat(formatter.format(button)).startsWith(getClass().getName()).contains("javax.swing.JButton");
  }

  @Test
  public void should_Additionally_Show_Name_Of_Superclass_When_Having_Anynomous_Class_Inside_Anonymous_Class() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "text");
    button = execute(() -> new JButton() {
      /** Generated serial version UID. */
      private static final long serialVersionUID = -6097882709760432679L;
      private JButton b = new JButton() {
        /** Generated serial version UID. */
        private static final long serialVersionUID = -8731420542549513675L;
      };
    }.b);
    assertThat(formatter.format(button)).startsWith(getClass().getName()).contains("javax.swing.JButton");
  }

  static class MyButton extends JButton {
    /** Generated serial version UID. */
    private static final long serialVersionUID = -2996550506597947057L;

    static MyButton newButton(final String[] names) {
      return execute(() -> new MyButton(names));
    }

    final String[] names;

    MyButton(String[] names) {
      this.names = names;
    }

    // to be called by introspector - do not remove
    public String[] getNames() {
      return names;
    }
  };
}
