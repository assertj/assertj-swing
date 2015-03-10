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
import static org.assertj.swing.assertions.data.RgbColor.color;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsReflexive;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsSymmetric;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsTransitive;
import static org.fest.test.EqualsHashCodeContractAssert.assertMaintainsEqualsAndHashCodeContract;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link RgbColor#equals(Object)}</code> and <code>{@link RgbColor#hashCode()}</code>.
 * 
 * @author Alex Ruiz
 */
public class RgbColor_equals_hashCode_Test {

  private static RgbColor color;

  @BeforeClass
  public static void setUpOnce() {
    color = color(0xFFFFFF);
  }

  @Test
  public void should_Have_Reflexive_Equals() {
    assertEqualsIsReflexive(color);
  }

  @Test
  public void should_Have_Symmetric_Equals() {
    assertEqualsIsSymmetric(color, color(0xFFFFFF));
  }

  @Test
  public void should_Have_Transitive_Equals() {
    assertEqualsIsTransitive(color, color(0xFFFFFF), color(0xFFFFFF));
  }

  @Test
  public void should_Maintain_Equals_And_HashCode_Contract() {
    assertMaintainsEqualsAndHashCodeContract(color, color(0xFFFFFF));
  }

  @Test
  public void should_Not_Be_Equal_To_Object_Of_Different_Type() {
    assertFalse(color.equals("WHITE"));
  }

  @Test
  public void should_Not_Be_Equal_To_Null() {
    assertFalse(color.equals(null));
  }

  @Test
  public void should_Not_Be_Equal_To_RgbColor_With_Different_Red_Component() {
    assertFalse(color.equals(color(0x00FFFF)));
  }

  @Test
  public void should_Not_Be_Equal_To_RgbColor_With_Different_Green_Component() {
    assertFalse(color.equals(color(0xFF00FF)));
  }

  @Test
  public void should_Not_Be_Equal_To_RgbColor_With_Different_Blue_Component() {
    assertFalse(color.equals(color(0xFFFF00)));
  }
}
