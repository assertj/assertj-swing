/*
 * Created on Jul 1, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JSlider;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JSliderDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JSlider}s.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSliderFixture extends AbstractJPopupMenuInvokerFixture<JSliderFixture, JSlider, JSliderDriver> {
  /**
   * Creates a new {@link JSliderFixture}.
   * 
   * @param robot performs simulation of user events on the given {@code JSlider}.
   * @param target the {@code JSlider} to be managed {@code JSlider} by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JSliderFixture(@Nonnull Robot robot, @Nonnull JSlider target) {
    super(JSliderFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JSliderFixture}.
   * 
   * @param robot performs simulation of user events on a {@code JSlider}.
   * @param sliderName the name of the {@code JSlider} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JSlider} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JSlider} is found.
   */
  public JSliderFixture(@Nonnull Robot robot, @Nullable String sliderName) {
    super(JSliderFixture.class, robot, sliderName, JSlider.class);
  }

  @Override
  protected @Nonnull JSliderDriver createDriver(@Nonnull Robot robot) {
    return new JSliderDriver(robot);
  }

  /**
   * Simulates a user sliding this fixture's {@code JSlider} to the given value.
   * 
   * @param value the value to slide the {@code JSlider} to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSlider} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSlider} is not showing on the screen.
   * @throws IllegalArgumentException if the given position is not within the {@code JSlider} bounds.
   */
  public @Nonnull JSliderFixture slideTo(int value) {
    driver().slide(target(), value);
    return this;
  }

  /**
   * Simulates a user sliding this fixture's {@code JSlider} to its maximum value.
   * 
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSlider} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSlider} is not showing on the screen.
   */
  public @Nonnull JSliderFixture slideToMaximum() {
    driver().slideToMaximum(target());
    return this;
  }

  /**
   * Simulates a user sliding this fixture's {@code JSlider} to its minimum value.
   * 
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSlider} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSlider} is not showing on the screen.
   */
  public @Nonnull JSliderFixture slideToMinimum() {
    driver().slideToMinimum(target());
    return this;
  }
}
