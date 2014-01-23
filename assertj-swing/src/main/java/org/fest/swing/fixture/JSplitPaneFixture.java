/*
 * Created on Sep 4, 2007
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
import javax.swing.JSplitPane;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JSplitPaneDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JSplitPane}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSplitPaneFixture extends
    AbstractJPopupMenuInvokerFixture<JSplitPaneFixture, JSplitPane, JSplitPaneDriver> {
  /**
   * Creates a new {@link JSplitPaneFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JSplitPane}.
   * @param target the {@code JSplitPane} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JSplitPaneFixture(@Nonnull Robot robot, @Nonnull JSplitPane target) {
    super(JSplitPaneFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JSplitPaneFixture}.
   *
   * @param robot performs simulation of user events on a {@code JSplitPane}.
   * @param spinnerName the name of the {@code JSplitPane} to find using the given {@code Robot}.
   * @throws ComponentLookupException if a matching {@code JSplitPane} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JSplitPane} is found.
   */
  public JSplitPaneFixture(@Nonnull Robot robot, @Nullable String spinnerName) {
    super(JSplitPaneFixture.class, robot, spinnerName, JSplitPane.class);
  }

  @Override
  protected @Nonnull JSplitPaneDriver createDriver(@Nonnull Robot robot) {
    return new JSplitPaneDriver(robot);
  }

  /**
   * <p>
   * Simulates a user moving the divider of this fixture's {@code JSplitPane}.
   * </p>
   * <p>
   * Since 1.2, this method respects the minimum and maximum values of the left and right components inside this
   * fixture's {@code JSplitPane}.
   * </p>
   *
   * @param location the location to move the divider to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JSplitPane} is disabled.
   * @throws IllegalStateException if this fixture's {@code JSplitPane} is not showing on the screen.
   */
  public @Nonnull JSplitPaneFixture moveDividerTo(int location) {
    driver().moveDividerTo(target(), location);
    return this;
  }
}
