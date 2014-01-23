/*
 * Created on Nov 18, 2009
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JLabel;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JLabelDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JLabelFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JLabelFixture_withMocks_Test {
  private JLabelFixture fixture;

  @Before
  public void setUp() {
    fixture = new JLabelFixture(mock(Robot.class), mock(JLabel.class));
    fixture.replaceDriverWith(mock(JLabelDriver.class));
  }

  @Test
  public void should_return_text_using_driver() {
    JLabelDriver driver = fixture.driver();
    JLabel target = fixture.target();
    when(driver.textOf(target)).thenReturn("Six");
    assertThat(fixture.text()).isEqualTo("Six");
    verify(driver).textOf(target);
  }

  @Test
  public void should_call_requireText_with_text_and_return_self() {
    assertThat(fixture.requireText("Six")).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), "Six");
  }

  @Test
  public void should_call_requireText_with_pattern_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.requireText(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), pattern);
  }
}
