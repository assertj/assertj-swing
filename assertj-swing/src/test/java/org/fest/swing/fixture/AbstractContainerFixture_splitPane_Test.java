/*
 * Created on Jun 7, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.NeverMatchingComponentMatcher.neverMatches;
import static org.fest.test.ExpectedException.none;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Dimension;

import javax.annotation.Nonnull;
import javax.swing.JList;
import javax.swing.JSplitPane;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JSplitPane}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_splitPane_Test extends RobotBasedTestCase {
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
  public void should_find_visible_JButton_by_name() {
    robot.showWindow(window);
    JSplitPaneFixture splitPane = fixture.splitPane("slideMeSplitPane");
    assertThat(splitPane.target()).isSameAs(window.splitPane);
  }

  @Test
  public void should_fail_if_visible_JButton_not_found_by_name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='mySplitPane', type=javax.swing.JSplitPane, requireShowing=true");
    fixture.splitPane("mySplitPane");
  }

  @Test
  public void should_find_visible_JButton_by_type() {
    robot.showWindow(window);
    JSplitPaneFixture splitPane = fixture.splitPane();
    assertThat(splitPane.target()).isSameAs(window.splitPane);
  }

  @Test
  public void should_fail_if_visible_JButton_not_found_by_type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JSplitPane, requireShowing=true");
    fixture.splitPane();
  }

  @Test
  public void should_find_visible_JButton_by_Matcher() {
    robot.showWindow(window);
    JSplitPaneFixture splitPane = fixture.splitPane(new GenericTypeMatcher<JSplitPane>(JSplitPane.class) {
      @Override
      protected boolean isMatching(@Nonnull JSplitPane s) {
        return s.getLeftComponent() instanceof JList;
      }
    });
    assertThat(splitPane.target()).isSameAs(window.splitPane);
  }

  @Test
  public void should_fail_if_visible_JButton_not_found_by_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.splitPane(neverMatches(JSplitPane.class));
  }

  private static class MyWindow extends TestWindow {
    final JSplitPane splitPane = new JSplitPane(VERTICAL_SPLIT);

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
      splitPane.setName("slideMeSplitPane");
      splitPane.setLeftComponent(list("One", "Two"));
      splitPane.setRightComponent(list("Three"));
      addComponents(splitPane);
    }

    private static JList list(@Nonnull Object... elements) {
      JList list = new JList(elements);
      list.setPreferredSize(new Dimension(100, 50));
      return list;
    }
  }
}
