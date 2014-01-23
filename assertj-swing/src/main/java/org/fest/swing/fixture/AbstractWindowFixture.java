/*
 * Created on Feb 16, 2007
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
package org.fest.swing.fixture;

import static org.fest.swing.core.BasicRobot.robotWithCurrentAwtHierarchy;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.WindowDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.lock.ScreenLock;

/**
 * Supports functional testing of {@code Window}.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <C> the type of {@code Container} that this fixture can manage.
 * @param <D> the type of {@link ComponentDriver} that this fixture uses internally.
 *
 * @author Alex Ruiz
 */
public abstract class AbstractWindowFixture<S, C extends Window, D extends WindowDriver> extends
    AbstractContainerFixture<S, C, D> implements WindowLikeContainerFixture<S>, JPopupMenuInvokerFixture {
  /**
   * Creates a new {@link AbstractWindowFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param selfType the "self type."
   * @param type the type of {@code Window} to find using the created {@code Robot}.
   * @throws NullPointerException if the given {@code Window} type is {@code null}.
   * @throws ComponentLookupException if a {@code Window} having a matching type could not be found.
   * @throws ComponentLookupException if more than one {@code Window} having a matching type is found.
   * @see BasicRobot#robotWithCurrentAwtHierarchy()
   */
  public AbstractWindowFixture(@Nonnull Class<S> selfType, @Nonnull Class<? extends C> type) {
    this(selfType, robotWithCurrentAwtHierarchy(), type);
  }

  /**
   * Creates a new {@link AbstractWindowFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code Window}.
   * @param type the type of {@code Window} to find using the given {@code Robot}.
   * @throws NullPointerException if the given robot is {@code null}.
   * @throws NullPointerException if the given {@code Window} type is {@code null}.
   * @throws ComponentLookupException if a {@code Window} having a matching type could not be found.
   * @throws ComponentLookupException if more than one {@code Window} having a matching type is found.
   */
  public AbstractWindowFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull Class<? extends C> type) {
    super(selfType, robot, type);
  }

  /**
   * Creates a new {@link AbstractWindowFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param selfType the "self type."
   * @param name the name of the {@code Window} to find.
   * @param type the type of {@code Window} to find using the created {@code Robot}.
   * @throws NullPointerException if the given {@code Window} type is {@code null}.
   * @throws ComponentLookupException if a {@code Window} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code Window} having a matching name is found.
   * @see BasicRobot#robotWithCurrentAwtHierarchy()
   */
  public AbstractWindowFixture(@Nonnull Class<S> selfType, @Nullable String name, @Nonnull Class<? extends C> type) {
    this(selfType, robotWithCurrentAwtHierarchy(), name, type);
  }

  /**
   * Creates a new {@link AbstractWindowFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code Window}.
   * @param name the name of the {@code Window} to find using the given {@code Robot}.
   * @param type the type of {@code Window} to find using the given {@code Robot}.
   * @throws NullPointerException if the given robot is {@code null}.
   * @throws NullPointerException if the given {@code Window} type is {@code null}.
   * @throws ComponentLookupException if a {@code Window} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code Window} having a matching name is found.
   */
  public AbstractWindowFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String name,
      @Nonnull Class<? extends C> type) {
    super(selfType, robot, name, type);
  }

  /**
   * Creates a new {@link AbstractWindowFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param selfType the "self type."
   * @param target the {@code Window} to be managed by this fixture.
   * @throws NullPointerException if the given target {@code Window} is {@code null}.
   */
  public AbstractWindowFixture(@Nonnull Class<S> selfType, @Nonnull C target) {
    this(selfType, robotWithCurrentAwtHierarchy(), target);
  }

  /**
   * Creates a new {@link AbstractWindowFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on the given {@code Window}.
   * @param target the {@code Window} to be managed by this fixture.
   * @throws NullPointerException if the given robot is {@code null}.
   * @throws NullPointerException if the given target {@code Window} is {@code null}.
   */
  public AbstractWindowFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull C target) {
    super(selfType, robot, target);
  }

  /**
   * Simulates a user moving this fixture's {@code Window} to the given point.
   *
   * @param p the point to move this fixture's {@code Window} to.
   * @return this fixture.
   * @throws ActionFailedException if the {@code Window} is not movable.
   * @throws ActionFailedException if the given {@code Window} is not showing on the screen.
   */
  @Override
  public final @Nonnull S moveTo(@Nonnull Point p) {
    driver().moveTo(target(), p);
    return myself();
  }

  /**
   * If fixture's {@code Window} is visible, brings it to the front and may make it the focused one.
   *
   * @return this fixture.
   */
  @Override
  public final @Nonnull S moveToFront() {
    driver().moveToFront(target());
    return myself();
  }

  /**
   * If the given {@code Window} is visible, sends it to the back and may cause it to lose focus or activation if it is
   * the focused or active.
   *
   * @return this fixture.
   */
  @Override
  public final @Nonnull S moveToBack() {
    driver().moveToBack(target());
    return myself();
  }

  /**
   * Asserts that the size of this fixture's {@code Window} is equal to given one.
   *
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's {@code Window} is not equal to the given size.
   */
  @Override
  public final @Nonnull S requireSize(@Nonnull Dimension size) {
    driver().requireSize(target(), size);
    return myself();
  }


  /**
   * Simulates a user resizing vertically this fixture's {@code Window}.
   *
   * @param height the height that this fixture's {@code Window} should have after being resized.
   * @return this fixture.
   * @throws ActionFailedException if the {@code Window} is not resizable.
   */
  @Override
  public final @Nonnull S resizeHeightTo(int height) {
    driver().resizeHeightTo(target(), height);
    return myself();
  }

  /**
   * Simulates a user resizing this fixture's {@code Window}.
   *
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   * @throws ActionFailedException if the {@code Window} is not resizable.
   */
  @Override
  public final @Nonnull S resizeTo(@Nonnull Dimension size) {
    driver().resizeTo(target(), size);
    return myself();
  }

  /**
   * Simulates a user resizing horizontally this fixture's {@code Window}.
   *
   * @param width the width that this fixture's {@code Window} should have after being resized.
   * @return this fixture.
   * @throws ActionFailedException if the {@code Window} is not resizable.
   */
  @Override
  public final @Nonnull S resizeWidthTo(int width) {
    driver().resizeWidthTo(target(), width);
    return myself();
  }

  /**
   * Shows this fixture's {@code Window}.
   *
   * @return this fixture.
   */
  public final @Nonnull S show() {
    driver().show(target());
    return myself();
  }

  /**
   * Shows this fixture's {@code Window}, resized to the given size.
   *
   * @param size the size to resize this fixture's {@code Window} to.
   * @return this fixture.
   */
  public final @Nonnull S show(@Nonnull Dimension size) {
    driver().show(target(), size);
    return myself();
  }

  /**
   * Shows a pop-up menu using this fixture's {@code Window} as the invoker of the pop-up menu.
   *
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code Window} is disabled.
   * @throws IllegalStateException if this fixture's {@code Window} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public final @Nonnull JPopupMenuFixture showPopupMenu() {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target()));
  }

  /**
   * Shows a pop-up menu at the given point using this fixture's {@code Window} as the invoker of the pop-up menu.
   *
   * @param p the given point where to show the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code Window} is disabled.
   * @throws IllegalStateException if this fixture's {@code Window} is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public final @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull Point p) {
    return new JPopupMenuFixture(robot(), driver().invokePopupMenu(target(), p));
  }

  /**
   * Simulates a user closing this fixture's {@code Window}.
   */
  @Override
  public final void close() {
    driver().close(target());
  }

  /**
   * Cleans up any used resources (keyboard, mouse, open windows and {@link ScreenLock}) used by this robot.
   */
  public final void cleanUp() {
    robot().cleanUp();
  }
}
