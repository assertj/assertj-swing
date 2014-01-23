/*
 * Created on Sep 5, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.driver.AbstractButtonTextQuery.textOf;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.join;
import static org.fest.util.Strings.quote;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.core.ComponentMatcher;

/**
 * Matches a {@code JMenuItem} given a simple label or a menu path of the format "menu|submenu|menuitem", for example
 * "File|Open|Can of worms". Adapted from Abbot's own {@code JMenuItemMatcher}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JMenuItemMatcher implements ComponentMatcher {
  private static final String SEPARATOR = "|";

  private final String label;

  /**
   * Creates a new {@link JMenuItemMatcher}.
   * 
   * @param path the path of the menu to match.
   */
  public JMenuItemMatcher(@Nonnull String... path) {
    this.label = join(path).with(SEPARATOR);
  }

  /**
   * <p>
   * Indicates whether the given component is a {@code JMenuItem} whose text matches the path specified in this matcher.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the component to verify.
   * @return {@code true} if the component matches, {@code false} otherwise.
   */
  @Override
  @RunsInCurrentThread
  public boolean matches(@Nullable Component c) {
    if (!(c instanceof JMenuItem)) {
      return false;
    }
    JMenuItem menuItem = (JMenuItem) c;
    String text = menuItem.getText();
    return areEqual(label, text) || areEqual(label, pathOf(menuItem));
  }

  @RunsInCurrentThread
  private String pathOf(@Nonnull JMenuItem menuItem) {
    Component parent = parentOrInvokerOf(menuItem);
    if (parent instanceof JMenuItem) {
      return concat(pathOf((JMenuItem) parent), SEPARATOR, textOf(menuItem));
    }
    return textOf(menuItem);
  }

  @RunsInCurrentThread
  private Component parentOrInvokerOf(@Nonnull JMenuItem menuItem) {
    Component parent = menuItem.getParent();
    if (parent instanceof JPopupMenu) {
      parent = ((JPopupMenu) parent).getInvoker();
    }
    return parent;
  }

  @Override
  public String toString() {
    return String.format("%s[label=%s]", getClass().getName(), quote(label));
  }
}