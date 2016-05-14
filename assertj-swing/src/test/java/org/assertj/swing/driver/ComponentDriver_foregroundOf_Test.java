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
package org.assertj.swing.driver;

import static java.awt.Color.BLUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Color;
import java.awt.Dimension;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#foregroundOf(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_foregroundOf_Test extends RobotBasedTestCase {
  private static final Color FOREGROUND = BLUE;

  private MyWindow window;
  private ComponentDriver driver;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    driver = new ComponentDriver(robot);
  }

  @Test
  public void should_Return_Component_Foreground() {
    window.startRecording();
    assertThat(driver.foregroundOf(window)).isEqualTo(FOREGROUND);
    window.requireInvoked("getForeground");
  }

  private static class MyWindow extends TestWindow {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(ComponentDriver_foregroundOf_Test.class);
      setForeground(FOREGROUND);
      setPreferredSize(new Dimension(500, 300));
    }

    @Override
    public Color getForeground() {
      if (recording) {
        methodInvocations.invoked("getForeground");
      }
      return super.getForeground();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
