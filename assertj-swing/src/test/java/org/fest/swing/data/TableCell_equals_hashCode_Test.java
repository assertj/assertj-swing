/*
 * Created on Apr 30, 2009
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
package org.fest.swing.data;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsReflexive;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsSymmetric;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsTransitive;
import static org.fest.test.EqualsHashCodeContractAssert.assertMaintainsEqualsAndHashCodeContract;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link TableCell#equals(Object)} and {@link TableCell#hashCode()}.
 * 
 * @author Alex Ruiz
 */
public class TableCell_equals_hashCode_Test implements EqualsHashCodeContractTestCase {
  private TableCell cell;

  @Before
  public void setUp() {
    cell = TableCell.row(6).column(8);
  }

  @Test
  public void should_have_consistent_equals() {
    TableCell other = TableCell.row(6).column(8);
    assertThat(cell.equals(other)).isTrue();
  }

  @Test
  public void should_have_reflexive_equals() {
    assertEqualsIsReflexive(cell);
  }

  @Test
  public void should_have_symmetric_equals() {
    TableCell other = TableCell.row(6).column(8);
    assertEqualsIsSymmetric(cell, other);
  }

  @Test
  public void should_have_transitive_equals() {
    TableCell other1 = TableCell.row(6).column(8);
    TableCell other2 = TableCell.row(6).column(8);
    assertEqualsIsTransitive(cell, other1, other2);
  }

  @Override
  @Test
  public void should_maintain_equals_and_hashCode_contract() {
    TableCell other = TableCell.row(6).column(8);
    assertMaintainsEqualsAndHashCodeContract(cell, other);
  }

  @Override
  @Test
  public void should_not_be_equal_to_null() {
    assertThat(cell.equals(null)).isFalse();
  }

  @Test
  public void should_not_be_equal_to_Object_not_being_of_same_type() {
    assertThat(cell.equals("Hello")).isFalse();
  }

  @Test
  public void should_return_not_equal_if_row_values_are_not_equal() {
    TableCell other = TableCell.row(8).column(8);
    assertThat(cell.equals(other)).isFalse();
  }

  @Test
  public void should_return_not_equal_if_column_values_are_not_equal() {
    TableCell other = TableCell.row(6).column(6);
    assertThat(cell.equals(other)).isFalse();
  }

  @Override
  public void should_not_be_equal_to_Object_of_different_type() {
    // TODO Auto-generated method stub

  }

  @Override
  public void equals_should_be_consistent() {
    // TODO Auto-generated method stub

  }

  @Override
  public void equals_should_be_reflexive() {
    // TODO Auto-generated method stub

  }

  @Override
  public void equals_should_be_symmetric() {
    // TODO Auto-generated method stub

  }

  @Override
  public void equals_should_be_transitive() {
    // TODO Auto-generated method stub

  }
}
