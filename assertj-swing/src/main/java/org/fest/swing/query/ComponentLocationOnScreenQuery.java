/*
 * Created on Jul 29, 2008
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
package org.fest.swing.query;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns the location of an AWT or Swing {@code Component} on screen. This query is executed in the event dispatch
 * thread (EDT.)
 * 
 * @see Component#getLocationOnScreen()
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ComponentLocationOnScreenQuery {
  /**
   * Returns the location of the given AWT or Swing {@code Component} on screen. This query is executed in the event
   * dispatch thread (EDT.)
   * 
   * @param component the given {@code Component}.
   * @return the location of the given {@code Component} on screen.
   * @see Component#getLocationOnScreen()
   */
  @RunsInEDT
  public static @Nonnull Point locationOnScreen(final @Nonnull Component component) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected @Nullable
      Point executeInEDT() {
        return component.getLocationOnScreen();
      }
    });
    return checkNotNull(result);
  }

  private ComponentLocationOnScreenQuery() {}
}