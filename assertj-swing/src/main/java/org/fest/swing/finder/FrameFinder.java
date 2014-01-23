/*
 * Created on Jul 31, 2007
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
package org.fest.swing.finder;

import java.awt.Component;
import java.awt.Frame;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;

/**
 * Finder for {@code Frame}s. This class cannot be used directly, please see {@link WindowFinder}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameFinder extends WindowFinderTemplate<Frame> {
  /**
   * Creates a new {@link FrameFinder}.
   * 
   * @param frameName the name of the {@code Frame} to look for.
   */
  protected FrameFinder(@Nullable String frameName) {
    super(frameName, Frame.class);
  }

  /**
   * Creates a new {@link FrameFinder}.
   * 
   * @param matcher specifies the search criteria to use when looking up a {@code Frame}.
   */
  protected FrameFinder(@Nonnull GenericTypeMatcher<? extends Frame> matcher) {
    super(matcher);
  }

  /**
   * Creates a new {@link FrameFinder}.
   * 
   * @param frameType the type of {@code Frame} to look for.
   */
  protected FrameFinder(@Nonnull Class<? extends Frame> frameType) {
    super(frameType);
  }

  /**
   * Sets the timeout for this finder. The {@code Frame} to search should be found within the given time period.
   * 
   * @param timeout the number of milliseconds before stopping the search.
   * @return this finder.
   */
  @Override
  public @Nonnull FrameFinder withTimeout(@Nonnegative long timeout) {
    super.withTimeout(timeout);
    return this;
  }

  /**
   * Sets the timeout for this finder. The {@code Frame} to search should be found within the given time period.
   * 
   * @param timeout the period of time the search should be performed.
   * @param unit the time unit for {@code timeout}.
   * @return this finder.
   */
  @Override
  public @Nonnull FrameFinder withTimeout(@Nonnegative long timeout, @Nonnull TimeUnit unit) {
    super.withTimeout(timeout, unit);
    return this;
  }

  /**
   * Finds a {@code Frame} by name or type.
   * 
   * @param robot contains the underlying finding to delegate the search to.
   * @return a {@code FrameFixture} managing the found {@code Frame}.
   * @throws org.fest.swing.exception.WaitTimedOutError if a {@code Frame} could not be found.
   */
  @Override
  public @Nonnull FrameFixture using(@Nonnull Robot robot) {
    return new FrameFixture(robot, findComponentWith(robot));
  }

  /**
   * Casts the given AWT or Swing {@code Component} to {@code Frame}.
   * 
   * @return the given {@code Component}, casted to {@code Frame}.
   */
  @Override
  protected @Nullable Frame cast(@Nullable Component c) {
    return (Frame) c;
  }
}