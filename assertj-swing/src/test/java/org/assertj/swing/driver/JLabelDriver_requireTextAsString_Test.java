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

import javax.swing.JLabel;

import org.junit.Test;

/**
 * Tests for {@link JLabelDriver#requireText(JLabel, String)}.
 * 
 * @author Yvonne Wang
 */
public class JLabelDriver_requireTextAsString_Test extends JLabelDriver_TestCase {
  @Test
  public void should_Pass_If_Text_Is_Equal_To_Expected() {
    driver.requireText(label, "Hi");
  }

  @Test
  public void should_Pass_If_Text_Matches_Expected_Pattern() {
    driver.requireText(label, "H.*");
  }

  @Test
  public void should_Fail_If_Text_Is_Not_Equal_To_Expected() {
    thrown.expectAssertionError("text", "Hi", Pattern.compile("Bye"));
    driver.requireText(label, "Bye");
  }
}
