/*
 * Created on Aug 9, 2008
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
package org.fest.swing.query;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.setEnabled;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.data.BooleanProvider;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link ComponentEnabledQuery#isEnabled(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class ComponentEnabledQuery_isEnabled_Test extends RobotBasedTestCase {
  private final boolean enabled;

  private MyWindow window;

  @Parameters
  public static Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public ComponentEnabledQuery_isEnabled_Test(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  public void should_indicate_if_Component_is_enabled_or_not() {
    setEnabled(window, enabled);
    robot.waitForIdle();
    window.startRecording();
    assertThat(ComponentEnabledQuery.isEnabled(window)).isEqualTo(enabled);
    window.requireInvoked("isEnabled");
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    private MyWindow() {
      super(ComponentEnabledQuery_isEnabled_Test.class);
    }

    @Override
    public boolean isEnabled() {
      if (recording) {
        methodInvocations.invoked("isEnabled");
      }
      return super.isEnabled();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
