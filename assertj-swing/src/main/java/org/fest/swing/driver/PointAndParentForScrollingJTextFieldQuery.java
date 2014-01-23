/*
 * Created on Oct 21, 2008
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

import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;

import javax.annotation.Nonnull;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.util.Pair;

/**
 * <p>
 * Returns the point and parent to use as a reference when scrolling a {@code JTextField} up or down.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class PointAndParentForScrollingJTextFieldQuery {
  @RunsInCurrentThread
  static @Nonnull Pair<Point, Container> pointAndParentForScrolling(final @Nonnull JTextField textField) {
    Point origin = new Point(textField.getX(), textField.getY());
    Container parent = textField.getParent();
    while (parent != null && !(parent instanceof JComponent) && !(parent instanceof CellRendererPane)) {
      origin = addRectangleToPoint(checkNotNull(parent.getBounds()), origin);
      parent = parent.getParent();
    }
    return Pair.of(origin, parent);
  }

  private static @Nonnull Point addRectangleToPoint(@Nonnull Rectangle r, @Nonnull Point p) {
    Point newPoint = new Point(p);
    newPoint.x += r.x;
    newPoint.y += r.y;
    return newPoint;
  }

  private PointAndParentForScrollingJTextFieldQuery() {}
}
