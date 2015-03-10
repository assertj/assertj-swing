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
package org.assertj.swing.jide.components;

import com.jidesoft.status.StatusBar;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.AbstractContainerFixture;
import org.assertj.core.util.Strings;

/**
 * TODO
 * 
 * @author Peter Murray
 */
public class JideStatusBarFixture extends AbstractContainerFixture<JideStatusBarFixture, StatusBar, StatusBarDriver> {

  /**
   * Creates a new <code>{@link org.assertj.swing.jide.action.CommandBarFixture}</code>.
   * 
   * @param robot performs simulation of user events on the given {@link StatusBar}.
   * @param target the {@link StatusBar} to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JideStatusBarFixture(Robot robot, StatusBar target) {
	super(JideStatusBarFixture.class, robot, target);
  }

  /**
   * Creates a new <code>{@link org.assertj.swing.jide.action.CommandBarFixture}</code>.
   * 
   * @param robot performs simulation of user events on a {@link StatusBar}.
   * @param commandBarName the name of the {@link StatusBar} to find using the given <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws org.assertj.swing.exception.ComponentLookupException if a matching {@link StatusBar} could not be found.
   * @throws org.assertj.swing.exception.ComponentLookupException if more than one matching {@link StatusBar} is found.
   */
  public JideStatusBarFixture(Robot robot, String commandBarName) {
	super(JideStatusBarFixture.class, robot, commandBarName, StatusBar.class);
  }

  @Override
  protected StatusBarDriver createDriver(Robot robot) {
	return new StatusBarDriver(robot);
  }

  public void requireText(String text) {
	driver().requireText(target(), text);
  }

  public void containsText(String text) {
	driver().containsText(target(), text);
  }

  @Override
  public String toString() {
	return Strings.concat("name=", target().getName(),
	                      ", enabled=", target().isEnabled(),
	                      ", visible=", target().isVisible(),
	                      ", showing=", target().isShowing());
  }
}
