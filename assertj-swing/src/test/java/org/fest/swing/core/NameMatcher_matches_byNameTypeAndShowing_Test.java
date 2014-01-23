/*
 * Created on Aug 5, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.Test;

/**
 * Tests for {@link NameMatcher#matches(java.awt.Component)}.
 * <p>
 * The {@link NameMatcher} is created through {@link NameMatcher#NameMatcher(String, Class, boolean)}, passing
 * {@code true} to indicate that the {@link java.awt.Component} to match must be showing on the screen.
 * </p>
 * 
 * @author Alex Ruiz
 */
public class NameMatcher_matches_byNameTypeAndShowing_Test extends NameMatcher_TestCase {
  @Test
  public void should_return_false_if_type_does_not_match() {
    window.display();
    NameMatcher matcher = new NameMatcher("b", JTextField.class, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  @Test
  public void should_return_false_if_name_and_type_match_but_Component_is_not_showing() {
    NameMatcher matcher = new NameMatcher(LABEL_TEXT, JButton.class, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

  @Test
  public void should_return_false_if_nothing_matches() {
    NameMatcher matcher = new NameMatcher("b", JTextField.class, true);
    assertThat(matcher.matches(window.button)).isFalse();
  }

}
