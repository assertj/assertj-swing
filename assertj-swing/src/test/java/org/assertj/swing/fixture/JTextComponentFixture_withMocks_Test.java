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

import javax.swing.text.JTextComponent;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JTextComponentDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentFixture}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentFixture_withMocks_Test {
  private JTextComponentFixture fixture;

  @Before
  public void setUp() {
    fixture = new JTextComponentFixture(mock(Robot.class), mock(JTextComponent.class));
    fixture.replaceDriverWith(mock(JTextComponentDriver.class));
  }

  @Test
  public void should_Return_Text_Using_Driver() {
    JTextComponentDriver driver = fixture.driver();
    JTextComponent target = fixture.target();
    when(driver.textOf(target)).thenReturn("Hello");
    assertThat(fixture.text()).isEqualTo("Hello");
    verify(driver).textOf(target);
  }

  @Test
  public void should_Call_SelectText_In_Driver_And_Return_Self() {
    assertThat(fixture.select("Hello")).isSameAs(fixture);
    verify(fixture.driver()).selectText(fixture.target(), "Hello");
  }

  @Test
  public void should_Call_SelectText_With_Range_In_Driver_And_Return_Self() {
    assertThat(fixture.selectText(6, 8)).isSameAs(fixture);
    verify(fixture.driver()).selectText(fixture.target(), 6, 8);
  }

  @Test
  public void should_Call_SelectAll_In_Driver_And_Return_Self() {
    assertThat(fixture.selectAll()).isSameAs(fixture);
    verify(fixture.driver()).selectAll(fixture.target());
  }

  @Test
  public void should_Call_DeleteText_In_Driver_And_Return_Self() {
    assertThat(fixture.deleteText()).isSameAs(fixture);
    verify(fixture.driver()).deleteText(fixture.target());
  }

  @Test
  public void should_Call_EnterText_In_Driver_And_Return_Self() {
    assertThat(fixture.enterText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).enterText(fixture.target(), "Hello");
  }

  @Test
  public void should_Call_SetText_In_Driver_And_Return_Self() {
    assertThat(fixture.setText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).setText(fixture.target(), "Hello");
  }

  @Test
  public void should_Call_RequireText_In_Driver_And_Return_Self() {
    assertThat(fixture.requireText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), "Hello");
  }

  @Test
  public void should_Call_RequireText_With_Driver_In_Driver_And_Return_Self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireText(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), pattern);
  }

  @Test
  public void should_Call_RequireEditable_In_Driver_And_Return_Self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireEditable(fixture.target());
  }

  @Test
  public void should_Call_RequireNotEditable_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireNotEditable(fixture.target());
  }

  @Test
  public void should_Call_RequireEmpty_In_Driver_And_Return_Self() {
    assertThat(fixture.requireEmpty()).isSameAs(fixture);
    verify(fixture.driver()).requireEmpty(fixture.target());
  }
}
