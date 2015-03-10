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

import java.awt.Dimension;

import javax.annotation.Nonnull;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookup of {@code JMenuItem}s in {@link AbstractContainerFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_menuItem_Test extends RobotBasedTestCase {
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
  public void should_Find_Visible_JMenuItem_By_Path() {
    robot.showWindow(window);
    JMenuItemFixture menuItem = fixture.menuItemWithPath("File", "New");
    assertThat(menuItem.target()).isSameAs(window.menuNew);
  }

  @Test
  public void should_Fail_If_Visible_JMenuItem_Not_Found_By_Path() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher", "label='Edit'");
    fixture.menuItemWithPath("Edit");
  }

  @Test
  public void should_Find_Visible_JMenuItem_By_Name() {
    robot.showWindow(window);
    JMenuItemFixture menuItem = fixture.menuItem("menuNew");
    assertThat(menuItem.target()).isSameAs(window.menuNew);
  }

  @Test
  public void should_Fail_If_Visible_JMenuItem_Not_Found_By_Name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myMenuNew', type=javax.swing.JMenuItem, requireShowing=false");
    fixture.menuItem("myMenuNew");
  }

  @Test
  public void should_Find_Visible_JMenuItem_By_Matcher() {
    robot.showWindow(window);
    JMenuItemFixture menuItem = fixture.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
      @Override
      protected boolean isMatching(@Nonnull JMenuItem m) {
        return "New".equals(m.getText());
      }
    });
    assertThat(menuItem.target()).isSameAs(window.menuNew);
  }

  @Test
  public void should_Fail_If_Visible_JMenuItem_Not_Found_By_Matcher() {
    thrown.expect(ComponentLookupException.class, "Unable to find component using matcher");
    fixture.menuItem(neverMatches(JMenuItem.class));
  }

  private static class MyWindow extends TestWindow {
    final JMenu menuFile = new JMenu("File");
    final JMenuItem menuNew = new JMenuItem("New");

    @RunsInEDT
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
      setJMenuBar(new JMenuBar());
      menuFile.add(menuNew);
      menuNew.setName("menuNew");
      getJMenuBar().add(menuFile);
      setPreferredSize(new Dimension(80, 60));
    }
  }
}
