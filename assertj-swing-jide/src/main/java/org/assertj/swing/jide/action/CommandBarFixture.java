/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.jide.action;

import org.assertj.core.util.Strings;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.AbstractContainerFixture;
import org.assertj.swing.jide.action.driver.CommandBarDriver;

import com.jidesoft.action.CommandBar;

/**
 * A AssertJ Fixture for the {@link CommandBar}.
 * 
 * @author Peter Murray
 */
public class CommandBarFixture extends AbstractContainerFixture<CommandBarFixture, CommandBar, CommandBarDriver> {

  /**
   * Creates a new <code>{@link CommandBarFixture}</code>.
   * 
   * @param robot performs simulation of user events on the given {@link CommandBar}.
   * @param target the {@link CommandBar} to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public CommandBarFixture(Robot robot, CommandBar target) {
	super(CommandBarFixture.class, robot, target);
  }

  /**
   * Creates a new <code>{@link CommandBarFixture}</code>.
   * 
   * @param robot performs simulation of user events on a {@link CommandBar}.
   * @param commandBarName the name of the {@link CommandBar} to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws org.assertj.swing.exception.ComponentLookupException if a matching {@link CommandBar} could not be found.
   * @throws org.assertj.swing.exception.ComponentLookupException if more than one matching {@link CommandBar} is found.
   */
  public CommandBarFixture(Robot robot, String commandBarName) {
	super(CommandBarFixture.class, robot, commandBarName, CommandBar.class);
  }

  @Override
  protected CommandBarDriver createDriver(Robot robot) {
	return new CommandBarDriver(robot);
  }

  @Override
  public String toString() {
	return Strings.concat("name=", target().getName(),
	                      ", enabled=", target().isEnabled(),
	                      ", visible=", target().isVisible(),
	                      ", showing=", target().isShowing());
  }
}
