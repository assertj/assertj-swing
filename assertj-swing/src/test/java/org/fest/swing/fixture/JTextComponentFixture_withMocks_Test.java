/*
 * Created on Feb 8, 2007
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

import javax.swing.text.JTextComponent;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTextComponentDriver;
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
  public void should_return_text_using_driver() {
    JTextComponentDriver driver = fixture.driver();
    JTextComponent target = fixture.target();
    when(driver.textOf(target)).thenReturn("Hello");
    assertThat(fixture.text()).isEqualTo("Hello");
    verify(driver).textOf(target);
  }

  @Test
  public void should_call_selectText_in_driver_and_return_self() {
    assertThat(fixture.select("Hello")).isSameAs(fixture);
    verify(fixture.driver()).selectText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_selectText_with_range_in_driver_and_return_self() {
    assertThat(fixture.selectText(6, 8)).isSameAs(fixture);
    verify(fixture.driver()).selectText(fixture.target(), 6, 8);
  }

  @Test
  public void should_call_selectAll_in_driver_and_return_self() {
    assertThat(fixture.selectAll()).isSameAs(fixture);
    verify(fixture.driver()).selectAll(fixture.target());
  }

  @Test
  public void should_call_deleteText_in_driver_and_return_self() {
    assertThat(fixture.deleteText()).isSameAs(fixture);
    verify(fixture.driver()).deleteText(fixture.target());
  }

  @Test
  public void should_call_enterText_in_driver_and_return_self() {
    assertThat(fixture.enterText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).enterText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_setText_in_driver_and_return_self() {
    assertThat(fixture.setText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).setText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_requireText_in_driver_and_return_self() {
    assertThat(fixture.requireText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_requireText_with_driver_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireText(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), pattern);
  }

  @Test
  public void should_call_requireEditable_in_driver_and_return_self() {
    assertThat(fixture.requireEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireEditable(fixture.target());
  }

  @Test
  public void should_call_requireNotEditable_in_driver_and_return_self() {
    assertThat(fixture.requireNotEditable()).isSameAs(fixture);
    verify(fixture.driver()).requireNotEditable(fixture.target());
  }

  @Test
  public void should_call_requireEmpty_in_driver_and_return_self() {
    assertThat(fixture.requireEmpty()).isSameAs(fixture);
    verify(fixture.driver()).requireEmpty(fixture.target());
  }
}
