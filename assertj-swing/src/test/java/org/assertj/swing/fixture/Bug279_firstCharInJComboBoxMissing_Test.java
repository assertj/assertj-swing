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

import static java.awt.event.KeyEvent.VK_ENTER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.core.util.Arrays.array;

import javax.swing.JComboBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=279">Bug 279</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug279_firstCharInJComboBoxMissing_Test extends RobotBasedTestCase {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    window = new FrameFixture(robot, MyWindow.createNew());
    window.show();
  }

  @Test
  public void should_Enter_Text_In_Editable_JComboBox() {
    window.comboBox("comboBox").doubleClick().enterText("hey");
    window.pressAndReleaseKeys(VK_ENTER);
    assertThat(window.comboBox("comboBox").requireSelection("hey"));
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

    final JComboBox comboBox = new JComboBox(array("One", "Two", "Three"));

    private MyWindow() {
      super(Bug279_firstCharInJComboBoxMissing_Test.class);
      comboBox.setName("comboBox");
      comboBox.setEditable(true);
      addComponents(comboBox);
    }
  }
}
