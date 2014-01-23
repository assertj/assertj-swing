/*
 * Created on Apr 28, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-104" target="_blank">FEST-104</a>
 * 
 * @author Alex Ruiz
 */
public class FEST104_JMenuItemShouldNotReceiveFocusWhenClicked_Test extends RobotBasedTestCase {
  private MyWindow window;
  private FrameFixture frameFixture;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
    frameFixture = new FrameFixture(robot, window);
    frameFixture.show();
  }

  @Test
  public void should_not_give_focus_to_JMenuItem_when_clicking_it() {
    FocusRecorder focusRecorder = FocusRecorder.attachTo(window.newMenu);
    frameFixture.menuItemWithPath("File", "New").click();
    assertThat(window.newMenu.hasFocus()).isFalse();
    assertThat(focusRecorder.focusReceived).isFalse();
  }

  private static class FocusRecorder extends FocusAdapter {
    boolean focusReceived;

    static FocusRecorder attachTo(Component c) {
      FocusRecorder r = new FocusRecorder();
      c.addFocusListener(r);
      return r;
    }

    @Override
    public void focusGained(FocusEvent e) {
      focusReceived = true;
    }
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JMenuItem newMenu = new JMenuItem("New");

    private MyWindow() {
      super(FEST104_JMenuItemShouldNotReceiveFocusWhenClicked_Test.class);
      JMenuBar menuBar = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      fileMenu.add(newMenu);
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);
      setPreferredSize(new Dimension(200, 100));
      newMenu.setFocusable(true);
    }
  }
}
