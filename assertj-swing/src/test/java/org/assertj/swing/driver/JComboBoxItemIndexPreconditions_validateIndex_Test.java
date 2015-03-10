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
package org.assertj.swing.driver;

import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.builder.JComboBoxes.comboBox;

import javax.swing.JComboBox;

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JComboBoxItemIndexPreconditions#checkItemIndexInBounds(JComboBox, int)}.
 * 
 * @author Alex Ruiz
 */
public class JComboBoxItemIndexPreconditions_validateIndex_Test extends EDTSafeTestCase {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_Not_Throw_Error_If_Index_Is_In_Bounds() {
    JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox, 0);
  }

  @Test
  public void should_Throw_Error_If_Index_Is_Negative() {
    thrown.expectIndexOutOfBoundsException("Item index (-1) should not be less than zero");
    JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox().createNew(), -1);
  }

  @Test
  public void should_Throw_Error_If_JComboBox_Is_Empty() {
    thrown.expectIndexOutOfBoundsException("JComboBox is empty");
    JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox().createNew(), 0);
  }

  @Test
  public void should_Throw_Error_If_Index_Is_Out_Of_Bounds() {
    thrown.expectIndexOutOfBoundsException("Item index (6) should be between [0] and [2] (inclusive)");
    JComboBox comboBox = comboBox().withItems("One", "Two", "Three").createNew();
    JComboBoxItemIndexPreconditions.checkItemIndexInBounds(comboBox, 6);
  }
}
