/*
 * Created on Jul 29, 2009
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
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Arrays#equal(String[][], String[][])}.
 * 
 * @author Alex Ruiz
 */
public class Arrays_equal_Test {
  @Test
  public void should_return_equal_arrays_jf_both_arrays_are_null() {
    assertThat(Arrays.equal(null, null)).isTrue();
  }

  @Test
  public void should_return_equal_arrays_If_both_arrays_are_empty() {
    assertThat(Arrays.equal(new String[0][], new String[0][])).isTrue();
  }

  @Test
  public void should_return_not_equal_arrays_if_only_first_array_is_null() {
    assertThat(Arrays.equal(null, new String[0][])).isFalse();
  }

  @Test
  public void should_return_not_equal_arrays_if_only_second_array_is_null() {
    assertThat(Arrays.equal(new String[0][], null)).isFalse();
  }

  @Test
  public void should_return_not_equal_arrays_if_arrays_have_different_dimensions() {
    assertThat(Arrays.equal(new String[0][], new String[1][0])).isFalse();
  }

  @Test
  public void should_return_not_equal_arrays_if_arrays_have_different_second_dimensions() {
    String[][] one = { { "Hello" } };
    String[][] two = { { "Hello", "Bye" } };
    assertThat(Arrays.equal(one, two)).isFalse();
  }

  @Test
  public void should_return_not_equal_arrays_if_Arrays_have_different_data() {
    String[][] one = { { "Hello" } };
    String[][] two = { { "Bye" } };
    assertThat(Arrays.equal(one, two)).isFalse();
  }

  @Test
  public void should_return_equal_arrays_If_arrays_are_equal() {
    String[][] one = { { "Hello" } };
    String[][] two = { { "Hello" } };
    assertThat(Arrays.equal(one, two)).isTrue();
  }
}
