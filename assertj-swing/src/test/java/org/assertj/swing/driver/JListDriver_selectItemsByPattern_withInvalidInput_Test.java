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
package org.assertj.swing.driver;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#selectItems(JList, java.util.regex.Pattern[])}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_selectItemsByPattern_withInvalidInput_Test extends JListDriver_withMocks_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Array_Of_Patterns_Is_Null() {
    Pattern[] patterns = null;
    driver.selectItems(list, patterns);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Array_Of_Patterns_Is_Empty() {
    Pattern[] patterns = new Pattern[0];
    driver.selectItems(list, patterns);
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Any_Pattern_In_Array_Is_Null() {
    Pattern[] patterns = { Pattern.compile("hello"), null };
    driver.selectItems(list, patterns);
  }
}
