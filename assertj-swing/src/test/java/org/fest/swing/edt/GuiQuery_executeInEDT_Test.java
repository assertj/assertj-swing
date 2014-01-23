/*
 * Created on Jul 29, 2008
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

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import org.junit.Test;

/**
 * Tests for {@link GuiQuery#executeInEDT()}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class GuiQuery_executeInEDT_Test {
  @Test
  public void should_execute_in_EDT_when_called_in_EDT() {
    final GuiQueryInEDT task = new GuiQueryInEDT();
    boolean executedFromEDT = execute(task);
    assertThat(executedFromEDT).isTrue();
  }

  private static class GuiQueryInEDT extends GuiQuery<Boolean> {
    GuiQueryInEDT() {
    }

    @Override
    protected Boolean executeInEDT() {
      return isEventDispatchThread();
    }
  }
}
