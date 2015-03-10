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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JSpinner}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_spinner_Test extends RobotBasedTestCase {
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
  public void should_Find_Visible_JSpinner_By_Name() {
    robot.showWindow(window);
    JSpinnerFixture spinner = fixture.spinner("spinMeSpinner");
    assertThat(spinner.target()).isSameAs(window.spinner);
  }

  @Test
  public void should_Fail_If_Visible_JSpinner_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='mySpinner', type=javax.swing.JSpinner, requireShowing=true");
    fixture.spinner("mySpinner");
  }

  @Test
  public void should_Find_Visible_JSpinner_By_Type() {
    robot.showWindow(window);
    JSpinnerFixture spinner = fixture.spinner();
    assertThat(spinner.target()).isSameAs(window.spinner);
  }

  @Test
  public void should_Fail_If_Visible_JSpinner_Not_Found_By_Type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JSpinner, requireShowing=true");
    fixture.spinner();
  }

  @Test
  public void should_Find_Visible_JSpinner_By_Matcher() {
    robot.showWindow(window);
    JSpinnerFixture spinner = fixture.spinner(new GenericTypeMatcher<JSpinner>(JSpinner.class) {
      @Override
      protected boolean isMatching(@Nonnull JSpinner s) {
        return s.getValue().equals(8);
      }
    });
    assertThat(spinner.target()).isSameAs(window.spinner);
  }

  @Test
  public void should_Fail_If_Visible_JSpinner_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.spinner(neverMatches(JSpinner.class));
  }

  private static class MyWindow extends TestWindow {
    final JSpinner spinner = new JSpinner(new SpinnerNumberModel(8, 6, 10, 1));

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
      spinner.setName("spinMeSpinner");
      addComponents(spinner);
    }
  }
}
