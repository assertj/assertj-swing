/*
 * Created on Jul 17, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTextComponentSelectedTextQuery.selectedTextOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
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
  public void should_select_given_text() {
    showWindow();
    setTextFieldText("Hello World");
    driver.selectText(textField, "llo W");
    assertThat(selectedTextOf(textField)).isEqualTo("llo W");
  }

  @Test
  public void should_scroll_to_text_to_select() {
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
  public void should_throw_error_if_JTextComponent_is_disabled() {
    disableTextField();
    try {
      driver.selectText(textField, "llo W");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTextComponent_is_not_showing_on_the_screen() {
    try {
      driver.selectText(textField, "llo W");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
