/*
 * Created on Mar 13, 2008
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

import java.awt.Point;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.swing.table.JTableHeader;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.TextMatcher;

/**
 * Location of a {@code JTableHeader} (a coordinate, column index or value.)
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableHeaderLocation {
  /**
   * <p>
   * Returns the index and the coordinates of the column which name matches the value in the given {@link TextMatcher}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tableHeader the target {@code JTableHeader}.
   * @param matcher indicates which is the matching column name.
   * @return the index and the coordinates of the column under the given index.
   * @throws LocationUnavailableException if a column with a matching value cannot be found.
   */
  @RunsInCurrentThread
  public @Nonnull Pair<Integer, Point> pointAt(@Nonnull JTableHeader tableHeader, @Nonnull TextMatcher matcher) {
    int index = indexOf(tableHeader, matcher);
    if (isValidIndex(tableHeader, index)) {
      return Pair.of(index, point(tableHeader, index));
    }
    String format = "Unable to find column with name matching %s %s";
    String msg = String.format(format, matcher.description(), matcher.formattedValues());
    throw new LocationUnavailableException(msg);
  }

  @RunsInCurrentThread
  private boolean isValidIndex(@Nonnull JTableHeader tableHeader, int index) {
    int itemCount = columnCount(tableHeader);
    return (index >= 0 && index < itemCount);
  }

  /**
   * <p>
   * Returns the coordinates of the column under the given index.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tableHeader the target {@code JTableHeader}.
   * @param index the given index.
   * @return the coordinates of the column under the given index.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  @RunsInCurrentThread
  public @Nonnull Point pointAt(@Nonnull JTableHeader tableHeader, int index) {
    return point(tableHeader, checkIndexInBounds(tableHeader, index));
  }

  @RunsInCurrentThread
  private static @Nonnull Point point(@Nonnull JTableHeader tableHeader, int index) {
    Rectangle r = tableHeader.getHeaderRect(index);
    return new Point(r.x + r.width / 2, r.y + r.height / 2);
  }

  @RunsInCurrentThread
  private int checkIndexInBounds(@Nonnull JTableHeader tableHeader, int index) {
    int itemCount = columnCount(tableHeader);
    if (index >= 0 && index < itemCount) {
      return index;
    }
    String msg = String.format("Item index <%d> should be between <0> and <%d>", index, itemCount - 1);
    throw new IndexOutOfBoundsException(msg);
  }

  /**
   * <p>
   * Returns the index of the column which name matches the value in the given {@link TextMatcher}, or -1 if a matching
   * column was not found.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tableHeader the target {@code JTableHeader}.
   * @param matcher indicates which is the matching column name.
   * @return the index of a matching column or -1 if a matching column was not found.
   */
  @RunsInCurrentThread
  public int indexOf(@Nonnull JTableHeader tableHeader, @Nonnull TextMatcher matcher) {
    int size = columnCount(tableHeader);
    for (int i = 0; i < size; i++) {
      if (matcher.isMatching(columnName(tableHeader, i))) {
        return i;
      }
    }
    return -1;
  }

  @RunsInCurrentThread
  private int columnCount(@Nonnull JTableHeader header) {
    return header.getColumnModel().getColumnCount();
  }

  @RunsInCurrentThread
  private String columnName(@Nonnull JTableHeader tableHeader, int index) {
    return tableHeader.getTable().getModel().getColumnName(index);
  }
}
