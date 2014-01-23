/*
 * Created on Jul 21, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JComponent}s capable of invoking {@code JPopupMenu}s.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <T> the type of {@code JComponent} that this fixture can manage.
 * @param <D> the type of {@link JComponentDriver} that this fixture uses internally.
 *
 * @author Alex Ruiz
 */
public abstract class AbstractJPopupMenuInvokerFixture<S, T extends JComponent, D extends JComponentDriver> extends
    AbstractJComponentFixture<S, T, D> implements JPopupMenuInvokerFixture {
  /**
   * Creates a new {@link AbstractJPopupMenuInvokerFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code JComponent}.
   * @param type the type of the {@code JComponent} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public AbstractJPopupMenuInvokerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull Class<? extends T> type) {
    super(selfType, robot, type);
  }

  /**
   * Creates a new {@link AbstractJPopupMenuInvokerFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code JComponent}.
   * @param name the name of the {@code JComponent} to find using the given {@code Robot}.
   * @param type the type of the {@code JComponent} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public AbstractJPopupMenuInvokerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String name,
      @Nonnull Class<? extends T> type) {
    super(selfType, robot, name, type);
  }

  /**
   * Creates a new {@link AbstractJPopupMenuInvokerFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on the given {@code JComponent}.
   * @param target the {@code JComponent} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public AbstractJPopupMenuInvokerFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull T target) {
    super(selfType, robot, target);
  }

  /**
   * Shows a pop-up menu using this fixture's {@code JComponent} as the invoker of the pop-up menu.
   *
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JComponent} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComponent} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public @Nonnull JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target()));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's {@code JComponent} as the invoker of the pop-up menu.
   *
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JComponent} is disabled.
   * @throws IllegalStateException if this fixture's {@code JComponent} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull Point p) {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target(), p));
  }
}
