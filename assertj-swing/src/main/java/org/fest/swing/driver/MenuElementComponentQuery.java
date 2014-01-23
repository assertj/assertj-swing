/*
 * Created on Aug 22, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.MenuElement;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns the AWT or Swing {@code Component} used to paint a Swing {@code MenuElement}. This query is executed in the
 * event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class MenuElementComponentQuery {
  @RunsInEDT
  static @Nonnull Component componentIn(final @Nonnull MenuElement menuElement) {
    Component result = execute(new GuiQuery<Component>() {
      @Override
      protected @Nullable Component executeInEDT() {
        return menuElement.getComponent();
      }
    });
    return checkNotNull(result);
  }

  private MenuElementComponentQuery() {}
}