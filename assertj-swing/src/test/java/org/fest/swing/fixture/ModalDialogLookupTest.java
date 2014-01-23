/*
 * Created on Jun 3, 2008
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
import static org.fest.swing.finder.WindowFinder.findDialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests lookup of a modal dialog. This test tries to reproduce the problem reported at <a
 * href="http://groups.google.com/group/easytesting/browse_thread/thread/c42bd103c28d6a1a" target="_blank">this mailing
 * list message</a>.
 *
 * @author Alex Ruiz
 */
public class ModalDialogLookupTest extends RobotBasedTestCase {
  private MyWindow frame;

  @Override
  protected void onSetUp() {
    frame = MyWindow.createNew();
    robot.showWindow(frame);
  }

  @Test
  public void should_show_modal_Dialog_and_not_block() {
    FrameFixture frameFixture = new FrameFixture(robot, frame);
    frameFixture.button("launch").click();
    DialogFixture dialogFixture = findDialog(TestDialog.class).using(robot);
    assertThat(dialogFixture.target()).isSameAs(frame.dialog);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JButton button = new JButton("Launch");
    final TestDialog dialog = TestDialog.createNewDialog(this);

    private MyWindow() {
      super(ModalDialogLookupTest.class);
      button.setName("launch");
      add(button);
      dialog.setModal(true);
      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          dialog.setVisible(true);
        }
      });
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
