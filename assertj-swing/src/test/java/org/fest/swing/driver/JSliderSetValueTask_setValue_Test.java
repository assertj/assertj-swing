/*
 * Created on Aug 11, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JSliderValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.swing.JSlider;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JSliderSetValueTask#setValue(JSlider, int)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JSliderSetValueTask_setValue_Test extends RobotBasedTestCase {
  private JSlider slider;

  private final int value;

  @Parameters
  public static Collection<Object[]> values() {
    return newArrayList(new Object[][] {
        { 8 }, { 10 }, { 28 }, { 68 }, { 80 }
      });
  }

  public JSliderSetValueTask_setValue_Test(int value) {
    this.value = value;
  }

  @Override
  protected void onSetUp() {
    MyWindow window = MyWindow.createNew();
    slider = window.slider;
  }

  @Test
  public void shouldReturnValueOfJSlider() {
    JSliderSetValueTask.setValue(slider, value);
    robot.waitForIdle();
    assertThat(valueOf(slider)).isEqualTo(value);
  }

  private static class MyWindow extends TestWindow {
    final JSlider slider = new JSlider(6, 80);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JSliderSetValueTask_setValue_Test.class);
      slider.setValue(6);
      addComponents(slider);
    }
  }
}
