/*
 * Created on Dec 20, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.timing.Timeout.timeout;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JProgressBar;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JProgressBarDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link JProgressBarFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JProgressBarFixture_withMocks_TestCase {
  private JProgressBarFixture fixture;

  @Before
  public void setUp() {
    fixture = new JProgressBarFixture(mock(Robot.class), mock(JProgressBar.class));
    fixture.replaceDriverWith(mock(JProgressBarDriver.class));
  }

  @Test
  public void should_call_requireValue_in_driver_and_return_self() {
    assertThat(fixture.requireValue(6)).isSameAs(fixture);
    verify(fixture.driver()).requireValue(fixture.target(), 6);
  }

  @Test
  public void should_call_requireDeterminate_in_driver_and_return_self() {
    assertThat(fixture.requireDeterminate()).isSameAs(fixture);
    verify(fixture.driver()).requireDeterminate(fixture.target());
  }

  @Test
  public void should_call_requireIndeterminate_in_driver_and_return_self() {
    assertThat(fixture.requireIndeterminate()).isSameAs(fixture);
    verify(fixture.driver()).requireIndeterminate(fixture.target());
  }

  @Test
  public void should_return_text_using_driver() {
    JProgressBarDriver driver = fixture.driver();
    JProgressBar target = fixture.target();
    when(driver.textOf(target)).thenReturn("Hello");
    assertThat(fixture.text()).isEqualTo("Hello");
    verify(driver).textOf(target);
  }

  @Test
  public void should_call_requireText_in_driver_and_return_self() {
    assertThat(fixture.requireText("Hello")).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), "Hello");
  }

  @Test
  public void should_call_requireText_with_Pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireText(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), pattern);
  }

  @Test
  public void should_call_waitUntilValueIs_in_driver_and_return_self() {
    assertThat(fixture.waitUntilValueIs(6)).isSameAs(fixture);
    verify(fixture.driver()).waitUntilValueIs(fixture.target(), 6);
  }

  @Test
  public void should_call_waitUntilValueIs_with_timeout_in_driver_and_return_self() {
    assertThat(fixture.waitUntilValueIs(6, timeout(8))).isSameAs(fixture);
    verify(fixture.driver()).waitUntilValueIs(fixture.target(), 6, timeout(8));
  }

  @Test
  public void should_call_waitUntilIsDeterminate_in_driver_and_return_self() {
    assertThat(fixture.waitUntilIsDeterminate()).isSameAs(fixture);
    verify(fixture.driver()).waitUntilIsDeterminate(fixture.target());
  }

  @Test
  public void should_call_waitUntilIsDeterminate_with_timeout_in_driver_and_return_self() {
    assertThat(fixture.waitUntilIsDeterminate(timeout(8))).isSameAs(fixture);
    verify(fixture.driver()).waitUntilIsDeterminate(fixture.target(), timeout(8));
  }
}
