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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.task.AbstractButtonSetArmedTask.setArmed;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.swing.JCheckBox;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.data.BooleanProvider;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link AbstractButtonArmedQuery#isArmed(javax.swing.AbstractButton)}.
 *
 * @author Christian Rösch
 */
@RunWith(Parameterized.class)
public class AbstractButtonArmedQuery_isArmed_Test extends RobotBasedTestCase {
  private JCheckBox checkBox;

  private final boolean armed;

  @Parameters
  public static @Nonnull Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public AbstractButtonArmedQuery_isArmed_Test(boolean armed) {
    this.armed = armed;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    checkBox = window.checkBox;
  }

  @Test
  public void should_Indicate_If_AbstractButton_Is_Armed() {
    setArmed(checkBox, armed);
    robot.waitForIdle();
    boolean isArmed = AbstractButtonArmedQuery.isArmed(checkBox);
    assertThat(isArmed).isEqualTo(armed);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JCheckBox checkBox = new JCheckBox("A Button");

    private MyWindow() {
      super(AbstractButtonArmedQuery_isArmed_Test.class);
      addComponents(checkBox);
    }
  }
}
