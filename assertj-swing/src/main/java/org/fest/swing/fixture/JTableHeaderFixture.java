/*
 * Created on Mar 16, 2008
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
package org.fest.swing.fixture;

import static org.fest.util.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JPopupMenu;
import javax.swing.table.JTableHeader;

import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTableHeaderDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Supports functional testing of {@code JTableHeader}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableHeaderFixture extends
    AbstractJComponentFixture<JTableHeaderFixture, JTableHeader, JTableHeaderDriver> {
  /**
   * Creates a new {@link JTableHeaderFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JTableHeader}.
   * @param target the {@code JTableHeader} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JTableHeaderFixture(@Nonnull Robot robot, @Nonnull JTableHeader target) {
    super(JTableHeaderFixture.class, robot, target);
  }

  @Override
  protected @Nonnull JTableHeaderDriver createDriver(@Nonnull Robot robot) {
    return new JTableHeaderDriver(robot);
  }

  /**
   * Simulates a user clicking the column under the given index, in this fixture's {@code JTableHeader}.
   *
   * @param index the index of the column to click.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public @Nonnull JTableHeaderFixture clickColumn(int index) {
    driver().clickColumn(target(), index);
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given value, in this fixture's {@code JTableHeader}.
   *
   * @param columnName the column name to match. It can be a regular expression.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public @Nonnull JTableHeaderFixture clickColumn(@Nullable String columnName) {
    driver().clickColumn(target(), columnName);
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given regular expression pattern, in this fixture's
   * {@code JTableHeader}.
   *
   * @param columnNamePattern the regular expression pattern to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws NullPointerException if the given regular expression is {@code null}.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   * @since 1.2
   */
  public @Nonnull JTableHeaderFixture clickColumn(@Nonnull Pattern columnNamePattern) {
    driver().clickColumn(target(), columnNamePattern);
    return this;
  }

  /**
   * Simulates a user clicking the column under the given index, in this fixture's {@code JTableHeader}, using the given
   * mouse button, the given number of times.
   *
   * @param index the index of the column to click.
   * @param mouseClickInfo specifies the mouse button to use and the number of times to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   */
  public @Nonnull JTableHeaderFixture clickColumn(int index, @Nonnull MouseClickInfo mouseClickInfo) {
    checkNotNull(mouseClickInfo);
    driver().clickColumn(target(), index, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given one, in this fixture's {@code JTableHeader},
   * using the given mouse button, the given number of times.
   *
   * @param columnName the column name to match. It can be a regular expression.
   * @param mouseClickInfo specifies the mouse button to use and the number of times to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   */
  public @Nonnull JTableHeaderFixture clickColumn(@Nullable String columnName, @Nonnull MouseClickInfo mouseClickInfo) {
    checkNotNull(mouseClickInfo);
    driver().clickColumn(target(), columnName, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user clicking the column which name matches the given regular expression pattern, in this fixture's
   * {@code JTableHeader}, using the given mouse button, the given number of times.
   *
   * @param columnNamePattern the regular expression pattern to match.
   * @param mouseClickInfo specifies the mouse button to use and the number of times to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws LocationUnavailableException if a column with a matching name cannot be found.
   * @since 1.2
   */
  public @Nonnull JTableHeaderFixture clickColumn(@Nonnull Pattern columnNamePattern,
      @Nonnull MouseClickInfo mouseClickInfo) {
    checkNotNull(mouseClickInfo);
    driver().clickColumn(target(), columnNamePattern, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Shows a pop-up menu using this fixture's {@code JTableHeader} as the invoker of the pop-up menu.
   *
   * @param columnIndex the index of the column where the pop-up menu will be displayed.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the index is out of bounds.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(int columnIndex) {
    JPopupMenu popupMenu = driver().showPopupMenu(target(), columnIndex);
    return new JPopupMenuFixture(robot(), popupMenu);
  }

  /**
   * Shows a pop-up menu using this fixture's {@code JTableHeader} as the invoker of the pop-up menu.
   *
   * @param columnName the name of the column where the pop-up menu will be displayed. It can be a regular expression.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nullable String columnName) {
    JPopupMenu popupMenu = driver().showPopupMenu(target(), columnName);
    return new JPopupMenuFixture(robot(), popupMenu);
  }

  /**
   * Shows a pop-up menu using this fixture's {@code JTableHeader} as the invoker of the pop-up menu. The name of the
   * column to use must match the given regular expression pattern.
   *
   * @param columnNamePattern the regular expression pattern to match.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTableHeader} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   * @since 1.2
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull Pattern columnNamePattern) {
    JPopupMenu popupMenu = driver().showPopupMenu(target(), columnNamePattern);
    return new JPopupMenuFixture(robot(), popupMenu);
  }
}