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

import static java.awt.Adjustable.VERTICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.core.NeverMatchingComponentMatcher.neverMatches;

import javax.annotation.Nonnull;
import javax.swing.JScrollBar;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JScrollBar}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_scrollBar_Test extends RobotBasedTestCase {
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
  public void should_Find_Visible_JScrollBar_By_Name() {
    robot.showWindow(window);
    JScrollBarFixture scrollBar = fixture.scrollBar("scrollMeScrollBar");
    assertThat(scrollBar.target()).isSameAs(window.scrollBar);
  }

  @Test
  public void should_Fail_If_Visible_JScrollBar_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myScrollBar', type=javax.swing.JScrollBar, requireShowing=true");
    fixture.scrollBar("myScrollBar");
  }

  @Test
  public void should_Find_Visible_JScrollBar_By_Type() {
    robot.showWindow(window);
    JScrollBarFixture scrollBar = fixture.scrollBar();
    assertThat(scrollBar.target()).isSameAs(window.scrollBar);
  }

  @Test
  public void should_Fail_If_Visible_JScrollBar_Not_Found_By_Type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JScrollBar, requireShowing=true");
    fixture.scrollBar();
  }

  @Test
  public void should_Find_Visible_JScrollBar_By_Matcher() {
    robot.showWindow(window);
    JScrollBarFixture scrollBar = fixture.scrollBar(new GenericTypeMatcher<JScrollBar>(JScrollBar.class) {
      @Override
      protected boolean isMatching(@Nonnull JScrollBar s) {
        return s.getOrientation() == VERTICAL && s.getValue() == 8;
      }
    });
    assertThat(scrollBar.target()).isSameAs(window.scrollBar);
  }

  @Test
  public void should_Fail_If_Visible_JScrollBar_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.scrollBar(neverMatches(JScrollBar.class));
  }

  private static class MyWindow extends TestWindow {
    final JScrollBar scrollBar = new JScrollBar(VERTICAL, 8, 1, 6, 10);

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
      scrollBar.setName("scrollMeScrollBar");
      addComponents(scrollBar);
    }
  }
}
