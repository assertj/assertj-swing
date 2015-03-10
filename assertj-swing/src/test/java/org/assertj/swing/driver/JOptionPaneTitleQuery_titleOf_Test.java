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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.swing.JOptionPaneLauncher.launch;

import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneTitleQuery#titleOf(JOptionPane)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JOptionPaneTitleQuery_titleOf_Test extends RobotBasedTestCase {
  private static final String TITLE = "My Message";

  private MyOptionPane optionPane;

  @Override
  protected void onSetUp() {
    optionPane = MyOptionPane.createNew();
    launch(optionPane, TITLE);
  }

  @Test
  public void should_Return_Title_Of_JOptionPane() {
    String title = JOptionPaneTitleQuery.titleOf(optionPane);
    assertThat(title).isEqualTo(TITLE);
  }

  private static class MyOptionPane extends JOptionPane {
    @RunsInEDT
    static MyOptionPane createNew() {
      return execute(new GuiQuery<MyOptionPane>() {
        @Override
        protected MyOptionPane executeInEDT() {
          return new MyOptionPane();
        }
      });
    }

    private MyOptionPane() {
      super("Hello World", INFORMATION_MESSAGE);
    }
  }
}
