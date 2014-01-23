/*
 * Created on Oct 10, 2008
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=197" target="_blank">Bug 197</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug197_clickVisibleAreaInComponent_Test extends RobotBasedTestCase {
  private FrameFixture fixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    fixture = new FrameFixture(robot, window);
    fixture.show();
  }

  @Test
  public void should_click_center_of_visible_area_of_Component_when_visible_area_is_on_the_left() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.button);
    fixture.button("clickMe").click();
    assertThat(recorder).wasClicked();
  }

  @Test
  public void should_click_center_of_visible_area_of_Component_when_visible_area_is_on_the_right() {
    ClickRecorder recorder = ClickRecorder.attachTo(window.button);
    fixture.scrollPane().horizontalScrollBar().scrollToMaximum();
    fixture.button("clickMe").click();
    assertThat(recorder).wasClicked();
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

    final JButton button = new JButton("Click Me");
    final JScrollPane scrollPane;

    private MyWindow() {
      super(Bug197_clickVisibleAreaInComponent_Test.class);
      button.setName("clickMe");
      scrollPane = new JScrollPane(button);
      scrollPane.setPreferredSize(new Dimension(20, 60));
      addComponents(scrollPane);
    }
  }
}
