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

import static org.assertj.swing.awt.AWT.centerOf;

import java.awt.Point;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.swing.JList;

import org.assertj.swing.annotation.RunsInCurrentThread;

/**
 * Calculates the centre of a cell in a {@code JList}.
 *
 * @author Alex Ruiz
 */
final class JListCellCenterQuery {
  /*
   * Sometimes the cell is not completely contained in the visible rectangle of the JList (e.g. when a list item
   * has long text and the JList is in a JScrollPane). In this case, we return the center of the intersection of the
   * visible rectangle of the JList and the cell bounds. (issue FEST-65).
   */
  @RunsInCurrentThread
  static @Nonnull Point cellCenter(@Nonnull JList<?> list, @Nonnull Rectangle cellBounds) {
    Point cellCenter = centerOf(cellBounds);
    Rectangle visibleRect = list.getVisibleRect();
    if (visibleRect.contains(cellCenter)) {
      return cellCenter;
    }
    Rectangle intersection = visibleRect.intersection(cellBounds);
    return centerOf(intersection);
  }

  private JListCellCenterQuery() {
  }
}
