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

import static java.awt.BorderLayout.NORTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import java.awt.BorderLayout;

import javax.annotation.Nonnull;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookup of {@code JToolBar}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_toolBar_Test extends RobotBasedTestCase {
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
  public void should_Find_Visible_JToolBar_By_Name() {
    robot.showWindow(window);
    JToolBarFixture toolBar = fixture.toolBar("myToolBar");
    assertThat(toolBar.target()).isSameAs(window.toolBar);
  }

  @Test
  public void should_Fail_If_Visible_JToolBar_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myButton', type=javax.swing.JToolBar, requireShowing=true");
    fixture.toolBar("myButton");
  }

  @Test
  public void should_Find_Visible_JToolBar_By_Type() {
    robot.showWindow(window);
    JToolBarFixture toolBar = fixture.toolBar();
    assertThat(toolBar.target()).isSameAs(window.toolBar);
  }

  @Test
  public void should_Fail_If_Visible_JToolBar_Not_Found_By_Type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JToolBar, requireShowing=true");
    fixture.toolBar();
  }

  @Test
  public void should_Find_Visible_JToolBar_By_Matcher() {
    robot.showWindow(window);
    JToolBarFixture toolBar = fixture.toolBar(new GenericTypeMatcher<JToolBar>(JToolBar.class) {
      @Override
      protected boolean isMatching(@Nonnull JToolBar t) {
        return "myToolBar".equals(t.getName());
      }
    });
    assertThat(toolBar.target()).isSameAs(window.toolBar);
  }

  @Test
  public void should_Fail_If_Visible_JToolBar_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.toolBar(neverMatches(JToolBar.class));
  }

  private static class MyWindow extends TestWindow {
    final JToolBar toolBar = new JToolBar();

    static @Nonnull MyWindow createNew(final @Nonnull Class<?> testClass) {
      return checkNotNull(execute(() -> new MyWindow(testClass)));
    }

    private MyWindow(@Nonnull Class<?> testClass) {
      super(testClass);
      toolBar.setName("myToolBar");
      setLayout(new BorderLayout());
      toolBar.add(new JLabel("Hello"));
      add(toolBar, NORTH);
    }
  }
}
