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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentDriver#selectText(javax.swing.text.JTextComponent, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentDriver_selectText_Test extends JTextComponentDriver_TestCase {
  private JTextField scrollToViewTextField;

  @RunsInEDT
  @Override
  void extraSetUp() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        scrollToViewTextField = new JTextField(10);
        JScrollPane scrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        JViewport viewport = scrollPane.getViewport();
        viewport.add(new JLabel("A Label"));
        viewport.add(scrollToViewTextField);
        scrollPane.setPreferredSize(new Dimension(50, 50));
        window.add(scrollPane);
      }
    });
  }

  @Test
  public void should_Select_Given_Text() {
    showWindow();
    setTextFieldText("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isEqualTo("llo W");
  }

  @Test
  public void should_Scroll_To_Text_To_Select() {
    showWindow();
    updateTextTo(scrollToViewTextField, "one two three four five six seven eight nine ten");
    driver.selectText(scrollToViewTextField, "ten");
    assertThat(selectedTextOf(scrollToViewTextField)).isEqualTo("ten");
  }

  @RunsInEDT
  final void updateTextTo(JTextField f, String text) {
    setText(f, text);
    robot.waitForIdle();
  }

  @Test
  public void should_Throw_Error_If_JTextComponent_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectText(textField, "llo W");
  }

  @Test
  public void should_Throw_Error_If_JTextComponent_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectText(textField, "llo W");
  }
}
