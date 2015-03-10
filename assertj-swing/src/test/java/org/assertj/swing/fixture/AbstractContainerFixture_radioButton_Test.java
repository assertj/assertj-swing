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
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import javax.annotation.Nonnull;
import javax.swing.JRadioButton;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JRadioButton}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_radioButton_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  public void should_Find_Visible_JRadioButton_By_Name() {
    robot.showWindow(window);
    JRadioButtonFixture radioButton = fixture.radioButton("selectMeRadioButton");
    assertThat(radioButton.target()).isSameAs(window.radioButton);
  }

  @Test
  public void should_Fail_If_Visible_JRadioButton_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myRadioButton', type=javax.swing.JRadioButton, requireShowing=true");
    fixture.radioButton("myRadioButton");
  }

  @Test
  public void should_Find_Visible_JRadioButton_By_Type() {
    robot.showWindow(window);
    JRadioButtonFixture radioButton = fixture.radioButton();
    assertThat(radioButton.target()).isSameAs(window.radioButton);
  }

  @Test
  public void should_Fail_If_Visible_JRadioButton_Not_Found_By_Type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JRadioButton, requireShowing=true");
    fixture.radioButton();
  }

  @Test
  public void should_Find_Visible_JRadioButton_By_Matcher() {
    robot.showWindow(window);
    JRadioButtonFixture radioButton = fixture.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
      @Override
      protected boolean isMatching(@Nonnull JRadioButton r) {
        return "Select Me".equals(r.getText());
      }
    });
    assertThat(radioButton.target()).isSameAs(window.radioButton);
  }

  @Test
  public void should_Fail_If_Visible_JRadioButton_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.radioButton(neverMatches(JRadioButton.class));
  }

  private static class MyWindow extends TestWindow {
    final JRadioButton radioButton = new JRadioButton("Select Me");

    static @Nonnull MyWindow createNew(final @Nonnull Class<?> testClass) {
      MyWindow result = execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
      return checkNotNull(result);
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      radioButton.setName("selectMeRadioButton");
      addComponents(radioButton);
    }
  }
}
