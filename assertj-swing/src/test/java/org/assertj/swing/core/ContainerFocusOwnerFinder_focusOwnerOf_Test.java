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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.awt.TestContainers.singletonContainerMock;
import static org.assertj.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestDialog;
import org.assertj.swing.test.swing.TestWindow;
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
  public void should_Return_Null_If_Container_Is_Not_Window() {
    Container c = singletonContainerMock();
    assertThat(focusOwnerOf(c)).isNull();
  }

  @Test
  public void should_Return_Null_If_Window_Is_Not_Showing() {
    assertThat(focusOwnerOf(window)).isNull();
  }

  @Test
  public void should_Return_Focus_Owner_In_Window() {
    window.display();
    JTextField focusOwner = window.textBox;
    giveFocusAndWaitTillIsFocused(focusOwner);
    assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
  }

  @Test
  public void should_Return_Focus_Owner_In_Owned_Window_When_Top_Window_Does_Not_Have_Focus_Owner() {
    window.display();
    MyDialog dialog = MyDialog.createAndShow(window);
    JButton focusOwner = dialog.button;
    giveFocusAndWaitTillIsFocused(focusOwner);
    assertThat(focusOwnerOf(window)).isSameAs(focusOwner);
  }

  @Test
  public void should_Return_Null_If_Top_Window_Or_Owned_Windows_Do_Not_Have_Focus_Owner() {
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
      MyDialog dialog = execute(new GuiQuery<MyDialog>() {
        @Override
        protected MyDialog executeInEDT() {
          return new MyDialog(owner);
        }
      });
      dialog.display();
      return dialog;
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
