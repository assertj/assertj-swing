/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.fixture;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/** Test for <a href="https://github.com/joel-costigliola/assertj-swing/issues/190">github.com - assertj-swing #190</a> */
@RunWith(Parameterized.class)
public class GitHub_190_FrameFixture_Test extends RobotBasedTestCase {

  @Parameters
  public static Collection<Object[]> data() {
    List<Object[]> list = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      list.add(new Object[] { i });
    }
    return list;
  }

  public GitHub_190_FrameFixture_Test(@SuppressWarnings("unused") int in) {
    // just used to repeat the test
  }

  @Test
  public void test() {
    setup(robot);
    FrameFixture fixture = new FrameFixture(robot, GuiActionRunner.execute(
                                                                  () -> new MainFrame()));
    fixture.show();
    fixture.textBox().enterText("Acao");
    fixture.textBox().requireText("Acao");
    fixture.button().click();
    fixture.textBox().requireText("Text: Acao");
    fixture.close();
  }

  public void setup(Robot robot) {
    robot.settings().delayBetweenEvents(100);
    robot.settings().dragDelay(0);
    robot.settings().dropDelay(0);
    robot.settings().eventPostingDelay(0);
    robot.settings().idleTimeout(0);
  }

  public static class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public JButton button;

    public JTextField text;

    public MainFrame() {
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      add(createContentPanel());
      setSize(200, 100);
      setLocationRelativeTo(null);
    }

    private JPanel createContentPanel() {
      JPanel panel = new JPanel(new FlowLayout());
      panel.add(button = createButton());
      panel.add(text = new JTextField(10));
      return panel;
    }

    private JButton createButton() {
      JButton button = new JButton("Teste");
      button.addActionListener(e -> text.setText("Text: " + text.getText()));
      return button;
    }
  }
}