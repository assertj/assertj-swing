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
package org.assertj.swing.edt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.timing.Pause.pause;

import org.assertj.swing.timing.Condition;
import org.junit.Test;

/**
 * Tests for {@link GuiTask#executeInEDT()}.
 * 
 * @author Alex Ruiz
 */
public class GuiTask_executeInEDT_Test {
  @Test
  public void should_Execute_In_EDT_When_Called_In_EDT() {
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
