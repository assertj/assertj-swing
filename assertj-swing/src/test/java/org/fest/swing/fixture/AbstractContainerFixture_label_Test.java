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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.NeverMatchingComponentMatcher.neverMatches;
import static org.fest.test.ExpectedException.none;

import javax.annotation.Nonnull;
import javax.swing.JLabel;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests lookup of {@code JLabel}s in {@link AbstractContainerFixture}.
 *
 * @author Alex Ruiz
 */
public class AbstractContainerFixture_label_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private ContainerFixture fixture;
  private MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew();
    fixture = new ContainerFixture(robot, window);
  }

  @Test
  public void should_find_visible_JLabel_by_name() {
    robot.showWindow(window);
    JLabelFixture label = fixture.label("readMeLabel");
    assertThat(label.target()).isSameAs(window.label);
  }

  @Test
  public void should_fail_if_visible_JLabel_not_found_by_name() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "name='myLabel', type=javax.swing.JLabel, requireShowing=true");
    fixture.label("myLabel");
  }

  @Test
  public void should_find_visible_JLabel_by_type() {
    robot.showWindow(window);
    JLabelFixture label = fixture.label();
    assertThat(label.target()).isSameAs(window.label);
  }

  @Test
  public void should_fail_if_visible_JLabel_not_found_by_type() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher",
        "type=javax.swing.JLabel, requireShowing=true");
    fixture.label();
  }

  @Test
  public void should_find_visible_JLabel_by_Matcher() {
    robot.showWindow(window);
    JLabelFixture label = fixture.label(new GenericTypeMatcher<JLabel>(JLabel.class) {
      @Override
      protected boolean isMatching(@Nonnull JLabel l) {
        return "Read Me".equals(l.getText());
      }
    });
    assertThat(label.target()).isSameAs(window.label);
  }

  @Test
  public void should_fail_if_visible_JLabel_not_found_by_Matcher() {
    thrown.expect(ComponentLookupException.class);
    thrown.expectMessageToContain("Unable to find component using matcher");
    fixture.label(neverMatches(JLabel.class));
  }

  private static class MyWindow extends TestWindow {
    final JLabel label = new JLabel("Read Me");

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(AbstractContainerFixture_label_Test.class);
      label.setName("readMeLabel");
      addComponents(label);
    }
  }
}
