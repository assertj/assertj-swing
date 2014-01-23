/*
 * Created on Mar 11, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.timing.Timeout.timeout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-303" target="_blank">FEST-303</a>.
 * 
 * @author Alex Ruiz
 */
public class FEST303_JFileChooserNotFoundOnMacOS_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
  }

  @Test
  public void should_find_JFileChooser() {
    frame.button().click();
    frame.fileChooser(timeout(5000));
  }

  private static class MyWindow extends TestWindow {
    final JButton button = new JButton("Click me");
    final JFileChooser chooser = new JFileChooser();

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    @RunsInCurrentThread
    private MyWindow() {
      super(FEST303_JFileChooserNotFoundOnMacOS_Test.class);
      add(button);
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          chooser.showOpenDialog(MyWindow.this);
        }
      });
    }
  }
}
