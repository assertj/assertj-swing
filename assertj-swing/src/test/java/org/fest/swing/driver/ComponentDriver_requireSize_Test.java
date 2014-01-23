/*
 * Created on Jul 19, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Dimension;

import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#requireSize(java.awt.Component, java.awt.Dimension)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_requireSize_Test extends ComponentDriver_TestCase {
  @Test
  public void should_pass_if_actual_size_is_equal_to_expected() {
    Dimension expected = sizeOf(window.button);
    driver.requireSize(window.button, expected);
  }

  @Test
  public void should_fail_if_actual_size_is_not_equal_to_expected() {
    showWindow();
    try {
      driver.requireSize(window.button, new Dimension(0, 0));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'size'")
                                .contains("expected:<...awt.Dimension[width=[0,height=0]]>")
                                .contains("but was:<");
    }
  }
}
