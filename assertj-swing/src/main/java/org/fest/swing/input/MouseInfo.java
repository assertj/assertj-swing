/*
 * Created on Mar 28, 2008
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
package org.fest.swing.input;

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static java.awt.event.InputEvent.BUTTON2_MASK;
import static java.awt.event.InputEvent.BUTTON3_MASK;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import static java.awt.event.MouseEvent.MOUSE_EXITED;
import static java.awt.event.MouseEvent.MOUSE_PRESSED;
import static java.awt.event.MouseEvent.MOUSE_RELEASED;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.lang.ref.WeakReference;
import java.util.Stack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A description mouse-related operations.
 * 
 * @author Alex Ruiz
 */
class MouseInfo {
  static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;

  /** Current mouse position, in component coordinates. */
  private Point location = new Point(0, 0);

  /** Current mouse position, in screen coordinates. */
  private Point locationOnScreen = new Point(0, 0);

  private final Stack<WeakReference<Component>> componentStack = new Stack<WeakReference<Component>>();
  private final Stack<Point> locationStack = new Stack<Point>();
  private final Stack<Point> screenLocationStack = new Stack<Point>();

  private int buttons;
  private int modifiers;
  private int clickCount;

  void clear() {
    buttons = modifiers = clickCount = 0;
    componentStack.clear();
    locationStack.clear();
    screenLocationStack.clear();
  }

  void update(@Nonnull MouseEvent event, @Nullable Point eventScreenLocation) {
    // When a button is released, only that button appears in the modifier mask
    clickCount(event.getClickCount());
    updateOnMousePressed(event);
    updateOnMouseReleased(event);
    updateOnMouseEntered(event, eventScreenLocation);
    updateOnMouseExited(event);
    if (eventScreenLocation != null) {
      Point where = event.getPoint();
      location = componentStack.empty() ? null : new Point(where);
      locationOnScreen.setLocation(eventScreenLocation);
      locationOnScreen.translate(where.x, where.y);
    }
  }

  private void updateOnMousePressed(@Nonnull MouseEvent event) {
    if (event.getID() != MOUSE_PRESSED) {
      return;
    }
    int buttonUsed = buttonUsed(event);
    buttons |= buttonUsed;
    modifiers |= buttonUsed;
  }

  private void updateOnMouseReleased(@Nonnull MouseEvent event) {
    if (event.getID() != MOUSE_RELEASED) {
      return;
    }
    int buttonUsed = buttonUsed(event);
    buttons &= ~buttonUsed;
    modifiers &= ~buttonUsed;
  }

  private int buttonUsed(@Nonnull MouseEvent event) {
    return event.getModifiers() & BUTTON_MASK;
  }

  private void updateOnMouseEntered(@Nonnull MouseEvent event, @Nullable Point eventScreenLocation) {
    if (event.getID() != MOUSE_ENTERED) {
      return;
    }
    componentStack.push(new WeakReference<Component>(event.getComponent()));
    Point eventPoint = event.getPoint();
    locationStack.push(eventPoint);
    screenLocationStack.push(eventScreenLocation != null ? eventScreenLocation : eventPoint);
  }

  private void updateOnMouseExited(@Nonnull MouseEvent event) {
    if (event.getID() != MOUSE_EXITED || componentStack.empty()) {
      return;
    }
    componentStack.pop();
    locationStack.pop();
    screenLocationStack.pop();
  }

  public @Nullable Component component() {
    if (componentStack.empty()) {
      return null;
    }
    Component c = componentStack.peek().get();
    // Make sure we don't return a component that has gone away.
    if (c != null && isShowing(c)) {
      return c;
    }
    componentStack.pop();
    locationStack.pop();
    screenLocationStack.pop();
    c = component();
    if (c != null) {
      location = locationStack.peek();
      locationOnScreen = screenLocationStack.peek();
    }
    return c;
  }

  int buttons() {
    return buttons;
  }

  void buttons(int newButtons) {
    buttons = newButtons;
  }

  int modifiers() {
    return modifiers;
  }

  void modifiers(int newModifiers) {
    modifiers = newModifiers;
  }

  int clickCount() {
    return clickCount;
  }

  void clickCount(int newClickCount) {
    clickCount = newClickCount;
  }

  @Nullable Point location() {
    return pointFrom(location);
  }

  @Nullable Point locationOnScreen() {
    return pointFrom(locationOnScreen);
  }

  private @Nullable Point pointFrom(Point source) {
    return source != null ? new Point(source) : null;
  }
}
