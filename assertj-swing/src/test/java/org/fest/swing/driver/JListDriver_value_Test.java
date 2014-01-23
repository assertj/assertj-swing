/*
 * Created on Feb 24, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#value(javax.swing.JList, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_value_Test extends JListDriver_TestCase {
  @Test
  public void should_return_text_of_item() {
    Object text = driver.value(list, 0);
    assertThat(text).isEqualTo("one");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_throw_error_if_index_is_out_of_bounds() {
    try {
      driver.value(list, 6);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException expected) {
      assertThat(expected.getMessage()).isEqualTo("Item index (6) should be between [0] and [2] (inclusive)");
    }
  }
}
