/*
 * Created on Aug 6, 2008
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

import java.awt.Dimension;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentSizeQuery#sizeOf(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentSizeQuery_sizeOf_Test extends SequentialEDTSafeTestCase {
  private static final Dimension SIZE = new Dimension(500, 300);

  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    window.display();
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_return_size_of_Component() {
    window.startRecording();
    assertThat(ComponentSizeQuery.sizeOf(window)).isEqualTo(SIZE);
    window.requireInvoked("getSize");
  }

  private static class MyWindow extends TestWindow {
    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ComponentSizeQuery_sizeOf_Test.class);
      setPreferredSize(new Dimension(SIZE));
    }

    @Override
    public Dimension getSize() {
      if (recording) {
        methodInvocations.invoked("getSize");
      }
      return super.getSize();
    }

    void startRecording() {
      recording = true;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
