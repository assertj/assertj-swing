/*
 * Created on Dec 25, 2007
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

import java.awt.Point;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of Swing {@code Container}s.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <C> the type of {@code Component} that this fixture can manage.
 * @param <D> the type of {@link ComponentDriver} that this fixture uses internally.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class AbstractSwingContainerFixture<S, C extends JComponent, D extends JComponentDriver> extends
    AbstractContainerFixture<S, C, D> implements JComponentFixture<S>, JPopupMenuInvokerFixture {
  /**
   * Creates a new {@link AbstractSwingContainerFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code Container}.
   * @param type the type of the {@code Container} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   * @see org.fest.swing.core.ComponentFinder#findByType(Class)
   */
  public AbstractSwingContainerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull Class<? extends C> type) {
    super(selfType, robot, type);
  }

  /**
   * Creates a new {@link AbstractSwingContainerFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code Container}.
   * @param name the name of the {@code Container} to find using the given {@code Robot}.
   * @param type the type of the {@code Container} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   * @see org.fest.swing.core.ComponentFinder#findByName(String, Class)
   */
  public AbstractSwingContainerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String name,
      @Nonnull Class<? extends C> type) {
    super(selfType, robot, name, type);
  }

  /**
   * Creates a new {@link AbstractSwingContainerFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on the given {@code Container}.
   * @param target the {@code Container} to be.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public AbstractSwingContainerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull C target) {
    super(selfType, robot, target);
  }

  /**
   * Asserts that the toolTip in this fixture's {@code JScrollPane} matches the given value.
   *
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's {@code JScrollPane} does not match the given value.
   * @since 1.2
   */
  @Override
  public final @Nonnull S requireToolTip(@Nullable String expected) {
    driver().requireToolTip(target(), expected);
    return myself();
  }

  /**
   * Asserts that the toolTip in this fixture's {@code JScrollPane} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the toolTip in this fixture's {@code JScrollPane} does not match the given regular
   *           expression.
   * @since 1.2
   */
  @Override
  public final @Nonnull S requireToolTip(@Nonnull Pattern pattern) {
    driver().requireToolTip(target(), pattern);
    return myself();
  }

  /**
   * Returns the client property stored in this fixture's {@code JScrollPane}, under the given key.
   *
   * @param key the key to use to retrieve the client property.
   * @return the value of the client property stored under the given key, or {@code null} if the property was not found.
   * @throws NullPointerException if the given key is {@code null}.
   * @since 1.2
   */
  @Override
  public final @Nullable Object clientProperty(@Nonnull Object key) {
    return driver().clientProperty(target(), key);
  }

  /**
   * Shows a pop-up menu using this fixture's {@code JScrollPane} as the invoker of the pop-up menu.
   *
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JScrollPane} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollPane} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public final @Nonnull JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target()));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's {@code JScrollPane} as the invoker of the pop-up menu.
   *
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JScrollPane} is disabled.
   * @throws IllegalStateException if this fixture's {@code JScrollPane} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public final @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull Point p) {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target(), p));
  }
}
