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

import java.awt.Window;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.AbstractWindowFixture;

/**
 * Template for {@code Window} finders.
 * 
 * @param <T> the type of {@code Window} this finder can search.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class WindowFinderTemplate<T extends Window> extends ComponentFinderTemplate<T> {
  /**
   * Creates a new {@link WindowFinderTemplate}.
   * 
   * @param windowName the name of the {@code Window} to find.
   * @param windowType the type of the {@code Window} to find.
   */
  protected WindowFinderTemplate(@Nullable String windowName, @Nonnull Class<? extends T> windowType) {
    super(windowName, windowType);
  }

  /**
   * Creates a new {@link WindowFinderTemplate}.
   * 
   * @param matcher specifies the search criteria to use when looking up a {@code Window}.
   */
  protected WindowFinderTemplate(@Nonnull GenericTypeMatcher<? extends T> matcher) {
    super(matcher);
  }

  /**
   * Creates a new {@link WindowFinderTemplate}.
   * 
   * @param windowType the type of the {@code Window} to find.
   */
  protected WindowFinderTemplate(@Nonnull Class<? extends T> windowType) {
    super(windowType);
  }

  /**
   * Sets the timeout for this finder. The {@code Window} to find should be found within the given time period.
   * 
   * @param timeout the number of milliseconds before stopping the search.
   * @return this finder.
   * @throws IllegalArgumentException if the timeout is a negative number.
   */
  @Override
  protected @Nonnull WindowFinderTemplate<T> withTimeout(@Nonnegative long timeout) {
    super.withTimeout(timeout);
    return this;
  }

  /**
   * Sets the timeout for this finder. The {@code Window} to find should be found within the given time period.
   * 
   * @param timeout the period of time the search should be performed.
   * @param unit the time unit for {@code timeout}.
   * @return this finder.
   * @throws NullPointerException if the time unit is {@code null}.
   * @throws IllegalArgumentException if the timeout is a negative number.
   */
  @Override
  protected WindowFinderTemplate<T> withTimeout(@Nonnegative long timeout, @Nonnull TimeUnit unit) {
    super.withTimeout(timeout, unit);
    return this;
  }

  /**
   * Finds a {@code Window} by name or type using the given robot.
   * 
   * @param robot contains the underlying finding to delegate the search to.
   * @return a fixture capable of managing the found {@code Window}.
   * @throws org.fest.swing.exception.WaitTimedOutError if a {@code Window} with the given name or of the given type
   *           could not be found.
   */
  @Override
  public abstract @Nonnull AbstractWindowFixture<?, T, ?> using(@Nonnull Robot robot);
}
