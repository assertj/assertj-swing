/*
 * Created on Apr 1, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.query.ComponentParentQuery.parentOf;

import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link WindowAncestorFinder#windowAncestorOf(java.awt.Component)}.
 *
 * @author Yvonne Wang
 */
public class WindowAncestorFinder_windowAncestorOf_Test extends EDTSafeTestCase {
  private MyWindow frame;

  @Before
  public void setUp() {
    frame = MyWindow.createNew();
  }

  @After
  public void tearDown() {
    frame.destroy();
  }

  @Test
  public void should_find_Window_ancestor() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.button);
    assertThat(ancestor).isSameAs(frame);
  }

  @Test
  public void should_return_null_if_Component_is_null() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(null);
    assertThat(ancestor).isSameAs(null);
  }

  @Test
  public void should_return_Window_as_its_own_ancestor() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame);
    assertThat(ancestor).isSameAs(frame);
  }

  @Test
  public void should_return_invoker_as_ancestor_of_MenuElement() {
    Robot robot = null;
    try {
      robot = BasicRobot.robotWithCurrentAwtHierarchy();
      robot.showWindow(frame);
      robot.showPopupMenu(frame.textField);
      Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.popupMenu);
      assertThat(ancestor).isSameAs(frame);
    } finally {
      if (robot != null) {
        robot.cleanUp();
      }
    }
  }

  @Test
  public void should_return_parent_as_ancestor_if_Component_is_MenuElement_and_invoker_is_null() {
    Window ancestor = WindowAncestorFinder.windowAncestorOf(frame.popupMenu);
    assertThat(ancestor).isSameAs(parentOf(frame.popupMenu));
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

    final JButton button = new JButton("Click Me");
    final JTextField textField = new JTextField(20);
    final JPopupMenu popupMenu = new JPopupMenu();

    private MyWindow() {
      super(WindowAncestorFinder_windowAncestorOf_Test.class);
      add(button);
      add(textField);
      textField.setComponentPopupMenu(popupMenu);
      popupMenu.add(new JMenuItem("Frodo"));
    }
  }
}
