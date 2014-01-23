/*
 * Created on Feb 24, 2008
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
package org.fest.swing.driver;

import java.util.regex.Pattern;

import javax.swing.JList;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#selectItems(JList, java.util.regex.Pattern[])}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_selectItemsByPattern_withInvalidInput_Test extends JListDriver_withMocks_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_of_patterns_is_null() {
    Pattern[] patterns = null;
    driver.selectItems(list, patterns);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_array_of_patterns_is_empty() {
    Pattern[] patterns = new Pattern[0];
    driver.selectItems(list, patterns);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_any_pattern_in_array_is_null() {
    Pattern[] patterns = { Pattern.compile("hello"), null };
    driver.selectItems(list, patterns);
  }
}
