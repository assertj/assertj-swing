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

import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.builder.JOptionPanes.optionPane;

import javax.swing.JOptionPane;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFormatter#format(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JOptionPaneFormatter_format_Test extends EDTSafeTestCase {
  private JOptionPane optionPane;
  private JOptionPaneFormatter formatter;

  @Before
  public void setUp() {
    optionPane = optionPane().withMessage("A message").withMessageType(ERROR_MESSAGE).withOptionType(DEFAULT_OPTION)
                             .createNew();
    formatter = new JOptionPaneFormatter();
  }

  @Test
  public void should_Format_JOptionPane() {
    String formatted = formatter.format(optionPane);
    assertThat(formatted).contains("javax.swing.JOptionPane").contains("message='A message'")
                         .contains("messageType=ERROR_MESSAGE").contains("optionType=DEFAULT_OPTION")
                         .contains("enabled=true")
                         .contains("visible=true").contains("showing=false");
  }

  @Test
  public void should_Additionally_Show_Name_Of_Superclass_When_Having_Anynomous_Class_Inside() {
    optionPane = execute(() -> new JOptionPane() {
      /** Generated serial version UID. */
      private static final long serialVersionUID = -6097882709760432679L;
    });
    assertThat(formatter.format(optionPane)).startsWith(getClass().getName()).contains("javax.swing.JOptionPane");
  }

  @Test
  public void should_Additionally_Show_Name_Of_Superclass_When_Having_Anynomous_Class_Inside_Anonymous_Class() {
    optionPane = execute(() -> new JOptionPane() {
      /** Generated serial version UID. */
      private static final long serialVersionUID = -6097882709760432679L;
      private JOptionPane b = new JOptionPane() {
        /** Generated serial version UID. */
        private static final long serialVersionUID = -8731420542549513675L;
      };
    }.b);
    assertThat(formatter.format(optionPane)).startsWith(getClass().getName()).contains("javax.swing.JOptionPane");
  }
}
