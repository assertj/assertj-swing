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

import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=303">Bug 303</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug303_JSpinnerDoesNotSelectExistingTextBeforeEnteringNewText_Test extends RobotBasedTestCase {
  private FrameFixture window;

  @Override
  protected void onSetUp() {
    window = new FrameFixture(robot, MyWindow.createNew());
    window.show();
  }

  @Test
  public void should_Select_Existing_Text_Before_Entering_New_Ext() {
    window.spinner("spinner1").focus();
    window.spinner("spinner2").enterTextAndCommit("Gandalf").requireValue("Gandalf");
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

    final JSpinner spinner1 = new JSpinner(new SpinnerListModel(array("Anakin", "Leia", "Han")));
    final JSpinner spinner2 = new JSpinner(new SpinnerListModel(array("Frodo", "Sam", "Gandalf")));

    private MyWindow() {
      super(Bug303_JSpinnerDoesNotSelectExistingTextBeforeEnteringNewText_Test.class);
      spinner1.setName("spinner1");
      spinner2.setName("spinner2");
      addComponents(spinner1, spinner2);
    }
  }
}
