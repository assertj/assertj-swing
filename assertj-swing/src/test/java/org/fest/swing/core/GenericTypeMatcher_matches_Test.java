/*
 * Created on Aug 6, 2007
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JLabels.label;

import javax.annotation.Nonnull;
import javax.swing.JButton;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link GenericTypeMatcher#matches(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class GenericTypeMatcher_matches_Test extends EDTSafeTestCase {
  @Test
  public void should_return_true_if_type_and_search_criteria_match() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(button().createNew())).isTrue();
  }

  public void should_return_false_if_type_matches_but_not_search_criteria() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(button().createNew())).isFalse();
  }

  public void should_return_false_if_search_criteria_matches_but_not_type() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(label().createNew())).isFalse();
  }

  public void should_return_false_if_search_criteria_and_type_do_not_match() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(label().createNew())).isFalse();
  }

  public void should_return_false_if_Component_is_null() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(null)).isFalse();
  }

  public void should_return_false_if_Component_is_not_showing() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class, true) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(button().createNew())).isFalse();
  }
}
