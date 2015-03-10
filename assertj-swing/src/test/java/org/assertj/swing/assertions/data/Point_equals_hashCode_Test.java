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
package org.assertj.swing.assertions.data;

import static junit.framework.Assert.assertFalse;
import static org.assertj.swing.assertions.data.Point.atPoint;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsReflexive;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsSymmetric;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsTransitive;
import static org.fest.test.EqualsHashCodeContractAssert.assertMaintainsEqualsAndHashCodeContract;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link Index#equals(Object)}</code> and <code>{@link Index#hashCode()}</code>.
 * 
 * @author Yvonne Wang
 */
public class Point_equals_hashCode_Test {

  private static Point point;

  @BeforeClass
  public static void setUpOnce() {
    point = atPoint(6, 8);
  }

  @Test
  public void should_Have_Reflexive_Equals() {
    assertEqualsIsReflexive(point);
  }

  @Test
  public void should_Have_Symmetric_Equals() {
    assertEqualsIsSymmetric(point, atPoint(6, 8));
  }

  @Test
  public void should_Have_Transitive_Equals() {
    assertEqualsIsTransitive(point, atPoint(6, 8), atPoint(6, 8));
  }

  @Test
  public void should_Maintain_Equals_And_HashCode_Contract() {
    assertMaintainsEqualsAndHashCodeContract(point, atPoint(6, 8));
  }

  @Test
  public void should_Not_Be_Equal_To_Object_Of_Different_Type() {
    assertFalse(point.equals("6, 8"));
  }

  @Test
  public void should_Not_Be_Equal_To_Null() {
    assertFalse(point.equals(null));
  }

  @Test
  public void should_Not_Be_Equal_To_Index_With_Different_X() {
    assertFalse(point.equals(atPoint(8, 8)));
  }

  @Test
  public void should_Not_Be_Equal_To_Index_With_Different_Y() {
    assertFalse(point.equals(atPoint(6, 6)));
  }
}
