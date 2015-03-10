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
package org.assertj.swing.core;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.timing.Pause.pause;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.timing.Condition;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#requireNoJOptionPaneIsShowing()}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_requireNoJOptionPaneIsShowing_Test extends BasicRobot_TestCase {
  private JButton button;
  @Rule
  public ExpectedException thrown = none();

  @RunsInEDT
  @Override
  void beforeShowingWindow() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        button = new JButton("Click Me");
        button.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(window(), "A Message");
          }
        });
        window().add(button);
      }
    });
  }

  @Test
  public void should_Pass_If_No_JOptionPane_Is_Showing() {
    robot().requireNoJOptionPaneIsShowing();
  }

  @Test
  public void should_Fail_If_A_JOptionPane_Is_Showing() {
    robot().click(button);
    pauseTillJOptionPaneIsShowing();
    thrown.expectAssertionError("Expecting no JOptionPane to be showing");
    robot().requireNoJOptionPaneIsShowing();
  }

  private void pauseTillJOptionPaneIsShowing() {
    pause(new Condition("JOptionPane is showing") {
      @Override
      public boolean test() {
        Collection<Component> found = robot().finder().findAll(new TypeMatcher(JOptionPane.class));
        return !found.isEmpty();
      }
    });
  }
}
