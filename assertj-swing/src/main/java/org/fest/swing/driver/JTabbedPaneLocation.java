/*
 * Created on Jan 27, 2008
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

import static org.fest.swing.driver.JTabbedPaneTabIndexQuery.indexOfTab;

import java.awt.Point;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTabbedPane;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.StringTextMatcher;
import org.fest.swing.util.TextMatcher;
import org.fest.util.InternalApi;
import org.fest.util.VisibleForTesting;

/**
 * A location on a {@code JTabbedPane} (notably a tab).
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@InternalApi
public class JTabbedPaneLocation {
  /**
   * <p>
   * Returns the index of the first tab that matches the given {@code String}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param title the title to match.
   * @return the index of the first tab that matches the given {@code String}.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  @RunsInCurrentThread
  public int indexOf(@Nonnull JTabbedPane tabbedPane, @Nullable String title) {
    return indexOf(tabbedPane, new StringTextMatcher(title));
  }

  /**
   * <p>
   * Returns the index of the first tab whose title matches the value in the given {@link TextMatcher}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param matcher indicates if the text of the {@code JTabbedPane} matches the value we are looking for.
   * @return the index of the first tab that matches the given {@code String}.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  @RunsInCurrentThread
  public int indexOf(final @Nonnull JTabbedPane tabbedPane, final @Nonnull TextMatcher matcher) {
    int index = indexOfTab(tabbedPane, matcher);
    if (index >= 0) {
      return index;
    }
    String format = "Unable to find a tab with title matching %s %s";
    String msg = String.format(format, matcher.description(), matcher.formattedValues());
    throw new LocationUnavailableException(msg);
  }

  /**
   * <p>
   * Returns the coordinates of the tab under the given index.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param tabbedPane the target {@code JTabbedPane}.
   * @param index the given index.
   * @return the coordinates of the tab under the given index.
   * @throws IndexOutOfBoundsException if the given index is negative or out of bounds.
   * @throws LocationUnavailableException if the tab under the given index is not visible.
   */
  @RunsInCurrentThread
  public @Nonnull Point pointAt(final @Nonnull JTabbedPane tabbedPane, final int index) {
    checkIndexInBounds(tabbedPane, index);
    Rectangle rect = tabbedPane.getUI().getTabBounds(tabbedPane, index);
    // From Abbot: TODO figure out the effects of tab layout policy sometimes tabs are not directly visible
    if (rect == null || rect.x < 0) {
      String msg = String.format("The tab %d is not visible", index);
      throw new LocationUnavailableException(msg);
    }
    return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
  }

  @VisibleForTesting
  @RunsInCurrentThread
  void checkIndexInBounds(JTabbedPane tabbedPane, int index) {
    int max = tabbedPane.getTabCount() - 1;
    if (index >= 0 && index <= max) {
      return;
    }
    String format = "Index <%d> is not within the JTabbedPane bounds of <0> and <%d> (inclusive)";
    String msg = String.format(format, index, max);
    throw new IndexOutOfBoundsException(msg);
  }
}
