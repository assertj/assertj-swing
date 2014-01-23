/*
 * Created on Dec 21, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.security;

import static org.fest.swing.security.NoExitSecurityManagerInstaller.installNoExitSecurityManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-249" target="_blank">FEST-249</a>
 * 
 * @author Alex Ruiz
 */
public class FEST249_shouldNotAllowExit extends RobotBasedTestCase {
  private MyWindow window;
  private FrameFixture frame;
  private NoExitSecurityManagerInstaller noExitSecurityManagerInstaller;

  @Override
  protected void onSetUp() {
    noExitSecurityManagerInstaller = installNoExitSecurityManager();
    window = MyWindow.createNew();
    frame = new FrameFixture(robot, window);
    frame.show();
  }

  @Override
  protected void onTearDown() {
    noExitSecurityManagerInstaller.uninstall();
  }

  @Test
  public void should_not_exit() {
    frame.button().click();
    System.out.println("Test should reach this point");
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private final JButton button = new JButton("Exit");

    private MyWindow() {
      super(FEST249_shouldNotAllowExit.class);
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
        }
      });
      addComponents(button);
    }
  }
}
