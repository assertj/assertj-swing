/*
 * Created on Nov 3, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.swing.JList;

import org.fest.swing.cell.JListCellReader;
import org.fest.swing.util.TextMatcher;
import org.junit.Test;

/**
 * Tests for {@link JListMatchingItemQuery#matchingItemIndices(JList, TextMatcher, JListCellReader)}.
 *
 * @author Alex Ruiz
 */
public class JListMatchingItemQuery_matchingItemIndices_Test extends JListMatchingItemQuery_TestCase {
  @Test
  public void should_return_indices_of_matching_items() {
    TextMatcher matcher = mockTextMatcher();
    when(matcher.isMatching("Yoda")).thenReturn(false);
    when(matcher.isMatching("Luke")).thenReturn(true);
    List<Integer> indices = JListMatchingItemQuery.matchingItemIndices(list, matcher, cellReader);
    assertThat(indices).hasSize(1).containsOnly(1);
  }

  @Test
  public void should_return_empty_list_if_matching_indices_not_found() {
    TextMatcher matcher = mockTextMatcher();
    when(matcher.isMatching("Yoda")).thenReturn(false);
    when(matcher.isMatching("Luke")).thenReturn(false);
    List<Integer> indices = JListMatchingItemQuery.matchingItemIndices(list, matcher, cellReader);
    assertThat(indices).isEmpty();
  }
}
