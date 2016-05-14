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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.swing.util.TextMatcher;
import org.junit.Test;

/**
 * Tests for {@link JListMatchingItemQuery#matchingItemValues(JList, TextMatcher, JListCellReader)}.
 * 
 * @author Alex Ruiz
 */
public class JListMatchingItemQuery_matchingItemValues_Test extends JListMatchingItemQuery_TestCase {
  @Test
  public void should_Return_Values_Of_Matching_Items() {
    TextMatcher matcher = mockTextMatcher();
    when(matcher.isMatching("Yoda")).thenReturn(false);
    when(matcher.isMatching("Luke")).thenReturn(true);
    List<String> values = JListMatchingItemQuery.matchingItemValues(list, matcher, cellReader);
    assertThat(values).hasSize(1).containsOnly("Luke");
  }

  @Test
  public void should_Return_Empty_List_If_Matching_Values_Not_Found() {
    TextMatcher matcher = mockTextMatcher();
    when(matcher.isMatching("Yoda")).thenReturn(false);
    when(matcher.isMatching("Luke")).thenReturn(false);
    List<String> values = JListMatchingItemQuery.matchingItemValues(list, matcher, cellReader);
    assertThat(values).isEmpty();
  }
}
