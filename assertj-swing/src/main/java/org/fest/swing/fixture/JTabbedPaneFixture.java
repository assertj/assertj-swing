/*
 * Created on Apr 3, 2007
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

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTabbedPane;

import org.fest.swing.core.Robot;
import org.fest.swing.data.Index;
import org.fest.swing.driver.JTabbedPaneDriver;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.LocationUnavailableException;

/**
 * Supports functional testing of {@code JTabbedPane}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneFixture extends
    AbstractJPopupMenuInvokerFixture<JTabbedPaneFixture, JTabbedPane, JTabbedPaneDriver> {
  /**
   * Creates a new {@link JTabbedPaneFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JTabbedPane}.
   * @param target the {@code JTabbedPane} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JTabbedPaneFixture(@Nonnull Robot robot, @Nonnull JTabbedPane target) {
    super(JTabbedPaneFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JTabbedPaneFixture}.
   *
   * @param robot performs simulation of user events on a {@code JTabbedPane}.
   * @param tabbedPaneName the name of the {@code JTabbedPane} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JTabbedPane} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JTabbedPane} is found.
   */
  public JTabbedPaneFixture(@Nonnull Robot robot, @Nonnull String tabbedPaneName) {
    super(JTabbedPaneFixture.class, robot, tabbedPaneName, JTabbedPane.class);
  }

  @Override
  protected @Nonnull JTabbedPaneDriver createDriver(@Nonnull Robot robot) {
    return new JTabbedPaneDriver(robot);
  }

  /**
   * Returns the titles of all the tabs in this fixture's {@code JTabbedPane}.
   *
   * @return the titles of all the tabs.
   */
  public String[] tabTitles() {
    return driver().tabTitles(target());
  }

  /**
   * Simulates a user selecting the tab located at the given index.
   *
   * @param index the index of the tab to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTabbedPane} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTabbedPane} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
   */
  public @Nonnull JTabbedPaneFixture selectTab(int index) {
    driver().selectTab(target(), index);
    return this;
  }

  /**
   * Simulates a user selecting the tab whose title matches the given value.
   *
   * @param title the title to match. It can be a regular expression.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTabbedPane} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTabbedPane} is not showing on the screen.
   * @throws LocationUnavailableException if a tab matching the given title could not be found.
   */
  public @Nonnull JTabbedPaneFixture selectTab(@Nullable String title) {
    driver().selectTab(target(), title);
    return this;
  }

  /**
   * Simulates a user selecting the tab whose title matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTabbedPane} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTabbedPane} is not showing on the screen.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws LocationUnavailableException if a tab matching the given regular expression pattern could not be found.
   * @since 1.2
   */
  public @Nonnull JTabbedPaneFixture selectTab(@Nonnull Pattern pattern) {
    driver().selectTab(target(), pattern);
    return this;
  }

  /**
   * Asserts that the title of the tab at the given index matches the given value.
   *
   * @param title the expected title. It can be a regular expression.
   * @param index the index of the tab.
   * @return this fixture.
   * @throws IndexOutOfBoundsException if the given index is not within the {@code JTabbedPane} bounds.
   * @throws AssertionError if the title of the tab at the given index does not match the given one.
   */
  public @Nonnull JTabbedPaneFixture requireTitle(@Nullable String title, @Nonnull Index index) {
    driver().requireTabTitle(target(), title, index);
    return this;
  }

  /**
   * Asserts that the title of the tab at the given index matches the given regular expression pattern.
   *
   * @param pattern the regular expression pattern to match.
   * @param index the index of the tab.
   * @return this fixture.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the title of the tab at the given index does not match the given regular expression
   *           pattern.
   */
  public @Nonnull JTabbedPaneFixture requireTitle(@Nonnull Pattern pattern, @Nonnull Index index) {
    driver().requireTabTitle(target(), pattern, index);
    return this;
  }

  /**
   * Asserts that the tabs of this fixture's {@code JTabbedPane} have the given titles. The tab titles are evaluated by
   * index order, for example, the first tab is expected to have the first title in the given array, and so on.
   *
   * @param titles the expected titles.
   * @return this fixture.
   * @throws AssertionError if the title of any of the tabs is not equal to the expected titles.
   */
  public @Nonnull JTabbedPaneFixture requireTabTitles(@Nonnull String... titles) {
    driver().requireTabTitles(target(), titles);
    return this;
  }
}
