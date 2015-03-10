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
 * Tests for {@link Index#equals(Object)} and {@link Index#hashCode()}.
 * 
 * @author Alex Ruiz
 */
public class Index_equals_hashCode_Test implements EqualsHashCodeContractTestCase {
  private Index index;

  @Before
  public void setUp() {
    index = Index.atIndex(8);
  }

  @Test
  public void should_Have_Consistent_Equals() {
    Index other = Index.atIndex(8);
    assertThat(index.equals(other)).isTrue();
  }

  @Test
  public void should_Have_Reflexive_Equals() {
    assertEqualsIsReflexive(index);
  }

  @Test
  public void should_Have_Symmetric_Equals() {
    Index other = Index.atIndex(8);
    assertEqualsIsSymmetric(index, other);
  }

  @Test
  public void should_Have_Transitive_Equals() {
    Index other1 = Index.atIndex(8);
    Index other2 = Index.atIndex(8);
    assertEqualsIsTransitive(index, other1, other2);
  }

  @Override
  @Test
  public void should_maintain_equals_and_hashCode_contract() {
    Index other = Index.atIndex(8);
    assertMaintainsEqualsAndHashCodeContract(index, other);
  }

  @Override
  @Test
  public void should_not_be_equal_to_null() {
    assertThat(index.equals(null)).isFalse();
  }

  @Test
  public void should_Not_Be_Equal_To_Object_Not_Being_Of_Same_Type() {
    assertThat(index.equals("Hello")).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_If_Index_Values_Are_Not_Equal() {
    Index other = Index.atIndex(6);
    assertThat(index.equals(other)).isFalse();
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
