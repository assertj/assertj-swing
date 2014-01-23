/*
 * Created on Feb 13, 2007
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JOptionPaneDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFixture}.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneFixture_withMocks_Test {
  private JOptionPaneDriver driver;
  private JOptionPane target;

  private JOptionPaneFixture fixture;

  @Before
  public void setUp() {
    fixture = new JOptionPaneFixture(mock(Robot.class), mock(JOptionPane.class));
    fixture.replaceDriverWith(mock(JOptionPaneDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_return_title_using_driver() {
    when(driver.title(target)).thenReturn("Testing");
    assertThat(fixture.title()).isEqualTo("Testing");
    verify(driver).title(target);
  }

  @Test
  public void should_return_ok_button_using_driver() {
    JButton button = mock(JButton.class);
    when(driver.okButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.okButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).okButton(target);
  }

  @Test
  public void should_return_cancel_button_using_driver() {
    JButton button = mock(JButton.class);
    when(driver.cancelButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.cancelButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).cancelButton(target);
  }

  @Test
  public void should_return_yes_button_using_driver() {
    JButton button = mock(JButton.class);
    when(driver.yesButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.yesButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).yesButton(target);
  }

  @Test
  public void should_return_no_button_using_driver() {
    JButton button = mock(JButton.class);
    when(driver.noButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.noButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).noButton(target);
  }

  @Test
  public void should_return_button_with_text_using_driver() {
    JButton button = mock(JButton.class);
    when(driver.buttonWithText(target, "Maybe")).thenReturn(button);
    JButtonFixture buttonFixture = fixture.buttonWithText("Maybe");
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).buttonWithText(target, "Maybe");
  }

  @Test
  public void should_return_button_matching_pattern_using_driver() {
    Pattern pattern = Pattern.compile("Maybe");
    JButton button = mock(JButton.class);
    when(driver.buttonWithText(target, pattern)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.buttonWithText(pattern);
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).buttonWithText(target, pattern);
  }

  @Test
  public void should_call_requireErrorMessage_in_driver_and_return_self() {
    assertThat(fixture.requireErrorMessage()).isSameAs(fixture);
    verify(driver).requireErrorMessage(target);
  }

  @Test
  public void should_call_requireInformationMessage_in_driver_and_return_self() {
    assertThat(fixture.requireInformationMessage()).isSameAs(fixture);
    verify(driver).requireInformationMessage(target);
  }

  @Test
  public void should_call_requireWarningMessage_in_driver_and_return_self() {
    assertThat(fixture.requireWarningMessage()).isSameAs(fixture);
    verify(driver).requireWarningMessage(target);
  }

  @Test
  public void should_call_requireQuestionMessage_in_driver_and_return_self() {
    assertThat(fixture.requireQuestionMessage()).isSameAs(fixture);
    verify(driver).requireQuestionMessage(target);
  }

  @Test
  public void should_call_requirePlainMessage_in_driver_and_return_self() {
    assertThat(fixture.requirePlainMessage()).isSameAs(fixture);
    verify(driver).requirePlainMessage(target);
  }

  @Test
  public void should_call_requireTitle_with_text_in_driver_and_return_self() {
    assertThat(fixture.requireTitle("Hello")).isSameAs(fixture);
    verify(driver).requireTitle(target, "Hello");
  }

  @Test
  public void should_call_requireTitle_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireTitle(pattern)).isSameAs(fixture);
    verify(driver).requireTitle(target, pattern);
  }

  @Test
  public void should_call_requireMessage_with_text_in_driver_and_return_self() {
    assertThat(fixture.requireMessage("Hello")).isSameAs(fixture);
    verify(driver).requireMessage(target, "Hello");
  }

  @Test
  public void should_call_requireMessage_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireMessage(pattern)).isSameAs(fixture);
    verify(driver).requireMessage(target, pattern);
  }

  @Test
  public void should_call_requireOptions_in_driver_and_return_self() {
    Object[] options = { mock(JButton.class) };
    assertThat(fixture.requireOptions(options)).isSameAs(fixture);
    verify(driver).requireOptions(target, options);
  }
}
