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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JOptionPaneDriver;
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
  public void should_Return_Title_Using_Driver() {
    when(driver.title(target)).thenReturn("Testing");
    assertThat(fixture.title()).isEqualTo("Testing");
    verify(driver).title(target);
  }

  @Test
  public void should_Return_Ok_Button_Using_Driver() {
    JButton button = mock(JButton.class);
    when(driver.okButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.okButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).okButton(target);
  }

  @Test
  public void should_Return_Cancel_Button_Using_Driver() {
    JButton button = mock(JButton.class);
    when(driver.cancelButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.cancelButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).cancelButton(target);
  }

  @Test
  public void should_Return_Yes_Button_Using_Driver() {
    JButton button = mock(JButton.class);
    when(driver.yesButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.yesButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).yesButton(target);
  }

  @Test
  public void should_Return_No_Button_Using_Driver() {
    JButton button = mock(JButton.class);
    when(driver.noButton(target)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.noButton();
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).noButton(target);
  }

  @Test
  public void should_Return_Button_With_Text_Using_Driver() {
    JButton button = mock(JButton.class);
    when(driver.buttonWithText(target, "Maybe")).thenReturn(button);
    JButtonFixture buttonFixture = fixture.buttonWithText("Maybe");
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).buttonWithText(target, "Maybe");
  }

  @Test
  public void should_Return_Button_Matching_Pattern_Using_Driver() {
    Pattern pattern = Pattern.compile("Maybe");
    JButton button = mock(JButton.class);
    when(driver.buttonWithText(target, pattern)).thenReturn(button);
    JButtonFixture buttonFixture = fixture.buttonWithText(pattern);
    assertThat(buttonFixture.target()).isSameAs(button);
    verify(driver).buttonWithText(target, pattern);
  }

  @Test
  public void should_Call_RequireErrorMessage_In_Driver_And_Return_Self() {
    assertThat(fixture.requireErrorMessage()).isSameAs(fixture);
    verify(driver).requireErrorMessage(target);
  }

  @Test
  public void should_Call_RequireInformationMessage_In_Driver_And_Return_Self() {
    assertThat(fixture.requireInformationMessage()).isSameAs(fixture);
    verify(driver).requireInformationMessage(target);
  }

  @Test
  public void should_Call_RequireWarningMessage_In_Driver_And_Return_Self() {
    assertThat(fixture.requireWarningMessage()).isSameAs(fixture);
    verify(driver).requireWarningMessage(target);
  }

  @Test
  public void should_Call_RequireQuestionMessage_In_Driver_And_Return_Self() {
    assertThat(fixture.requireQuestionMessage()).isSameAs(fixture);
    verify(driver).requireQuestionMessage(target);
  }

  @Test
  public void should_Call_RequirePlainMessage_In_Driver_And_Return_Self() {
    assertThat(fixture.requirePlainMessage()).isSameAs(fixture);
    verify(driver).requirePlainMessage(target);
  }

  @Test
  public void should_Call_RequireTitle_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.requireTitle("Hello")).isSameAs(fixture);
    verify(driver).requireTitle(target, "Hello");
  }

  @Test
  public void should_Call_RequireTitle_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireTitle(pattern)).isSameAs(fixture);
    verify(driver).requireTitle(target, pattern);
  }

  @Test
  public void should_Call_RequireMessage_With_Text_In_Driver_And_Return_Self() {
    assertThat(fixture.requireMessage("Hello")).isSameAs(fixture);
    verify(driver).requireMessage(target, "Hello");
  }

  @Test
  public void should_Call_RequireMessage_With_Pattern_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireMessage(pattern)).isSameAs(fixture);
    verify(driver).requireMessage(target, pattern);
  }

  @Test
  public void should_Call_RequireOptions_In_Driver_And_Return_Self() {
    Object[] options = { mock(JButton.class) };
    assertThat(fixture.requireOptions(options)).isSameAs(fixture);
    verify(driver).requireOptions(target, options);
  }
}
