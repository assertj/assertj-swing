/*
 * Created on Feb 2, 2008
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

import static java.awt.Adjustable.HORIZONTAL;
import static java.awt.Adjustable.VERTICAL;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Point;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * A location in a {@code JScrollBar}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class JScrollBarLocation {
  // TODO Test horizontal scroll bar

  private static final int BLOCK_OFFSET = 4;

  private static final Map<Integer, JScrollBarLocationStrategy> LOCATIONS = newHashMap();

  static {
    LOCATIONS.put(HORIZONTAL, new HorizontalJScrollBarLocation());
    LOCATIONS.put(VERTICAL, new VerticalJScrollBarLocation());
  }

  /**
   * <p>
   * Returns the location where to move the mouse pointer to scroll to the given position.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @param position the position to scroll to.
   * @return the location where to move the mouse pointer to scroll to the given position.
   */
  @RunsInCurrentThread
  public @Nonnull Point thumbLocation(@Nonnull JScrollBar scrollBar, int position) {
    double fraction = (double) position / maximumMinusMinimum(scrollBar);
    return locationStrategyFor(scrollBar).thumbLocation(scrollBar, fraction);
  }

  @RunsInCurrentThread
  private int maximumMinusMinimum(@Nonnull JScrollBar scrollBar) {
    return scrollBar.getMaximum() - scrollBar.getMinimum();
  }

  /**
   * <p>
   * Returns the location where to move the mouse pointer to scroll one block up (or right.)
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @return the location where to move the mouse pointer to scroll one block up (or right.)
   */
  @RunsInCurrentThread
  public @Nonnull Point blockLocationToScrollUp(@Nonnull JScrollBar scrollBar) {
    Point p = unitLocationToScrollUp(scrollBar);
    int offset = BLOCK_OFFSET;
    return blockLocation(scrollBar, p, offset);
  }

  /**
   * <p>
   * Returns the location where to move the mouse pointer to scroll one block down (or left.)
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @return the location where to move the mouse pointer to scroll one block down (or left.)
   */
  @RunsInCurrentThread
  public @Nonnull Point blockLocationToScrollDown(@Nonnull JScrollBar scrollBar) {
    Point p = unitLocationToScrollDown(scrollBar);
    int offset = -BLOCK_OFFSET;
    return blockLocation(scrollBar, p, offset);
  }

  @RunsInCurrentThread
  private @Nonnull Point blockLocation(@Nonnull JScrollBar scrollBar, @Nonnull Point unitLocation, int offset) {
    return locationStrategyFor(scrollBar).blockLocation(scrollBar, unitLocation, offset);
  }

  /**
   * <p>
   * Returns the location where to move the mouse pointer to scroll one unit up (or right.)
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @return the location where to move the mouse pointer to scroll one unit up (or right.)
   */
  @RunsInCurrentThread
  public @Nonnull Point unitLocationToScrollUp(@Nonnull JScrollBar scrollBar) {
    int arrow = locationStrategyFor(scrollBar).arrow(scrollBar);
    return new Point(arrow / 2, arrow / 2);
  }

  /**
   * <p>
   * Returns the location where to move the mouse pointer to scroll one unit down (or left.)
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param scrollBar the target {@code JScrollBar}.
   * @return the location where to move the mouse pointer to scroll one unit down (or left.)
   */
  @RunsInCurrentThread
  public @Nonnull Point unitLocationToScrollDown(@Nonnull JScrollBar scrollBar) {
    return locationStrategyFor(scrollBar).unitLocationToScrollDown(scrollBar);
  }

  @RunsInCurrentThread
  private @Nonnull JScrollBarLocationStrategy locationStrategyFor(JScrollBar scrollBar) {
    JScrollBarLocationStrategy strategy = LOCATIONS.get(scrollBar.getOrientation());
    return checkNotNull(strategy);
  }
}
