/*
 * Created on Nov 29, 2008
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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=134">Bug 134</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug134_clickComponentOutsideScreen_Test extends RobotBasedTestCase {
  private FrameFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew();
    fixture = new FrameFixture(robot, window);
    fixture.show();
  }

  @Test
  public void should_throw_error_when_clicking_button_outside_screen() {
    moveWindowOutOfScreen();
    try {
      fixture.button().click();
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertThat(e.getMessage()).isEqualTo("The component to click is out of the boundaries of the screen");
    }
  }

  private void moveWindowOutOfScreen() {
    Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    int x = screen.width - (window.getWidth() / 3);
    int y = screen.height / 2;
    fixture.moveTo(new Point(x, y));
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

    final JTextField textField = new JTextField(20);
    final JButton button = new JButton("Click Me");

    private MyWindow() {
      super(Bug134_clickComponentOutsideScreen_Test.class);
      addComponents(textField, button);
    }
  }
}
