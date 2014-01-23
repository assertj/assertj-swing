/*
 * Created on Nov 1, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JButtons.button;

import java.awt.Component;

import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link WindowFilter#isIgnored(Component)}.
 * 
 * @author Alex Ruiz
 */
public class WindowFilter_isIgnored_Test extends WindowFilter_TestCase {
  @Test
  public void should_return_true_if_Component_is_ignored() {
    Component c = button().createNew();
    addToIgnoredMap(c);
    assertThat(isComponentIgnored(filter, c)).isTrue();
  }

  @Test
  public void should_return_true_if_Window_parent_is_ignored() {
    MyWindow window = MyWindow.createNew();
    Component c = window.button;
    addToIgnoredMap(window);
    assertThat(isComponentIgnored(filter, c)).isTrue();
  }

  @Test
  public void should_return_true_if_parent_of_Window_is_ignored() {
    TestWindow window = TestWindow.createNewWindow(getClass());
    TestDialog dialog = TestDialog.createNewDialog(window);
    addToIgnoredMap(window);
    assertThat(isComponentIgnored(filter, dialog)).isTrue();
  }

  @Test
  public void should_return_false_if_given_Component_is_null() {
    assertThat(isComponentIgnored(filter, null)).isFalse();
  }

  @RunsInEDT
  private static boolean isComponentIgnored(final WindowFilter filter, final Component c) {
    return execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return filter.isIgnored(c);
      }
    });
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() throws Throwable {
          return new MyWindow();
        }
      });
    }

    final JButton button = new JButton("Press Me");

    private MyWindow() {
      super(WindowFilter_TestCase.class);
      addComponents(button);
    }
  }
}
