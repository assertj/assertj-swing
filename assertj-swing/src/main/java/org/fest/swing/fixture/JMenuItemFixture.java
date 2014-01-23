/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenuItem;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JMenuItemDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JMenuItem}s.
 * 
 * @author Alex Ruiz
 */
public class JMenuItemFixture extends AbstractJComponentFixture<JMenuItemFixture, JMenuItem, JMenuItemDriver> {
  /**
   * Creates a new {@link JMenuItemFixture}.
   * 
   * @param robot performs simulation of user events on a {@code JMenuItem}.
   * @param menuItemName the name of the {@code JMenuItem} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JMenuItem} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JMenuItem} is found.
   */
  public JMenuItemFixture(@Nonnull Robot robot, @Nullable String menuItemName) {
    this(robot, robot.finder().findByName(menuItemName, JMenuItem.class, false));
  }

  /**
   * Creates a new {@link JMenuItemFixture}.
   * 
   * @param robot performs simulation of user events on the given {@code JMenuItem}.
   * @param target the {@code JMenuItem} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JMenuItemFixture(@Nonnull Robot robot, @Nonnull JMenuItem target) {
    super(JMenuItemFixture.class, robot, target);
  }

  @Override
  protected @Nonnull JMenuItemDriver createDriver(@Nonnull Robot robot) {
    return new JMenuItemDriver(robot);
  }
}
