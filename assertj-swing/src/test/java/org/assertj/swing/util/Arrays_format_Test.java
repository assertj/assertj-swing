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
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.SystemProperties.LINE_SEPARATOR;

import org.junit.Test;

/**
 * Tests for {@link Arrays#format(String[][])}.
 * 
 * @author Alex Ruiz
 */
public class Arrays_format_Test {
  @Test
  public void should_Return_Null_If_Array_Is_Null() {
    assertThat(Arrays.format(null)).isEqualTo("null");
  }

  @Test
  public void should_Return_Empty_Brackets_If_First_Dimension_Is_Zero() {
    String[][] array = new String[0][];
    assertThat(Arrays.format(array)).isEqualTo("[]");
  }

  @Test
  public void should_Return_Empty_Brackets_If_Second_Dimension_Is_Zero() {
    String[][] array = new String[1][0];
    assertThat(Arrays.format(array)).isEqualTo("[[]]");
  }

  @Test
  public void should_Format_Array() {
    String[][] array = { { "0-0", "0-1", "0-2" }, { "1-0", "1-1", "1-2" }, { "2-0", "2-1", "2-2" },
        { "3-0", "3-1", "3-2" }, };
    String formatted = concat("[['0-0', '0-1', '0-2'],", LINE_SEPARATOR, " ['1-0', '1-1', '1-2'],", LINE_SEPARATOR,
        " ['2-0', '2-1', '2-2'],", LINE_SEPARATOR, " ['3-0', '3-1', '3-2']]");
    assertThat(Arrays.format(array)).isEqualTo(formatted);
  }
}
