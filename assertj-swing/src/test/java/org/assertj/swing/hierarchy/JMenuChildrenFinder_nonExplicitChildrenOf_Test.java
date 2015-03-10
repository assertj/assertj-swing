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
import static org.assertj.swing.test.builder.JTextFields.textField;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JMenuChildrenFinder#nonExplicitChildrenOf(Container)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JMenuChildrenFinder_nonExplicitChildrenOf_Test extends EDTSafeTestCase {
  private JMenuChildrenFinder finder;

  @Before
  public void setUp() {
    finder = new JMenuChildrenFinder();
  }

  @Test
  public void should_Return_Empty_Collection_If_Component_Is_Not_JMenu() {
    Container container = textField().createNew();
    assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
  }

  @Test
  public void should_Return_Empty_Collection_If_Component_Is_Null() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }

  @Test
  public void should_Return_JPopupMenu_If_Component_Is_JMenu() {
    ScreenLock.instance().acquire(this);
    final MyWindow window = MyWindow.createNew();
    Collection<Component> children = execute(new GuiQuery<Collection<Component>>() {
      @Override
      protected Collection<Component> executeInEDT() {
        return finder.nonExplicitChildrenOf(window.menu);
      }
    });
    try {
      assertThat(children).containsOnly(popupMenuOf(window.menu));
    } finally {
      try {
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }

  @RunsInEDT
  private static JPopupMenu popupMenuOf(final JMenu menu) {
    return execute(new GuiQuery<JPopupMenu>() {
      @Override
      protected JPopupMenu executeInEDT() {
        return menu.getPopupMenu();
      }
    });
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

    final JMenu menu = new JMenu("Menu");

    private MyWindow() {
      super(JMenuChildrenFinder_nonExplicitChildrenOf_Test.class);
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      setJMenuBar(menuBar);
    }
  }
}
