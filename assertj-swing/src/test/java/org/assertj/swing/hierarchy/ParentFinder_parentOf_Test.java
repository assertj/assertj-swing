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
package org.assertj.swing.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.hierarchy.JFrameContentPaneQuery.contentPaneOf;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestMdiWindow;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ParentFinder#parentOf(Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ParentFinder_parentOf_Test extends SequentialEDTSafeTestCase {
  private ParentFinder finder;

  @Override
  protected final void onSetUp() {
    finder = new ParentFinder();
  }

  @Test
  public void should_Return_Parent_Of_Component() {
    final MyWindow window = MyWindow.createNew();
    try {
      Container parent = findParent(finder, window.textField);
      assertThat(parent).isSameAs(contentPaneOf(window));
    } finally {
      window.destroy();
    }
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

    final JTextField textField = new JTextField();

    private MyWindow() {
      super(ParentFinder_parentOf_Test.class);
      addComponents(textField);
    }
  }

  @Test
  public void should_Return_Parent_Of_JInternalFrame() {
    TestMdiWindow window = TestMdiWindow.createNewWindow(getClass());
    JInternalFrame internalFrame = window.internalFrame();
    try {
      assertThat(findParent(finder, internalFrame)).isNotNull().isSameAs(desktopPaneOf(internalFrame));
    } finally {
      window.destroy();
    }
  }

  @RunsInEDT
  private static Container findParent(final ParentFinder finder, final Component c) {
    return execute(new GuiQuery<Container>() {
      @Override
      protected Container executeInEDT() {
        return finder.parentOf(c);
      }
    });
  }

  @RunsInEDT
  private static JDesktopPane desktopPaneOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopPane>() {
      @Override
      protected JDesktopPane executeInEDT() {
        return internalFrame.getDesktopIcon().getDesktopPane();
      }
    });
  }
}
