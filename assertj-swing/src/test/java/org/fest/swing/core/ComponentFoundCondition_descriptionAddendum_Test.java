/*
 * Created on Feb 11, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.timing.Pause.pause;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.swing.TestWindow;
import org.fest.swing.timing.Condition;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link ComponentFoundCondition#descriptionAddendum()}.
 *
 * @author Alex Ruiz
 */
public class ComponentFoundCondition_descriptionAddendum_Test {
  private static ComponentFinder finder;
  private static MyWindow window;

  @BeforeClass
  public static void setUpOnce() {
    finder = finderWithNewAwtHierarchy();
    window = MyWindow.createNew();
  }

  @Test
  public void should_append_component_hierarchy_to_exception_message_if_component_was_not_found() {
    Condition condition = new ComponentFoundCondition("JButton to be found", finder, byType(JButton.class));
    try {
      pause(condition, 10);
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for JButton to be found")
                                .contains("Unable to find component using matcher")
                                .contains("MyWindow[name='myWindow'")
                                .contains("javax.swing.JLabel[name=null, text='Hello'");
    }
  }

  @Test
  public void should_append_found_components_to_exception_message_if_multiple_components_were_found() {
    ComponentFoundCondition condition = new ComponentFoundCondition("JLabel to be found", finder, byType(JLabel.class));
    try {
      pause(condition, 10);
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for JLabel to be found")
                                .contains("Found more than one component using matcher")
                                .contains("javax.swing.JLabel[name=null, text='Hello'")
                                .contains("javax.swing.JLabel[name=null, text='World'");
      assertThat(condition.duplicatesFound()).containsOnly(window.helloLabel, window.worldLabel);
    }
  }

  private static TypeMatcher byType(Class<? extends JComponent> type) {
    return new TypeMatcher(type);
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JLabel helloLabel = new JLabel("Hello");
    final JLabel worldLabel = new JLabel("World");

    private MyWindow() {
      super(ComponentFoundCondition.class);
      setName("myWindow");
      addComponents(helloLabel, worldLabel);
    }
  }
}
