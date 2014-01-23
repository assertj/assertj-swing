/*
 * Created on Aug 11, 2008
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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.test.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import javax.swing.table.JTableHeader;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.TextMatcher;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableHeaderLocation#pointAt(JTableHeader, TextMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableHeaderLocation_pointAtColumnWithName_Test extends JTableHeaderLocation_TestCase {
  @Rule
  public ExpectedException thrown = none();

  private TextMatcher matcher;

  @Override
  void extraSetUp() {
    matcher = mock(TextMatcher.class);
  }

  @Test
  public void should_return_point_at_column() {
    when(matcher.isMatching("0")).thenReturn(false);
    when(matcher.isMatching("1")).thenReturn(true);
    Pair<Integer, Point> pair = matchingIndexAndPoint();
    int index = pair.first;
    assertThat(index).isEqualTo(1);
    Point point = pair.second;
    assertThat(point).isEqualTo(expectedPoint(1));
  }

  @Test
  public void should_throw_error_if_matching_column_was_not_found() {
    when(matcher.isMatching("0")).thenReturn(false);
    when(matcher.isMatching("1")).thenReturn(false);
    when(matcher.description()).thenReturn("text");
    when(matcher.formattedValues()).thenReturn("'Hello'");
    thrown.expect(LocationUnavailableException.class, "Unable to find column with name matching text 'Hello'");
    matchingIndexAndPoint();
  }

  @RunsInEDT
  private Pair<Integer, Point> matchingIndexAndPoint() {
    return pointAt(location, tableHeader, matcher);
  }

  @RunsInEDT
  private static Pair<Integer, Point> pointAt(final JTableHeaderLocation l, final JTableHeader h, final TextMatcher m) {
    return execute(new GuiQuery<Pair<Integer, Point>>() {
      @Override
      protected Pair<Integer, Point> executeInEDT() {
        return l.pointAt(h, m);
      }
    });
  }
}
