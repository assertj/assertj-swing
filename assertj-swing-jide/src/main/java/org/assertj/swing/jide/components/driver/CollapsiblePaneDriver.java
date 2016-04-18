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
package org.assertj.swing.jide.components.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;

import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.driver.JComponentDriver;

import com.jidesoft.pane.CollapsiblePane;

/**
 * TODO
 *
 * @author Peter Murray
 */
public class CollapsiblePaneDriver extends JComponentDriver {

  public CollapsiblePaneDriver(Robot robot) {
    super(robot);
  }

  public void expand(final CollapsiblePane pane) {
    if (isExpanded(pane)) {
      return;
    }
    clickExpandCollapseButton(pane);
  }

  public void collapse(final CollapsiblePane pane) {
    if (!isExpanded(pane)) {
      return;
    }
    clickExpandCollapseButton(pane);
    robot.waitForIdle();
  }

  public boolean isExpanded(final CollapsiblePane pane) {
    return execute(() -> pane.isExpanded());
  }

  public void requireExpanded(CollapsiblePane pane) {
    assertThat(isExpanded(pane)).as("Expanded Property").isTrue();
  }

  public void requireCollapsed(CollapsiblePane pane) {
    assertThat(isExpanded(pane)).as("Expanded Property").isFalse();
  }

  // @RunsInEDT
  private void clickExpandCollapseButton(CollapsiblePane pane) {
    final JButton button = robot.finder().find(pane,
                                               JButtonMatcher.withName("Collapse/Expand"));
    robot.focus(pane);
    robot.moveMouse(button);
    robot.click(button);
    // Pause.pause(500);
    robot.waitForIdle();
  }
}
