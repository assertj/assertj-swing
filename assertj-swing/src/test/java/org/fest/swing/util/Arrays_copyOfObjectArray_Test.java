/*
 * Created on May 12, 2008
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
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link Arrays#copyOf(Object[])}.
 * 
 * @author Alex Ruiz
 */
public class Arrays_copyOfObjectArray_Test {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_to_copy_is_null() {
    Object[] original = null;
    Arrays.copyOf(original);
  }

  @Test
  public void should_return_empty_array_if_array_to_copy_is_emtpy() {
    assertThat(Arrays.copyOf(new Object[0])).isEmpty();
  }

  @Test
  public void should_return_copy_of_array() {
    Object[] original = { "hello", "bye" };
    Object[] copy = Arrays.copyOf(original);
    assertThat(copy).isEqualTo(original).isNotSameAs(original);
  }
}
