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
import static org.assertj.swing.test.task.ComponentRequestFocusAndWaitForFocusGainTask.giveFocusAndWaitTillIsFocused;

import java.awt.Component;
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
 * Test case for implementations of {@link FocusOwnerFinderStrategy#focusOwner()}.
 * 
 * @author Alex Ruiz
 */
public abstract class FocusOwnerFinderStrategy_focusOwner_TestCase extends SequentialEDTSafeTestCase {
  private MyWindow window;
  private JTextField textField;
  private FocusOwnerFinderStrategy finder;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createAndShow(getClass());
    textField = window.textBox;
    finder = createStrategyToTest();
  }

  protected abstract FocusOwnerFinderStrategy createStrategyToTest();

  @Override
  public final void onTearDown() {
    window.destroy();
  }

  @Test
  public final void should_Find_Focus_Owner() {
    giveFocusAndWaitTillIsFocused(textField);
    Component focusOwner = execute(new GuiQuery<Component>() {
      @Override
      protected Component executeInEDT() {
        return finder.focusOwner();
      }
    });
    assertThat(focusOwner).isSameAs(textField);
  }

  @Test
  public final void should_Find_Focus_In_Owned_Window() {
    MyDialog dialog = MyDialog.createAndShow(window);
    giveFocusAndWaitTillIsFocused(dialog.button);
    try {
      Component focusOwner = finder.focusOwner();
      assertThat(focusOwner).isSameAs(dialog.button);
    } finally {
      dialog.destroy();
    }
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
    static MyWindow createAndShow(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return display(new MyWindow(testClass));
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(textBox);
    }
  }
}
