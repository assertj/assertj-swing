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

import com.jidesoft.pane.CollapsiblePane;
import org.assertj.swing.jide.components.driver.CollapsiblePaneDriver;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.AbstractContainerFixture;

/**
 * TODO
 * 
 * @author Peter Murray
 */
public class CollapsiblePaneFixture extends
    AbstractContainerFixture<CollapsiblePaneFixture, CollapsiblePane, CollapsiblePaneDriver> {

  public CollapsiblePaneFixture(Robot robot, CollapsiblePane pane) {
	super(CollapsiblePaneFixture.class, robot, pane);
  }

  public CollapsiblePaneFixture(Robot robot, String name) {
	super(CollapsiblePaneFixture.class, robot, name, CollapsiblePane.class);
  }

  public CollapsiblePaneFixture expand() {
	driver().expand(target());
	return this;
  }

  public CollapsiblePaneFixture collapse() {
	driver().collapse(target());
	return this;
  }

  public CollapsiblePaneFixture requireExpanded() {
	driver().requireExpanded(target());
	return this;
  }

  public CollapsiblePaneFixture requireCollapsed() {
	driver().requireCollapsed(target());
	return this;
  }

  @Override
  protected CollapsiblePaneDriver createDriver(Robot robot) {
	return new CollapsiblePaneDriver(robot);
  }
}
