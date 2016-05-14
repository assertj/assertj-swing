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
package org.assertj.swing.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentVisibleQuery#isVisible(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentVisibleQuery_isVisible_Test extends SequentialEDTSafeTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_Return_False_If_Component_Is_Not_Visible() {
    window.startRecording();
    assertThat(ComponentVisibleQuery.isVisible(window)).isFalse();
    window.requireInvoked("isVisible");
  }

  @Test
  public void should_Return_True_If_Component_Is_Visible() {
    window.display();
    window.startRecording();
    assertThat(ComponentVisibleQuery.isVisible(window)).isTrue();
    window.requireInvoked("isVisible");
  }

  private static class MyWindow extends TestWindow {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    private MyWindow() {
      super(ComponentVisibleQuery_isVisible_Test.class);
    }

    @Override
    public boolean isVisible() {
      if (recording) {
        methodInvocations.invoked("isVisible");
      }
      return super.isVisible();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
