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

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.assertj.swing.driver.JListCellBoundsQuery.cellBounds;
import static org.assertj.swing.driver.JListCellCenterQuery.cellCenter;
import static org.assertj.swing.driver.JListItemPreconditions.checkIndexInBounds;
import static org.assertj.swing.driver.JListMatchingItemQuery.matchingItemIndex;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Point;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JList;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.cell.JListCellReader;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.util.Pair;
import org.assertj.swing.util.TextMatcher;

/**
 * Performs scrolling to a specific element in a {@code JList}.
 * 
 * @author Alex Ruiz
 */
final class JListScrollToItemTask {
  static final Pair<Integer, Point> ITEM_NOT_FOUND = Pair.of(-1, null);

  @RunsInEDT
  // returns the point that the JList was scrolled to.
  static @Nonnull Point scrollToItem(final @Nonnull JList list, final int index) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        checkEnabledAndShowing(list);
        checkIndexInBounds(list, index);
        return scrollToItemWithIndex(list, index);
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  // returns the index of first matching element and the point that the JList was scrolled to.
  static @Nonnull Pair<Integer, Point> scrollToItem(final @Nonnull JList list, final @Nonnull TextMatcher matcher,
      final @Nonnull JListCellReader cellReader) {
    Pair<Integer, Point> result = execute(new GuiQuery<Pair<Integer, Point>>() {
      @Override
      protected Pair<Integer, Point> executeInEDT() {
        checkEnabledAndShowing(list);
        int index = matchingItemIndex(list, matcher, cellReader);
        if (index < 0) {
          return ITEM_NOT_FOUND;
        }
        return Pair.of(index, scrollToItemWithIndex(list, index));
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  // returns the index of first matching element and the point that the JList was scrolled to.
  static @Nonnull Pair<Integer, Point> scrollToItemIfNotSelectedYet(final @Nonnull JList list,
      final @Nonnull TextMatcher matcher, final @Nonnull JListCellReader cellReader) {
    Pair<Integer, Point> result = execute(new GuiQuery<Pair<Integer, Point>>() {
      @Override
      protected Pair<Integer, Point> executeInEDT() {
        checkEnabledAndShowing(list);
        int index = matchingItemIndex(list, matcher, cellReader);
        if (index < 0) {
          return ITEM_NOT_FOUND;
        }
        return Pair.of(index, scrollToItemWithIndexIfNotSelectedYet(list, index));
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  // returns the point that the JList was scrolled to.
  static @Nullable Point scrollToItemIfNotSelectedYet(final @Nonnull JList list, final int index) {
    return execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        checkEnabledAndShowing(list);
        checkIndexInBounds(list, index);
        return scrollToItemWithIndexIfNotSelectedYet(list, index);
      }
    });
  }

  @RunsInCurrentThread
  // returns the point that the JList was scrolled to.
  private static @Nullable Point scrollToItemWithIndexIfNotSelectedYet(final @Nonnull JList list, final int index) {
    if (list.getSelectedIndex() == index) {
      return null;
    }
    return scrollToItemWithIndex(list, index);
  }

  @RunsInCurrentThread
  private static @Nonnull Point scrollToItemWithIndex(@Nonnull JList list, int index) {
    Rectangle cellBounds = checkNotNull(cellBounds(list, index));
    list.scrollRectToVisible(cellBounds);
    return cellCenter(list, cellBounds);
  }

  private JListScrollToItemTask() {
  }
}
