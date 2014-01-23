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

import static java.awt.Color.RED;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.NeverMatchingComponentMatcher.neverMatches;
import static org.fest.test.ExpectedException.none;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.JPanel;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookups of {@code JPanel}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_panel_Test extends RobotBasedTestCase {
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
  public void should_find_visible_JPanel_by_name() {
    robot.showWindow(window);
    JPanelFixture panel = fixture.panel("myPanel");
    assertThat(panel.target()).isSameAs(window.panel);
  }

  @Test
  public void should_fail_if_visible_JPanel_not_found_by_name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='somePanel', type=javax.swing.JPanel, requireShowing=true");
    fixture.panel("somePanel");
  }

  @Test
  public void should_find_visible_JPanel_by_type() {
    robot.showWindow(window);
    JPanelFixture panel = fixture.panel();
    assertThat(panel.target()).isSameAs(window.panel);
  }

  @Test
  public void should_fail_if_visible_JPanel_not_found_by_type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JPanel, requireShowing=true");
    fixture.panel();
  }

  @Test
  public void should_find_visible_JPanel_by_Matcher() {
    robot.showWindow(window);
    JPanelFixture panel = fixture.panel(new GenericTypeMatcher<JPanel>(JPanel.class) {
      @Override
      protected boolean isMatching(@Nonnull JPanel p) {
        return RED.equals(p.getBackground());
      }
    });
    assertThat(panel.target()).isSameAs(window.panel);
  }

  @Test
  public void should_fail_if_visible_JPanel_not_found_by_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.panel(neverMatches(JPanel.class));
  }

  private static class MyWindow extends TestWindow {
    final JPanel panel = new JPanel();

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
      panel.setName("myPanel");
      panel.setBackground(RED);
      setContentPane(panel);
    }
  }
}
