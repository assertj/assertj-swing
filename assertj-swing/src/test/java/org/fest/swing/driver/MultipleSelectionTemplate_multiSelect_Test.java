/*
 * Created on May 8, 2008
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
import static org.fest.swing.core.TestRobots.newRobotMock;
import static org.fest.swing.util.Platform.controlOrCommandKey;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.fest.swing.core.Robot;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link MultipleSelectionTemplate#multiSelect()}.
 *
 * @author Yvonne Wang
 */
public class MultipleSelectionTemplate_multiSelect_Test {
  private Robot robot;
  private MultipleSelection template;

  @Before
  public void setUp() {
    robot = newRobotMock();
  }

  @Test
  public void should_select_once_if_element_count_is_one() {
    template = new MultipleSelection(robot, 1);
    template.multiSelect();
    assertThat(template.timesSelected).isEqualTo(1);
    verifyZeroInteractions(robot);
  }

  @Test
  public void should_select_multiple_items() {
    template = new MultipleSelection(robot, 2);
    int key = controlOrCommandKey();
    template.multiSelect();
    assertThat(template.timesSelected).isEqualTo(2);
    verify(robot).pressKey(key);
    verify(robot).releaseKey(key);
  }

  private static class MultipleSelection extends MultipleSelectionTemplate {
    private final int elementCount;

    int timesSelected;

    MultipleSelection(Robot robot, int elementCount) {
      super(robot);
      this.elementCount = elementCount;
    }

    @Override
    int elementCount() {
      return elementCount;
    }

    @Override
    void selectElement(int index) {
      timesSelected++;
    }
  }
}
