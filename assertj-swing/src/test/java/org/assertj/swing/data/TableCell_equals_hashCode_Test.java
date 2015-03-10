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
package org.assertj.swing.data;

import static org.assertj.core.api.Assertions.assertThat;
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
  public void should_Have_Consistent_Equals() {
    TableCell other = TableCell.row(6).column(8);
    assertThat(cell.equals(other)).isTrue();
  }

  @Test
  public void should_Have_Reflexive_Equals() {
    assertEqualsIsReflexive(cell);
  }

  @Test
  public void should_Have_Symmetric_Equals() {
    TableCell other = TableCell.row(6).column(8);
    assertEqualsIsSymmetric(cell, other);
  }

  @Test
  public void should_Have_Transitive_Equals() {
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
  public void should_Not_Be_Equal_To_Object_Not_Being_Of_Same_Type() {
    assertThat(cell.equals("Hello")).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_If_Row_Values_Are_Not_Equal() {
    TableCell other = TableCell.row(8).column(8);
    assertThat(cell.equals(other)).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_If_Column_Values_Are_Not_Equal() {
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
