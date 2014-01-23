/*
 * Created on May 22, 2009
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
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JComboBox;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxItemIndexPreconditions#checkItemIndexInBounds(JComboBox, int)}.
 *
 * @author Alex Ruiz
 */
public class JComboBoxItemIndexPreconditions_validateIndex_Test extends EDTSafeTestCase {
  @Test
  public void should_not_throw_error_if_index_is_in_bounds() {
    JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox, 0);
  }

  @Test
  public void should_throw_error_if_index_is_negative() {
    try {
      JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox().createNew(), -1);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("Item index (-1) should not be less than zero");
    }
  }

  @Test
  public void should_throw_error_if_JComboBox_is_empty() {
    try {
      JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox().createNew(), 0);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("JComboBox is empty");
    }
  }

  @Test
  public void should_throw_error_if_index_is_out_of_bounds() {
    try {
      JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
      JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox, 6);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo("Item index (6) should be between [0] and [2] (inclusive)");
    }
  }
}
