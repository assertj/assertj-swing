/*
 * Created on Nov 11, 2007
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
package org.fest.swing.util;

import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_OPENED;

import java.awt.AWTEvent;
import java.awt.Window;

import javax.annotation.Nonnull;

/**
 * Utility methods related to AWT events.
 * 
 * @author Alex Ruiz
 */
public final class AWTEvents {
  /**
   * Indicates whether the id of the given event is equal to {@code WindowEvent.WINDOW_OPENED}.
   * 
   * @param e the given event.
   * @return {@code true} if the id of the given event is equal to {@code WINDOW_OPENED}; {@code false} otherwise.
   */
  public static boolean wasWindowOpened(@Nonnull AWTEvent e) {
    return e.getID() == WINDOW_OPENED;
  }

  /**
   * Indicates whether the id of the given event is equal to {@code WindowEvent.COMPONENT_SHOWN} and the source of the
   * event is a {@code Window}.
   * 
   * @param e the given event.
   * @return {@code true} if the id of the given event is equal to {@code COMPONENT_SHOWN} and the source of the event
   *         is a {@code Window}; {@code false} otherwise.
   */
  public static boolean wasWindowShown(@Nonnull AWTEvent e) {
    return e.getID() == COMPONENT_SHOWN && e.getSource() instanceof Window;
  }

  /**
   * Indicates whether the id of the given event is equal to {@code WindowEvent.WINDOW_CLOSED}.
   * 
   * @param e the given event.
   * @return {@code true} if the id of the given event is equal to {@code WINDOW_CLOSED}; {@code false} otherwise.
   */
  public static boolean wasWindowClosed(@Nonnull AWTEvent e) {
    return e.getID() == WINDOW_CLOSED;
  }

  private AWTEvents() {}
}
