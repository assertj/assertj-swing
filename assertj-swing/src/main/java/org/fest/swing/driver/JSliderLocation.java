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

import static javax.swing.SwingConstants.HORIZONTAL;
import static javax.swing.SwingConstants.VERTICAL;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Insets;
import java.awt.Point;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.swing.JSlider;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * A location in a {@code JSlider}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JSliderLocation {
  private static final Map<Integer, JSliderLocationStrategy> LOCATIONS = newHashMap();

  static {
    LOCATIONS.put(HORIZONTAL, new JSliderHorizontalLocation());
    LOCATIONS.put(VERTICAL, new JSliderVerticalLocation());
  }

  /**
   * <p>
   * Returns the coordinates of the given value in the given {@code JSlider}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param slider the given {@code JSlider}.
   * @param value the given value.
   * @return the coordinates of the given value in the given {@code JSlider}.
   */
  @RunsInCurrentThread
  public @Nonnull Point pointAt(@Nonnull JSlider slider, int value) {
    JSliderLocationStrategy strategy = LOCATIONS.get(slider.getOrientation());
    return strategy.locationForValue(slider, value);
  }

  private static class JSliderHorizontalLocation extends JSliderLocationStrategy {
    @Override
    @RunsInCurrentThread
    int max(@Nonnull JSlider slider, @Nonnull Insets insets) {
      return slider.getWidth() - insets.left - insets.right - 1;
    }

    @Override
    @RunsInCurrentThread
    @Nonnull Point update(@Nonnull Point center, int coordinate) {
      return new Point(coordinate, center.y);
    }
  }

  private static class JSliderVerticalLocation extends JSliderLocationStrategy {
    @RunsInCurrentThread
    @Override
    int max(@Nonnull JSlider slider, @Nonnull Insets insets) {
      return slider.getHeight() - insets.top - insets.bottom - 1;
    }

    @RunsInCurrentThread
    @Override
    @Nonnull Point update(@Nonnull Point center, int coordinate) {
      return new Point(center.x, coordinate);
    }
  }

  private static abstract class JSliderLocationStrategy {
    @RunsInCurrentThread
    final @Nonnull Point locationForValue(JSlider slider, int value) {
      Point center = new Point(slider.getWidth() / 2, slider.getHeight() / 2);
      int max = max(slider, checkNotNull(slider.getInsets()));
      int coordinate = (int) (percent(slider, value) * max);
      if (!slider.getInverted()) {
        coordinate = max - coordinate;
      }
      return update(center, coordinate);
    }

    @RunsInCurrentThread
    abstract int max(@Nonnull JSlider slider, @Nonnull Insets insets);

    @RunsInCurrentThread
    abstract @Nonnull Point update(@Nonnull Point center, int coordinate);

    @RunsInCurrentThread
    private float percent(@Nonnull JSlider slider, int value) {
      int minimum = slider.getMinimum();
      int range = slider.getMaximum() - minimum;
      return (float) (value - minimum) / range;
    }
  }
}
