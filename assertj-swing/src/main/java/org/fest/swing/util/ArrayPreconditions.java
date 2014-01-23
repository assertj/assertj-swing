/*
 * Created on Feb 7, 2013
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
 * Copyright @2013 the original author or authors.
 */
package org.fest.swing.util;

import javax.annotation.Nullable;

/**
 * Verifies correct state of arrays.
 * 
 * @author Alex Ruiz
 */
public final class ArrayPreconditions {
  /**
   * Verifies that the given array is not {@code null} or empty.
   * 
   * @param array the given array.
   * @return the validated array.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws IllegalArgumentException if the given array is empty.
   */
  public static int[] checkNotNullOrEmpty(@Nullable int[] array) {
    if (array == null) {
      throw new NullPointerException();
    }
    if (array.length == 0) {
      throw new IllegalArgumentException();
    }
    return array;
  }

  private ArrayPreconditions() {}
}
