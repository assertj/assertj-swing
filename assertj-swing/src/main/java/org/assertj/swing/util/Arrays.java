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

import static java.lang.System.lineSeparator;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.core.util.Strings.quote;

import java.lang.reflect.Array;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.swing.internal.annotation.InternalApi;

/**
 * Utility methods for arrays.
 *
 * @author Alex Ruiz
 */
public final class Arrays {
  private static final String NO_COLUMNS = "[[]]";
  private static final String NO_ROWS = "[]";
  private static final String NULL = "null";

  /**
   * Verifies that the given {@code String} arrays are equal.
   *
   * @param one the first array.
   * @param two the second array.
   * @return {@code true} if the arrays are equal, {@code false} otherwise.
   */
  public static boolean equal(@Nullable String[][] one, @Nullable String[][] two) {
    if (one == null && two == null) {
      return true;
    }
    if (one == null || two == null) {
      return false;
    }
    if (one.length != two.length) {
      return false;
    }
    ;
    if (one.length == 0) {
      return true;
    }
    if (one[0].length != two[0].length) {
      return false;
    }
    for (int i = 0; i < one.length; i++) {
      for (int j = 0; j < one[i].length; j++) {
        if (!areEqual(one[i][j], two[i][j])) {
          return false;
        }
      }
    }
    return true;
  }

  @InternalApi
  public static String format(Object object) {
    return new StandardRepresentation().toStringOf(object);
  }

  /**
   * Formats a two-dimensional {@code String} array. For example, the array:
   *
   * <pre>
   * String[][] array = { { &quot;0-0&quot;, &quot;0-1&quot;, &quot;0-2&quot; }, { &quot;1-0&quot;, &quot;1-1&quot;,
   * &quot;1-2&quot; }, { &quot;2-0&quot;, &quot;2-1&quot;, &quot;2-2&quot; },
   * { &quot;3-0&quot;, &quot;3-1&quot;, &quot;3-2&quot; }, };
   * </pre>
   *
   * will be formatted as:
   *
   * <pre>
   * [['0-0', '0-1', '0-2'],
   * ['1-0', '1-1', '1-2'],
   * ['2-0', '2-1', '2-2'],
   * ['3-0', '3-1', '3-2']]
   * </pre>
   *
   * @param array the array to format.
   * @return the data of the given array formatted to make it easier to read.
   */
  public static @Nonnull String format(@Nullable String[][] array) {
    if (array == null) {
      return NULL;
    }
    int size = array.length;
    if (size == 0) {
      return NO_ROWS;
    }
    if (array[0].length == 0) {
      return NO_COLUMNS;
    }
    StringBuilder b = new StringBuilder();
    b.append("[");
    for (int i = 0; i < size; i++) {
      if (i != 0) {
        b.append(lineSeparator()).append(" ");
      }
      addLine(array[i], b);
      if (i != size - 1) {
        b.append(",");
      }
    }
    b.append("]");
    return b.toString();
  }

  private static void addLine(@Nullable String[] line, @Nonnull StringBuilder b) {
    if (line == null) {
      b.append(NULL);
      return;
    }
    int lineSize = line.length;
    b.append("[");
    for (int i = 0; i < lineSize; i++) {
      b.append(quote(line[i]));
      if (i != lineSize - 1) {
        b.append(", ");
      }
    }
    b.append("]");
  }

  /**
   * Creates and returns a copy of the given array.
   *
   * @param array the array to copy.
   * @return the created copy.
   * @throws NullPointerException if the array to copy is {@code null}.
   */
  public static @Nonnull int[] copyOf(@Nonnull int[] array) {
    checkNotNull(array);
    int arraySize = array.length;
    int[] copy = new int[arraySize];
    for (int i = 0; i < arraySize; i++) {
      copy[i] = array[i];
    }
    return copy;
  }

  /**
   * Creates and returns a copy of the given array.
   *
   * @param <T> the type of the values in this array.
   * @param array the array to copy.
   * @return the created copy.
   * @throws NullPointerException if the array to copy is {@code null}.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] copyOf(@Nonnull T[] array) {
    checkNotNull(array);
    int arraySize = array.length;
    T[] copy = (T[]) Array.newInstance(array.getClass().getComponentType(), arraySize);
    for (int i = 0; i < arraySize; i++) {
      copy[i] = array[i];
    }
    return copy;
  }

  private Arrays() {
  }
}
