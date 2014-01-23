/*
 * Created on Sep 5, 2007
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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JPopupMenuDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JPopupMenu}s
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixture extends AbstractJComponentFixture<JPopupMenuFixture, JPopupMenu, JPopupMenuDriver> {
  private final JMenuItemFinder menuItemFinder;

  /**
   * Creates a new {@link JPopupMenuFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JPopupMenu}.
   * @param target the {@code JPopupMenu} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JPopupMenuFixture(@Nonnull Robot robot, @Nonnull JPopupMenu target) {
    super(JPopupMenuFixture.class, robot, target);
    menuItemFinder = new JMenuItemFinder(robot, target);
  }

  @Override
  protected @Nonnull JPopupMenuDriver createDriver(@Nonnull Robot robot) {
    return new JPopupMenuDriver(robot);
  }

  /**
   * Finds a {@code JMenuItem}, contained in this fixture's {@code JPopupMenu}, which name matches the specified one.
   *
   * @param name the name to match.
   * @return a fixture that manages the {@code JMenuItem} found.
   * @throws ComponentLookupException if a {@code JMenuItem} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code JMenuItem} having a matching name is found.
   */
  public @Nonnull JMenuItemFixture menuItem(@Nullable String name) {
    return new JMenuItemFixture(robot(), driver().menuItem(target(), name));
  }

  /**
   * Finds a {@code JMenuItem}, contained in this fixture's {@code JPopupMenu}, that matches the specified search
   * criteria.
   *
   * @param matcher contains the search criteria for finding a {@code JMenuItem}.
   * @return a fixture that manages the {@code JMenuItem} found.
   * @throws ComponentLookupException if a {@code JMenuItem} that matches the given search criteria could not be found.
   * @throws ComponentLookupException if more than one {@code JMenuItem} that matches the given search criteria is
   *           found.
   */
  public @Nonnull JMenuItemFixture menuItem(@Nonnull GenericTypeMatcher<? extends JMenuItem> matcher) {
    return new JMenuItemFixture(robot(), driver().menuItem(target(), matcher));
  }

  /**
   * <p>
   * Finds a {@code JMenuItem} in this fixture's {@code JPopupMenu}, which path matches the given one.
   * </p>
   * <p>
   * For example, if we are looking for the menu with text "New" contained under the menu with text "File", we can
   * simply call
   * <pre>
   * JPopupMenuFixture popupMenu = tree.showPopupMenu();
   * JMenuItemFixture menuItem = popupMenu.<strong>menuItemWithPath(&quot;File&quot;, &quot;Menu&quot;)</strong>;
   * </pre>
   * </p>
   *
   * @param path the path of the menu to find.
   * @return a fixture that manages the {@code JMenuItem} found.
   * @throws ComponentLookupException if a {@code JMenuItem} under the given path could not be found.
   * @throws AssertionError if the {@code Component} found under the given path is not a {@code JMenuItem}.
   */
  public @Nonnull JMenuItemFixture menuItemWithPath(@Nonnull String... path) {
    return new JMenuItemFixture(robot(), menuItemFinder.menuItemWithPath(path));
  }

  /**
   * @return a {@code String} array representing the contents of this fixture's {@code JPopupMenu}.
   */
  public @Nonnull String[] menuLabels() {
    return driver().menuLabelsOf(target());
  }
}
