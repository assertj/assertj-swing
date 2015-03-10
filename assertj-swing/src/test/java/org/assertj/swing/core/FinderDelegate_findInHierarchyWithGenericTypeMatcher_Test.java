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
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.swing.JTextField;

import org.junit.Test;

/**
 * Tests for {@link FinderDelegate#find(org.assertj.swing.hierarchy.ComponentHierarchy, GenericTypeMatcher)}.
 * 
 * @author Alex Ruiz
 */
public class FinderDelegate_findInHierarchyWithGenericTypeMatcher_Test extends FinderDelegate_TestCase {
  @Test
  public void should_Return_Components_Matching_GenericTypeMatcher() {
    GenericTypeMatcher<JTextField> matcher = new GenericTypeMatcher<JTextField>(JTextField.class) {
      @Override
      protected boolean isMatching(@Nonnull JTextField textField) {
        return "textBox".equals(textField.getName());
      }
    };
    Collection<JTextField> found = finder.find(hierarchy, matcher);
    assertThat(found).containsOnly(window.textField);
  }

  @Test
  public void should_Return_Empty_Collection_If_Matching_Components_Not_Found() {
    Collection<JTextField> found = finder.find(hierarchy, neverMatches(JTextField.class));
    assertThat(found).isEmpty();
  }
}
