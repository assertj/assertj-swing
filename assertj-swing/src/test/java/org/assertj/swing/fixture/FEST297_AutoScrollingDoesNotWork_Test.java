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
package org.assertj.swing.fixture;

import static javax.swing.BoxLayout.Y_AXIS;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-297" target="_blank">FEST-297</a>.
 * 
 * @author Nicolae Bucalaete
 * @author Alex Ruiz
 */
public class FEST297_AutoScrollingDoesNotWork_Test extends RobotBasedTestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private FrameFixture frame;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    frame = new FrameFixture(robot, window);
    frame.show();
  }

  @Test
  public void should_Scroll_And_Click_JButton() {
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window.button);
    frame.button("button").click();
    recorder.wasClicked();
  }

  private static class MyWindow extends TestWindow {
    private final JButton button = new JButton("Click me");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    @RunsInCurrentThread
    private MyWindow() {
      super(FEST297_AutoScrollingDoesNotWork_Test.class);
      button.setName("button");
      setLayout(new BoxLayout(getContentPane(), Y_AXIS));
      add(new JScrollPane(topPanel()));
      add(panelWithSize(750, 700));
      setPreferredSize(new Dimension(200, 300));
    }

    private JPanel topPanel() {
      JPanel panel = new JPanel(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = c.gridy = 0;
      panel.add(panelWithSize(300, 600), c);
      c.gridy++;
      panel.add(button, c);
      return panel;
    }

    private static JPanel panelWithSize(int width, int height) {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(width, height));
      return panel;
    }
  }
}
