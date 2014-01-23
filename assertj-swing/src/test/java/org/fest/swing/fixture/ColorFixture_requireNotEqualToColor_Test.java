/*
 * Created on Aug 3, 2009
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

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link ColorFixture#requireNotEqualTo(java.awt.Color)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ColorFixture_requireNotEqualToColor_Test {
  @Test
  public void should_pass_if_Colors_are_not_equal() {
    ColorFixture fixture = new ColorFixture(BLUE);
    fixture.requireNotEqualTo(RED);
  }

  @Test(expected = AssertionError.class)
  public void should_fail_if_Colors_are_equal() {
    ColorFixture fixture = new ColorFixture(BLUE);
    fixture.requireNotEqualTo(BLUE);
  }

  @Test
  public void should_fail_showing_description_if_Colors_are_equal() {
    ColorFixture fixture = new ColorFixture(BLUE, "test");
    try {
      fixture.requireNotEqualTo(BLUE);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("test");
    }
  }
}
