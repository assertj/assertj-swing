/*
 * Created on Dec 22, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.format;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.util.Arrays.array;

import javax.swing.JButton;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.EDTSafeTestCase;
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
  public void should_format_Component() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "name", "text");
    assertThat(formatter.format(button)).isEqualTo("javax.swing.JButton[name='button', text='Click Me']");
  }

  @Test
  public void should_format_even_with_invalid_property_names() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "lastName", "text");
    String formatted = formatter.format(button);
    assertThat(formatted).contains("lastName=<Unable to read property").contains("text='Click Me'");
  }

  @Test
  public void should_format_property_showing() {
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(JButton.class, "showing");
    assertThat(formatter.format(button)).isEqualTo("javax.swing.JButton[showing=false]");
  }

  @Test
  public void should_format_one_dimensional_array_properties() {
    MyButton myButton = MyButton.newButton(array("Luke", "Leia"));
    IntrospectionComponentFormatter formatter = new IntrospectionComponentFormatter(MyButton.class, "names", "text");
    String formatted = formatter.format(myButton);
    assertThat(formatted).contains("names=['Luke', 'Leia']");
  }

  static class MyButton extends JButton {
    static MyButton newButton(final String[] names) {
      return GuiActionRunner.execute(new GuiQuery<MyButton>() {
        @Override
        protected MyButton executeInEDT() {
          return new MyButton(names);
        }
      });
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
