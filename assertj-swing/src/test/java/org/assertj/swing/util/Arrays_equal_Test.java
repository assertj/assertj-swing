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
package org.assertj.swing.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Arrays#equal(String[][], String[][])}.
 * 
 * @author Alex Ruiz
 */
public class Arrays_equal_Test {
  @Test
  public void should_Return_Equal_Arrays_Jf_Both_Arrays_Are_Null() {
    assertThat(Arrays.equal(null, null)).isTrue();
  }

  @Test
  public void should_Return_Equal_Arrays_If_Both_Arrays_Are_Empty() {
    assertThat(Arrays.equal(new String[0][], new String[0][])).isTrue();
  }

  @Test
  public void should_Return_Not_Equal_Arrays_If_Only_First_Array_Is_Null() {
    assertThat(Arrays.equal(null, new String[0][])).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_Arrays_If_Only_Second_Array_Is_Null() {
    assertThat(Arrays.equal(new String[0][], null)).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_Arrays_If_Arrays_Have_Different_Dimensions() {
    assertThat(Arrays.equal(new String[0][], new String[1][0])).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_Arrays_If_Arrays_Have_Different_Second_Dimensions() {
    String[][] one = { { "Hello" } };
    String[][] two = { { "Hello", "Bye" } };
    assertThat(Arrays.equal(one, two)).isFalse();
  }

  @Test
  public void should_Return_Not_Equal_Arrays_If_Arrays_Have_Different_Data() {
    String[][] one = { { "Hello" } };
    String[][] two = { { "Bye" } };
    assertThat(Arrays.equal(one, two)).isFalse();
  }

  @Test
  public void should_Return_Equal_Arrays_If_Arrays_Are_Equal() {
    String[][] one = { { "Hello" } };
    String[][] two = { { "Hello" } };
    assertThat(Arrays.equal(one, two)).isTrue();
  }
}
