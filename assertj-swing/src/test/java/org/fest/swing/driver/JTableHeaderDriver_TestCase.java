/*
 * Created on Mar 16, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;

import java.awt.Point;

import javax.swing.table.JTableHeader;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.recorder.ClickRecorder;

/**
 * Base test case for {@link JTableHeaderDriver}.
 * 
 * @author Yvonne Wang
 */
public abstract class JTableHeaderDriver_TestCase extends JTableHeaderBasedTestCase {
  JTableHeaderDriver driver;

  @Override
  final void setUpTestTarget() {
    driver = new JTableHeaderDriver(robot);
  }

  @RunsInEDT
  final void disableTableHeader() {
    disable(tableHeader);
    robot.waitForIdle();
  }

  @RunsInEDT
  final void assertThatColumnWasClicked(ClickRecorder recorder, int columnIndex) {
    int columnAtPoint = columnAtPoint(tableHeader, recorder.pointClicked());
    assertThat(columnAtPoint).isEqualTo(columnIndex);
  }

  @RunsInEDT
  private static int columnAtPoint(final JTableHeader header, final Point point) {
    return execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        return header.getTable().columnAtPoint(point);
      }
    });
  }
}
