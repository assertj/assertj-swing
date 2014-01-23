/*
 * Created on Apr 17, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JMenuItemMatcher#matches(Component)}.
 * 
 * @author Alex Ruiz
 */
public class JMenuItemMatcher_matches_Test extends SequentialEDTSafeTestCase {
  private ComponentFinder finder;
  private MyWindow window;
  private JMenuItemMatcher matcher;

  @Override
  protected void onSetUp() {
    finder = finderWithNewAwtHierarchy();
    window = MyWindow.createNew();
    matcher = new JMenuItemMatcher("Logout", "Logout");
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_find_second_Logout_menu() {
    Component found = finder.find(window, matcher);
    assertThat(found).isSameAs(window.logoutMenuItem);
  }

  private static class MyWindow extends TestWindow {
    final JMenuItem logoutMenuItem = new JMenuItem("Logout");

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
      super(JMenuItemMatcher_matches_Test.class);
      JMenuBar menuBar = new JMenuBar();
      JMenu logoutMenu = new JMenu("Logout");
      logoutMenu.add(logoutMenuItem);
      logoutMenu.add(new JMenuItem("Exit"));
      menuBar.add(logoutMenu);
      setJMenuBar(menuBar);
      setPreferredSize(new Dimension(200, 200));
    }
  }
}
