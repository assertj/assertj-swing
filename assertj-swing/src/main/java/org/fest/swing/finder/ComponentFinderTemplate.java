/*
 * Created on Oct 29, 2007
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

import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import java.awt.Component;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.ComponentFoundCondition;
import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.NameMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.AbstractComponentFixture;

/**
 * Template for AWT or Swing {@code Component} finders.
 *
 * @param <T> the type of {@code Component} this finder can search.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class ComponentFinderTemplate<T extends Component> {
  static final long TIMEOUT = 5000;

  private long timeout = TIMEOUT;

  private final ComponentMatcher matcher;
  private final String searchDescription;

  /**
   * Creates a new {@link ComponentFinderTemplate}.
   *
   * @param componentName the name of the {@code Component} to find.
   * @param componentType the type of the {@code Component} to find.
   */
  protected ComponentFinderTemplate(@Nullable String componentName, @Nonnull Class<? extends T> componentType) {
    this(new NameMatcher(componentName, componentType, true));
  }

  /**
   * Creates a new {@link ComponentFinderTemplate}.
   *
   * @param matcher specifies the search criteria to use when looking up a {@code Component}.
   */
  protected ComponentFinderTemplate(@Nonnull GenericTypeMatcher<? extends T> matcher) {
    this((ComponentMatcher) matcher);
  }

  /**
   * Creates a new {@link ComponentFinderTemplate}.
   *
   * @param componentType the type of the {@code Component} to find.
   */
  protected ComponentFinderTemplate(@Nonnull Class<? extends T> componentType) {
    this(new TypeMatcher(componentType, true));
  }

  private ComponentFinderTemplate(@Nonnull ComponentMatcher matcher) {
    this.matcher = checkNotNull(matcher);
    searchDescription = concat("component to be found using matcher ", matcher);
  }

  /**
   * Sets the timeout for this finder. The {@code Component} to find should be found within the given time period.
   *
   * @param newTimeout the period of time the search should be performed.
   * @param unit the time unit for {@code timeout}.
   * @return this finder.
   * @throws NullPointerException if the time unit is {@code null}.
   * @throws IllegalArgumentException if the timeout is a negative number.
   */
  protected ComponentFinderTemplate<T> withTimeout(@Nonnegative long newTimeout, @Nonnull TimeUnit unit) {
    checkNotNull(unit);
    return withTimeout(unit.toMillis(newTimeout));
  }

  /**
   * Sets the timeout for this finder. The {@code Component} to find should be found within the given time period.
   *
   * @param newTimeout the number of milliseconds before stopping the search.
   * @return this finder.
   * @throws IllegalArgumentException if the timeout is a negative number.
   */
  protected @Nonnull ComponentFinderTemplate<T> withTimeout(@Nonnegative long newTimeout) {
    if (newTimeout < 0) {
      throw new IllegalArgumentException("Timeout cannot be a negative number");
    }
    this.timeout = newTimeout;
    return this;
  }

  /**
   * Finds a component by name or type using the given robot.
   *
   * @param robot contains the underlying finding to delegate the search to.
   * @return a fixture capable of managing the found component.
   * @throws WaitTimedOutError if a component with the given name or of the given type could not be found.
   */
  public abstract @Nonnull AbstractComponentFixture<?, T, ?> using(@Nonnull Robot robot);

  /**
   * Finds the component using either by name or type.
   *
   * @param robot contains the underlying finding to delegate the search to.
   * @return the found component.
   * @throws WaitTimedOutError if a component with the given name or of the given type could not be found.
   */
  protected final @Nonnull T findComponentWith(@Nonnull Robot robot) {
    ComponentFoundCondition condition = new ComponentFoundCondition(searchDescription, robot.finder(), matcher);
    pause(condition, timeout);
    return checkNotNull(cast(condition.found()));
  }

  /**
   * Casts the given {@code Component} to the type supported by this finder.
   *
   * @param c the given {@code Component}.
   * @return the given {@code Component} casted to the type supported by this finder.
   */
  protected abstract @Nullable T cast(@Nullable Component c);
}
