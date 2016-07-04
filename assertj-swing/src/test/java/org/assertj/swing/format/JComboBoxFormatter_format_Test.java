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
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.builder.JComboBoxes.comboBox;

import javax.swing.JComboBox;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxFormatter#format(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JComboBoxFormatter_format_Test extends EDTSafeTestCase {
  private JComboBox<?> comboBox;
  private JComboBoxFormatter formatter;

  @Before
  public void setUp() {
    comboBox = comboBox().editable(true).withItems("One", 2, "Three", 4).withName("comboBox").withSelectedIndex(1)
                         .createNew();
    formatter = new JComboBoxFormatter();
  }

  @Test
  public void should_Format_JComboBox() {
    String formatted = formatter.format(comboBox);
    assertThat(formatted).contains("javax.swing.JComboBox").contains("name='comboBox'").contains("selectedItem=2")
                         .contains("contents=[\"One\", 2, \"Three\", 4]").contains("editable=true")
                         .contains("enabled=true")
                         .contains("visible=true").contains("showing=false");
  }

  @Test
  public void should_Additionally_Show_Name_Of_Superclass_When_Having_Anynomous_Class_Inside() {
    comboBox = execute(() -> new JComboBox() {
      /** Generated serial version UID. */
      private static final long serialVersionUID = -6097882709760432679L;
    });
    assertThat(formatter.format(comboBox)).startsWith(getClass().getName()).contains("javax.swing.JComboBox");
  }

  @Test
  public void should_Additionally_Show_Name_Of_Superclass_When_Having_Anynomous_Class_Inside_Anonymous_Class() {
    comboBox = execute(() -> new JComboBox() {
      /** Generated serial version UID. */
      private static final long serialVersionUID = -6097882709760432679L;
      private JComboBox<?> b = new JComboBox() {
        /** Generated serial version UID. */
        private static final long serialVersionUID = -8731420542549513675L;
      };
    }.b);
    assertThat(formatter.format(comboBox)).startsWith(getClass().getName()).contains("javax.swing.JComboBox");
  }
}
