/*
 * Created on Jul 31, 2008
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

import javax.annotation.Nonnull;
import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * A location in a horizontal {@code JScrollBar}.
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
class VerticalJScrollBarLocation extends JScrollBarLocationStrategy {
  @RunsInCurrentThread
  @Override
  @Nonnull Point thumbLocation(@Nonnull JScrollBar scrollBar, double fraction) {
    int arrow = arrow(scrollBar);
    return new Point(arrow / 2, arrow + (int) (fraction * (scrollBar.getHeight() - 2 * arrow)));
  }

  @RunsInCurrentThread
  @Override
  @Nonnull Point blockLocation(@Nonnull JScrollBar scrollBar, @Nonnull Point unitLocation, int offset) {
    Point p = new Point(unitLocation);
    p.y += offset;
    return p;
  }

  @RunsInCurrentThread
  @Override
  @Nonnull Point unitLocationToScrollDown(@Nonnull JScrollBar scrollBar) {
    int arrow = arrow(scrollBar);
    return new Point(arrow / 2, scrollBar.getHeight() - arrow / 2);
  }

  @RunsInCurrentThread
  @Override
  int arrow(@Nonnull JScrollBar scrollBar) {
    return scrollBar.getWidth();
  }
}
