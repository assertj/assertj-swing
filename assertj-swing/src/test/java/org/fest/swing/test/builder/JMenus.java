/*
 * Created on Aug 28, 2008
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
package org.fest.swing.test.builder;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Arrays.isNullOrEmpty;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@link JMenu}s.
 *
 * @author Alex Ruiz
 */
public final class JMenus {
  private JMenus() {}

  public static JMenuFactory menu() {
    return new JMenuFactory();
  }

  public static class JMenuFactory {
    JMenuItem[] menuItems;
    String name;
    boolean selected;
    String text;

    public JMenuFactory withMenuItems(JMenuItem... newMenuItems) {
      menuItems = newMenuItems;
      return this;
    }

    public JMenuFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JMenuFactory selected(boolean isSelected) {
      selected = isSelected;
      return this;
    }

    public JMenuFactory withText(String newText) {
      text = newText;
      return this;
    }

    @RunsInEDT
    public JMenu createNew() {
      return execute(new GuiQuery<JMenu>() {
        @Override
        protected JMenu executeInEDT() {
          JMenu menu = new JMenu();
          menu.setName(name);
          menu.setSelected(selected);
          menu.setText(text);
          addMenuItemsIfAnyTo(menu);
          return menu;
        }

        private void addMenuItemsIfAnyTo(JMenu menu) {
          if (isNullOrEmpty(menuItems)) {
            return;
          }
          for (JMenuItem menuItem : menuItems) {
            menu.add(menuItem);
          }
        }
      });
    }
  }
}