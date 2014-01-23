/*
 * Created on Aug 29, 2008
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
package org.fest.swing.test.task;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;

/**
 * Sets the {@code JPopupMenu} for a {@code JComponent}. This task is executed in the event dispatch thread (EDT.)
 *
 * @author Alex Ruiz
 */
public final class ComponentSetPopupMenuTask {
  @RunsInEDT
  public static void setPopupMenu(final @Nonnull JComponent c, final @Nonnull JPopupMenu popupMenu) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        c.setComponentPopupMenu(popupMenu);
      }
    });
  }

  @RunsInEDT
  public static @Nonnull JPopupMenu createAndSetPopupMenu(final @Nonnull JComponent c, final String... items) {
    JPopupMenu result = execute(new GuiQuery<JPopupMenu>() {
      @Override
      protected JPopupMenu executeInEDT() {
        JPopupMenu popupMenu = new JPopupMenu();
        for (String item : items) {
          popupMenu.add(new JMenuItem(item));
        }
        c.setComponentPopupMenu(popupMenu);
        return popupMenu;
      }
    });
    return checkNotNull(result);
  }

  private ComponentSetPopupMenuTask() {}
}