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

import com.jidesoft.pane.CollapsiblePane;
import org.fest.swing.core.Robot;
import org.fest.swing.jide.components.driver.CollapsiblePaneDriver;
import org.fest.swing.fixture.ContainerFixture;

/**
 * TODO
 * @author Peter Murray
 */
public class CollapsiblePaneFixture extends ContainerFixture<CollapsiblePane> {

  protected CollapsiblePaneDriver _driver;

  public CollapsiblePaneFixture(Robot robot, CollapsiblePane pane) {
    super(robot, pane);
    createDriver();
  }

  public CollapsiblePaneFixture(Robot robot, String name) {
    super(robot, name, CollapsiblePane.class);
    createDriver();
  }

  public CollapsiblePaneFixture expand() {
    _driver.expand(target);
    return this;
  }

  public CollapsiblePaneFixture collapse() {
    _driver.collapse(target);
    return this;
  }

  public CollapsiblePaneFixture requireExpanded() {
    _driver.requireExpanded(target);
    return this;
  }

  public CollapsiblePaneFixture requireCollapsed() {
    _driver.requireCollapsed(target);
    return this;
  }

  protected void createDriver() {
    _driver = new CollapsiblePaneDriver(robot);
  }
}
