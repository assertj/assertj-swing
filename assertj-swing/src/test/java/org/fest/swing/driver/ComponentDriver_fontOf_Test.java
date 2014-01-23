/*
 * Created on Aug 27, 2008
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

import static java.awt.Font.PLAIN;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Font;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentDriver#fontOf(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentDriver_fontOf_Test extends RobotBasedTestCase {
  private static final Font FONT = new Font("SansSerif", PLAIN, 8);

  private MyWindow window;
  private ComponentDriver driver;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    driver = new ComponentDriver(robot);
  }

  @Test
  public void should_return_Component_font() {
    window.startRecording();
    assertThat(driver.fontOf(window)).isEqualTo(FONT);
    window.requireInvoked("getFont");
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
      super(ComponentDriver_fontOf_Test.class);
      setFont(FONT);
    }

    @Override
    public Font getFont() {
      if (recording) {
        methodInvocations.invoked("getFont");
      }
      return super.getFont();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
