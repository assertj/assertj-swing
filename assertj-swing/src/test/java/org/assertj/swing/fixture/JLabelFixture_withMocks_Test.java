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

import javax.swing.JLabel;

import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JLabelDriver;
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
  public void should_Return_Text_Using_Driver() {
    JLabelDriver driver = fixture.driver();
    JLabel target = fixture.target();
    when(driver.textOf(target)).thenReturn("Six");
    assertThat(fixture.text()).isEqualTo("Six");
    verify(driver).textOf(target);
  }

  @Test
  public void should_Call_RequireText_With_Text_And_Return_Self() {
    assertThat(fixture.requireText("Six")).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), "Six");
  }

  @Test
  public void should_Call_RequireText_With_Pattern_And_Return_Self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.requireText(pattern)).isSameAs(fixture);
    verify(fixture.driver()).requireText(fixture.target(), pattern);
  }
}
