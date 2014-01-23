/*
 * Created on Nov 1, 2007
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
import javax.swing.JPanel;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JPanel}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JPanelFixture extends AbstractContainerFixture<JPanelFixture, JPanel, JComponentDriver> implements
    JComponentFixture<JPanelFixture>, JPopupMenuInvokerFixture {
  /**
   * Creates a new {@link JPanelFixture}.
   *
   * @param robot performs simulation of user events on a {@code JPanel}.
   * @param panelName the name of the {@code JPanel} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JPanel} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JPanel} is found.
   */
  public JPanelFixture(@Nonnull Robot robot, @Nonnull String panelName) {
    super(JPanelFixture.class, robot, panelName, JPanel.class);
  }

  /**
   * Creates a new {@link JPanelFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JPanel}.
   * @param target the {@code JPanel} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JPanelFixture(@Nonnull Robot robot, @Nonnull JPanel target) {
    super(JPanelFixture.class, robot, target);
  }

  @Override
  protected @Nonnull JComponentDriver createDriver(@Nonnull Robot robot) {
    return new JComponentDriver(robot);
  }

  /**
   * Asserts that the toolTip in this fixture's {@code JPanel} matches the given value.
   *
   * @param expected the given value. It can be a regular expression.
   * @return this fixture.
   * @throws AssertionError if the toolTip in this fixture's {@code JPanel} does not match the given value.
   * @since 1.2
   */
  @Override
  public @Nonnull JPanelFixture requireToolTip(@Nullable String expected) {
    driver().requireToolTip(target(), expected);
    return this;
  }

  /**
   * Asserts that the toolTip in this fixture's {@code JPanel} matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the toolTip in this fixture's {@code JPanel} does not match the given regular expression.
   * @since 1.2
   */
  @Override
  public @Nonnull JPanelFixture requireToolTip(@Nonnull Pattern pattern) {
    driver().requireToolTip(target(), pattern);
    return this;
  }

  /**
   * Returns the client property stored in this fixture's {@code JPanel}, under the given key.
   *
   * @param key the key to use to retrieve the client property.
   * @return the value of the client property stored under the given key, or {@code null} if the property was not found.
   * @throws NullPointerException if the given key is {@code null}.
   * @since 1.2
   */
  @Override
  public @Nullable Object clientProperty(@Nonnull Object key) {
    return driver().clientProperty(target(), key);
  }

  /**
   * Shows a pop-up menu using this fixture's {@code JPanel} as the invoker of the pop-up menu.
   *
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JPanel} is disabled.
   * @throws IllegalStateException if this fixture's {@code JPanel} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public @Nonnull JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target()));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's {@code JPanel} as the invoker of the pop-up menu.
   *
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JPanel} is disabled.
   * @throws IllegalStateException if this fixture's {@code JPanel} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull Point p) {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target(), p));
  }
}
