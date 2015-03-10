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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookup of {@code JComboBox}es in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_comboBox_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  public void should_Find_Visible_JComboBox_By_Name() {
    robot.showWindow(window);
    JComboBoxFixture comboBox = fixture.comboBox("selectMeComboBox");
    assertThat(comboBox.target()).isSameAs(window.comboBox);
  }

  @Test
  public void should_Fail_If_Visible_JComboBox_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myComboBox', type=javax.swing.JComboBox, requireShowing=true");
    fixture.comboBox("myComboBox");
  }

  @Test
  public void should_Find_Visible_JComboBox_By_Type() {
    robot.showWindow(window);
    JComboBoxFixture comboBox = fixture.comboBox();
    assertThat(comboBox.target()).isSameAs(window.comboBox);
  }

  @Test
  public void should_Fail_If_Visible_JComboBox_Not_Found_By_Type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JComboBox, requireShowing=true");
    fixture.comboBox();
  }

  @Test
  public void should_Find_Visible_JComboBox_By_Matcher() {
    robot.showWindow(window);
    JComboBoxFixture comboBox = fixture.comboBox(new GenericTypeMatcher<JComboBox>(JComboBox.class) {
      @Override
      protected boolean isMatching(@Nonnull JComboBox c) {
        return c.getItemCount() == 3;
      }
    });
    assertThat(comboBox.target()).isSameAs(window.comboBox);
  }

  @Test
  public void should_Fail_If_Visible_JComboBox_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class, "Unable to find component using matcher");
    fixture.comboBox(neverMatches(JComboBox.class));
  }

  private static class MyWindow extends TestWindow {
    final JComboBox comboBox = new JComboBox(array("One", "Two", "Three"));

    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      comboBox.setName("selectMeComboBox");
      addComponents(comboBox);
    }
  }
}
