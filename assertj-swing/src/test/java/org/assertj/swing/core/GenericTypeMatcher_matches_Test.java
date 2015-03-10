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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JButtons.button;
import static org.assertj.swing.test.builder.JLabels.label;

import javax.annotation.Nonnull;
import javax.swing.JButton;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link GenericTypeMatcher#matches(java.awt.Component)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class GenericTypeMatcher_matches_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Type_And_Search_Criteria_Match() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(button().createNew())).isTrue();
  }

  @Test
  public void should_Return_False_If_Type_Matches_But_Not_Search_Criteria() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(button().createNew())).isFalse();
  }

  @Test
  public void should_Return_False_If_Search_Criteria_Matches_But_Not_Type() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(label().createNew())).isFalse();
  }

  @Test
  public void should_Return_False_If_Search_Criteria_And_Type_Do_Not_Match() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return false;
      }
    };
    assertThat(matcher.matches(label().createNew())).isFalse();
  }

  @Test
  public void should_Return_False_If_Component_Is_Null() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(null)).isFalse();
  }

  @Test
  public void should_Return_False_If_Component_Is_Not_Showing() {
    GenericTypeMatcher<JButton> matcher = new GenericTypeMatcher<JButton>(JButton.class, true) {
      @Override
      protected boolean isMatching(@Nonnull JButton component) {
        return true;
      }
    };
    assertThat(matcher.matches(button().createNew())).isFalse();
  }
}
