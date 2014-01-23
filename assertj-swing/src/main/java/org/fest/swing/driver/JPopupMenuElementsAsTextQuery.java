/*
 * Created on Nov 18, 2008
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

import static org.fest.swing.driver.MenuElementComponentQuery.componentIn;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns the contents of a {@code JPopupMenu} as a {@code String} array. This query is executed in the event dispatch
 * thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JPopupMenuElementsAsTextQuery {
  @RunsInEDT
  static @Nonnull String[] menuElementsAsText(final @Nonnull JPopupMenu popupMenu) {
    String[] result = execute(new GuiQuery<String[]>() {
      @Override
      protected String[] executeInEDT() throws Throwable {
        MenuElement[] subElements = popupMenu.getSubElements();
        String[] result = new String[subElements.length];
        for (int i = 0; i < subElements.length; i++) {
          MenuElement subElement = checkNotNull(subElements[i]);
          result[i] = textOf(subElement);
        }
        return result;
      }
    });
    return checkNotNull(result);
  }

  private static @Nullable String textOf(@Nonnull MenuElement e) {
    Component c = componentIn(e);
    if (c instanceof JMenuItem) {
      return ((JMenuItem) c).getText();
    }
    return "-";
  }

  private JPopupMenuElementsAsTextQuery() {}
}
