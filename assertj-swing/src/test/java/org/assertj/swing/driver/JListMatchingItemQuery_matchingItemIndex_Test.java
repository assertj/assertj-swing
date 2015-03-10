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
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.mockito.Mockito.when;

import javax.swing.JList;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JListCellReader;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.util.TextMatcher;
import org.junit.Test;

/**
 * Tests for {@link JListMatchingItemQuery#matchingItemIndex(JList, TextMatcher, JListCellReader)}.
 * 
 * @author Alex Ruiz
 */
public class JListMatchingItemQuery_matchingItemIndex_Test extends JListMatchingItemQuery_TestCase {
  @Test
  public void should_Return_Index_Of_Matching_Item() {
    TextMatcher matcher = mockTextMatcher();
    when(matcher.isMatching("Yoda")).thenReturn(false);
    when(matcher.isMatching("Luke")).thenReturn(true);
    assertThat(matchingItemIndex(matcher)).isEqualTo(1);
  }

  @Test
  public void should_Return_Negative_One_If_Matching_Item_Not_Found() {
    TextMatcher matcher = mockTextMatcher();
    when(matcher.isMatching("Yoda")).thenReturn(false);
    when(matcher.isMatching("Luke")).thenReturn(false);
    assertThat(matchingItemIndex(matcher)).isEqualTo(-1);
  }

  @RunsInEDT
  private int matchingItemIndex(final TextMatcher matcher) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return JListMatchingItemQuery.matchingItemIndex(list, matcher, cellReader);
      }
    });
  }
}
