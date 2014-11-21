/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.action;

import com.jidesoft.action.CommandBar;
import org.fest.swing.core.Robot;
import org.fest.swing.jide.action.driver.CommandBarDriver;
import org.fest.swing.fixture.ContainerFixture;
import org.fest.util.Strings;

/**
 * A FEST Fixture for the {@link CommandBar}.
 * @author Peter Murray
 */
public class CommandBarFixture extends ContainerFixture<CommandBar> {

  private CommandBarDriver _driver;

  /**
   * Creates a new <code>{@link CommandBarFixture}</code>.
   * @param robot performs simulation of user events on the given {@link CommandBar}.
   * @param target the {@link CommandBar} to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public CommandBarFixture(Robot robot, CommandBar target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link CommandBarFixture}</code>.
   * @param robot performs simulation of user events on a {@link CommandBar}.
   * @param commandBarName the name of the {@link CommandBar} to find using the given
   * <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if a matching
   * {@link CommandBar} could not be found.
   * @throws org.fest.swing.exception.ComponentLookupException if more than one matching
   * {@link CommandBar} is found.
   */
  public CommandBarFixture(Robot robot, String commandBarName) {
    super(robot, commandBarName, CommandBar.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new CommandBarDriver(robot));
  }

  void updateDriver(CommandBarDriver newDriver) {
    _driver = newDriver;
  }

  @Override
  public String toString() {
    return Strings.concat("name=", target.getName(),
                          ", enabled=", target.isEnabled(),
                          ", visible=", target.isVisible(),
                          ", showing=", target.isShowing());
  }
}
