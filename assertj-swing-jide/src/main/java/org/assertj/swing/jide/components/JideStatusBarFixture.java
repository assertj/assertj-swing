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

package org.fest.swing.jide.components;

import com.jidesoft.status.StatusBar;
import org.fest.swing.core.Robot;
import org.fest.swing.jide.components.StatusBarDriver;
import org.fest.swing.fixture.ContainerFixture;
import org.fest.util.Strings;

/**
 * TODO
 * @author Peter Murray
 */
public class JideStatusBarFixture extends ContainerFixture<StatusBar> {

  private StatusBarDriver _driver;

  /**
   * Creates a new <code>{@link org.fest.swing.jide.action.CommandBarFixture}</code>.
   * @param robot performs simulation of user events on the given {@link StatusBar}.
   * @param target the {@link StatusBar} to be managed by this fixture.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws NullPointerException if <code>target</code> is <code>null</code>.
   */
  public JideStatusBarFixture(Robot robot, StatusBar target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link org.fest.swing.jide.action.CommandBarFixture}</code>.
   * @param robot performs simulation of user events on a {@link StatusBar}.
   * @param commandBarName the name of the {@link StatusBar} to find using the given
   * <code>Robot</code>.
   * @throws NullPointerException if <code>robot</code> is <code>null</code>.
   * @throws org.fest.swing.exception.ComponentLookupException if a matching {@link
   * StatusBar} could not be found.
   * @throws org.fest.swing.exception.ComponentLookupException if more than one matching
   * {@link StatusBar} is found.
   */
  public JideStatusBarFixture(Robot robot, String commandBarName) {
    super(robot, commandBarName, StatusBar.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new StatusBarDriver(robot));
  }

  void updateDriver(StatusBarDriver newDriver) {
    _driver = newDriver;
  }

  public void requireText(String text) {
    _driver.requireText(target, text);
  }

  public void containsText(String text) {
    _driver.containsText(target, text);
  }

  @Override
  public String toString() {
    return Strings.concat("name=", target.getName(),
                          ", enabled=", target.isEnabled(),
                          ", visible=", target.isVisible(),
                          ", showing=", target.isShowing());
  }
}
