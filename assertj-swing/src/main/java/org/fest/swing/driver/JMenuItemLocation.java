/*
 * Created on Jan 31, 2008
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

import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;

/**
 * Location of a {@code JMenuItem}.
 * 
 * @author Alex Ruiz
 */
public final class JMenuItemLocation {
  private Component parentOrInvoker;
  private JPopupMenu parentPopup;

  private final boolean inMenuBar;

  /**
   * <p>
   * Creates a new {@link JMenuItemLocation}.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This constructor is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this constructor from the EDT.
   * </p>
   * 
   * @param menuItem the target {@code JMenuItem}.
   */
  @RunsInCurrentThread
  public JMenuItemLocation(@Nonnull JMenuItem menuItem) {
    parentOrInvoker = menuItem.getParent();
    if (parentOrInvoker instanceof JPopupMenu) {
      parentPopup = (JPopupMenu) parentOrInvoker;
      parentOrInvoker = checkNotNull(parentPopup.getInvoker());
    }
    inMenuBar = parentOrInvoker instanceof JMenuBar;
  }

  /**
   * Indicates whether the {@code JMenuItem} is in a {@link JMenuBar}.
   * 
   * @return {@code true} if the {@code JMenuItem} is in a {@code JMenuBar}, {@code false} otherwise.
   */
  public boolean inMenuBar() {
    return inMenuBar;
  }

  /**
   * Indicates whether the parent of the {@code JMenuItem} is another menu.
   * 
   * @return {@code true} if the parent of the {@code JMenuItem} is another menu, {@code false} otherwise.
   */
  @RunsInEDT
  public boolean isParentAMenu() {
    if (!(parentOrInvoker instanceof JMenuItem)) {
      return false;
    }
    return parentPopup == null || !isShowing(parentPopup);
  }

  /**
   * @return the parent or the invoker of the {@code JMenuItem}, or its invoker (if it is in a pop-up.)
   */
  public @Nonnull Component parentOrInvoker() {
    return parentOrInvoker;
  }

  /**
   * @return the parent pop-up menu, or {@code null} if the {@code JMenuItem} is not in a pop-up.
   */
  public @Nullable JPopupMenu parentPopup() {
    return parentPopup;
  }
}
