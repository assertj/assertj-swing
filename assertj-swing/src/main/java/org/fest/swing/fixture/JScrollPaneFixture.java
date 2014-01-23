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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JScrollPaneDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JScrollPane}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JScrollPaneFixture extends
    AbstractJPopupMenuInvokerFixture<JScrollPaneFixture, JScrollPane, JScrollPaneDriver> {
  /**
   * Creates a new {@link JScrollPaneFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JScrollPane}.
   * @param target the {@code JScrollPane} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws IllegalArgumentException if {@code target} is {@code null}.
   */
  public JScrollPaneFixture(@Nonnull Robot robot, @Nonnull JScrollPane target) {
    super(JScrollPaneFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JScrollPaneFixture}.
   *
   * @param robot performs simulation of user events on a {@code JScrollPane}.
   * @param panelName the name of the {@code JScrollPane} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JScrollPane} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JScrollPane} is found.
   */
  public JScrollPaneFixture(@Nonnull Robot robot, @Nullable String panelName) {
    super(JScrollPaneFixture.class, robot, panelName, JScrollPane.class);
  }

  @Override
  protected @Nonnull JScrollPaneDriver createDriver(@Nonnull Robot robot) {
    return new JScrollPaneDriver(robot);
  }

  /**
   * @return a fixture managing the horizontal {@code JScrollBar} of this target's {@code JScrollPane}.
   */
  public @Nonnull JScrollBarFixture horizontalScrollBar() {
    return scrollBarFixture(driver().horizontalScrollBarIn(target()));
  }

  /**
   * @return a fixture managing the vertical {@code JScrollBar} of this target's {@code JScrollPane}.
   */
  public @Nonnull JScrollBarFixture verticalScrollBar() {
    return scrollBarFixture(driver().verticalScrollBarIn(target()));
  }

  private @Nonnull JScrollBarFixture scrollBarFixture(@Nonnull JScrollBar scrollBar) {
    return new JScrollBarFixture(robot(), scrollBar);
  }
}
