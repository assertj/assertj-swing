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
package org.assertj.swing.finder;

import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import javax.swing.JOptionPane;

import org.assertj.swing.core.GenericTypeMatcher;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFinder#findOptionPane()}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JOptionPaneFinder_findOptionPane_withMatcher_withInvalidInput_Test {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Matcher_Is_Null() {
    GenericTypeMatcher<JOptionPane> matcher = null;
    JOptionPaneFinder.findOptionPane(matcher);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Timeout_Is_Negative() {
    JOptionPaneFinder.findOptionPane(neverMatches(JOptionPane.class)).withTimeout(-20);
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Time_Unit_Is_Null() {
    JOptionPaneFinder.findOptionPane(neverMatches(JOptionPane.class)).withTimeout(10, null);
  }
}
