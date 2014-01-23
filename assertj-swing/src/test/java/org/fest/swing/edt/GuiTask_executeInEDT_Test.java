/*
 * Created on Aug 11, 2008
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
package org.fest.swing.edt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.timing.Pause.pause;

import org.fest.swing.timing.Condition;
import org.junit.Test;

/**
 * Tests for {@link GuiTask#executeInEDT()}.
 * 
 * @author Alex Ruiz
 */
public class GuiTask_executeInEDT_Test {
  @Test
  public void should_execute_in_EDT_when_called_in_EDT() {
    final GuiTaskInEDT task = new GuiTaskInEDT();
    execute(task);
    pause(new Condition("Task is executed") {
      @Override
      public boolean test() {
        return task.wasExecutedInEDT();
      }
    });
    assertThat(task.executed()).isEqualTo(true);
  }

  private static class GuiTaskInEDT extends GuiTask {
    private boolean executed;

    GuiTaskInEDT() {
    }

    @Override
    protected void executeInEDT() {
      executed = true;
    }

    boolean executed() {
      return executed;
    }
  }
}
