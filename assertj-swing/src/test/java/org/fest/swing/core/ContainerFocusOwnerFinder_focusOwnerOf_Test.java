/*
 * Created on May 31, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.awt.TestContainers.singletonContainerMock;
import static org.fest.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ContainerFocusOwnerFinder#focusOwnerOf(Container)}.
 *
 * @author Alex Ruiz
 */
public class ContainerFocusOwnerFinder_focusOwnerOf_Test extends SequentialEDTSafeTestCase {
  private MyWindow window;
  private ContainerFocusOwnerFinder finder;

  @Override
  protected void onSetUp() {
    finder = new ContainerFocusOwnerFinder();
    window = MyWindow.createNew();
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_return_null_if_Container_is_not_Window() {
    Container c = singletonContainerMock();
    assertThat(focusOwnerOf(c)).isNull();
  }

  @Test
  public void should_return_null_if_Window_is_not_showing() {
    assertThat(focusOwnerOf(window)).isNull();
  }

  @Test
  public void should_return_focus_owner_in_window() {
    window.display();
    JTextField focusOwner = window.textBox;
    giveFocusAndWaitTillIsFocused(focusOwner);
    assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
  }

  @Test
  public void should_return_focus_owner_in_owned_window_when_top_window_does_not_have_focus_owner() {
    window.display();
    MyDialog dialog = MyDialog.createAndShow(window);
    JButton focusOwner = dialog.button;
    giveFocusAndWaitTillIsFocused(focusOwner);
    assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
  }

  @Test
  public void should_return_null_if_top_window_or_owned_windows_do_not_have_focus_owner() {
    window.display();
    MyWindow window2 = MyWindow.createNew();
    window2.display();
    giveFocusAndWaitTillIsFocused(window2.textBox);
    assertThat(focusOwnerOf(window)).isNull();
  }

  @RunsInEDT
  private Component focusOwnerOf(final Container c) {
    return execute(new GuiQuery<Component>() {
      @Override
      protected Component executeInEDT() {
        return finder.focusOwnerOf(c);
      }
    });
  }

  private static class MyDialog extends TestDialog {
    final JButton button = new JButton("Click me");

    @RunsInEDT
    static MyDialog createAndShow(final Frame owner) {
      return execute(new GuiQuery<MyDialog>() {
        @Override
        protected MyDialog executeInEDT() {
          MyDialog dialog = new MyDialog(owner);
          dialog.displayInCurrentThread();
          return dialog;
        }
      });
    }

    private void displayInCurrentThread() {
      TestDialog.display(this);
    }

    private MyDialog(Frame owner) {
      super(owner);
      add(button);
    }
  }

  private static class MyWindow extends TestWindow {
    final JTextField textBox = new JTextField(20);

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(ContainerFocusOwnerFinder.class);
      addComponents(textBox);
    }
  }
}
