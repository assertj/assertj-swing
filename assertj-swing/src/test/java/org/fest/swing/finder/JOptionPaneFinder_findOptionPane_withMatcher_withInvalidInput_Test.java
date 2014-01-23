/*
 * Created on Oct 29, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.finder;

import static org.fest.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import javax.swing.JOptionPane;

import org.fest.swing.core.GenericTypeMatcher;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFinder#findOptionPane()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JOptionPaneFinder_findOptionPane_withMatcher_withInvalidInput_Test {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_matcher_is_null() {
    GenericTypeMatcher<JOptionPane> matcher = null;
    JOptionPaneFinder.findOptionPane(matcher);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_timeout_is_negative() {
    JOptionPaneFinder.findOptionPane(neverMatches(JOptionPane.class)).withTimeout(-20);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_time_unit_is_null() {
    JOptionPaneFinder.findOptionPane(neverMatches(JOptionPane.class)).withTimeout(10, null);
  }
}
