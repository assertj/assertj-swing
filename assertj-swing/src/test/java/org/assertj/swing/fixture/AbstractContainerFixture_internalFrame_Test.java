/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.fixture;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.swing.JInternalFrame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

/**
 * Tests lookups of {@code JInternalFrame}s in {@link AbstractContainerFixture}.
 * 
 * @author Abraham Grief
 */
public class AbstractContainerFixture_internalFrame_Test extends RobotBasedTestCase {
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
  public void should_Find_Visible_JInternalFrame_By_Name() {
    robot.showWindow(window);
    JInternalFrameFixture internalFrame = fixture.internalFrame("testInternalFrame");
    assertThat(internalFrame.target()).isSameAs(window.internalFrame);
  }

  @Test
  public void should_Fail_If_Visible_JInternalFrame_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='testInternalFrame', type=javax.swing.JInternalFrame, requireShowing=true");
    fixture.internalFrame("testInternalFrame");
  }

  @Test
  public void should_Find_Visible_JInternalFrame_By_Type() {
    robot.showWindow(window);
    JInternalFrameFixture internalFrame = fixture.internalFrame();
    assertThat(internalFrame.target()).isSameAs(window.internalFrame);
  }

  @Test
  public void should_Fail_If_Visible_JInternalFrame_Not_Found_By_Type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JInternalFrame, requireShowing=true");
    fixture.internalFrame();
  }

  @Test
  public void should_Find_Visible_JInternalFrame_By_Matcher() {
    robot.showWindow(window);
    JInternalFrameFixture internalFrame =
      fixture.internalFrame(new GenericTypeMatcher<JInternalFrame>(JInternalFrame.class, true) {
        @Override
        protected boolean isMatching(@Nonnull JInternalFrame j) {
          return "Test Internal Frame Title".equals(j.getTitle()) && requireShowingMatches(j);
        }
      });
    assertThat(internalFrame.target()).isSameAs(window.internalFrame);
  }

  @Test
  public void should_Fail_If_Visible_JInternalFrame_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class, "Unable to find component using matcher");
    fixture.internalFrame(neverMatches(JInternalFrame.class));
  }

  private static class MyWindow extends TestWindow {
    final JInternalFrame internalFrame = new JInternalFrame("Test Internal Frame Title");

    static MyWindow createNew(final Class<?> testClass) {
      return execute(() -> new MyWindow(testClass));
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      internalFrame.setName("testInternalFrame");
      addComponents(internalFrame);
      internalFrame.show();
    }
  }
}
