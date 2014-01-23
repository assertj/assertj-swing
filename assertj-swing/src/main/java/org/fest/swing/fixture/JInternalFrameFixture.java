/*
 * Created on Dec 8, 2007
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

import java.awt.Dimension;
import java.awt.Point;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JInternalFrame;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JInternalFrameDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JInternalFrame}s
 *
 * @author Alex Ruiz
 */
public class JInternalFrameFixture extends
    AbstractJPopupMenuInvokerFixture<JInternalFrameFixture, JInternalFrame, JInternalFrameDriver> implements
    FrameLikeFixture<JInternalFrameFixture> {
  /**
   * Creates a new {@link JInternalFrameFixture}.
   *
   * @param robot performs simulation of user events on a {@code JInternalFrame}.
   * @param internalFrameName the name of the {@code JInternalFrame} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JInternalFrame} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JInternalFrame} is found.
   */
  public JInternalFrameFixture(@Nonnull Robot robot, @Nullable String internalFrameName) {
    super(JInternalFrameFixture.class, robot, internalFrameName, JInternalFrame.class);
  }

  /**
   * Creates a new {@link JInternalFrameFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JInternalFrame}.
   * @param target the {@code JInternalFrame} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JInternalFrameFixture(@Nonnull Robot robot, @Nonnull JInternalFrame target) {
    super(JInternalFrameFixture.class, robot, target);
  }

  @Override
  protected @Nonnull
  JInternalFrameDriver createDriver(@Nonnull Robot robot) {
    return new JInternalFrameDriver(robot);
  }

  /**
   * Brings this fixture's {@code JInternalFrame} to the front.
   *
   * @return this fixture.
   */
  @Override
  public @Nonnull JInternalFrameFixture moveToFront() {
    driver().moveToFront(target());
    return this;
  }

  /**
   * Brings this fixture's {@code JInternalFrame} to the back.
   *
   * @return this fixture.
   */
  @Override
  public @Nonnull JInternalFrameFixture moveToBack() {
    driver().moveToBack(target());
    return this;
  }

  /**
   * Simulates a user deiconifying this fixture's {@code JInternalFrame}.
   *
   * @return this fixture.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @Override
  public @Nonnull JInternalFrameFixture deiconify() {
    driver().deiconify(target());
    return this;
  }

  /**
   * Simulates a user iconifying this fixture's {@code JInternalFrame}.
   *
   * @return this fixture.
   * @throws ActionFailedException if the given {@code JInternalFrame} is not iconifiable.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @Override
  public @Nonnull JInternalFrameFixture iconify() {
    driver().iconify(target());
    return this;
  }

  /**
   * Simulates a user maximizing this fixture's {@code JInternalFrame}, deconifying it first if it is iconified.
   *
   * @return this fixture.
   * @throws ActionFailedException if the given {@code JInternalFrame} is not maximizable.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @Override
  public @Nonnull JInternalFrameFixture maximize() {
    driver().maximize(target());
    return this;
  }

  /**
   * Simulates a user normalizing this fixture's {@code JInternalFrame}, deconifying it first if it is iconified.
   *
   * @return this fixture.
   * @throws ActionFailedException if the {@code JInternalFrame} vetoes the action.
   */
  @Override
  public @Nonnull JInternalFrameFixture normalize() {
    driver().normalize(target());
    return this;
  }

  /**
   * Simulates a user closing this fixture's {@code JInternalFrame}.
   *
   * @throws ActionFailedException if the {@code JInternalFrame} is not closable.
   */
  @Override
  public void close() {
    driver().close(target());
  }

  /**
   * Asserts that the size of this fixture's {@code JInternalFrame} is equal to given one.
   *
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's {@code JInternalFrame} is not equal to the given size.
   */
  @Override
  public @Nonnull JInternalFrameFixture requireSize(@Nonnull Dimension size) {
    driver().requireSize(target(), size);
    return this;
  }

  /**
   * Simulates a user resizing horizontally this fixture's {@code JInternalFrame}.
   *
   * @param width the width that this fixture's {@code JInternalFrame} should have after being resized.
   * @return this fixture.
   */
  @Override
  public @Nonnull JInternalFrameFixture resizeWidthTo(int width) {
    driver().resizeWidth(target(), width);
    return this;
  }

  /**
   * Simulates a user resizing vertically this fixture's {@code JInternalFrame}.
   *
   * @param height the height that this fixture's {@code JInternalFrame} should have after being resized.
   * @return this fixture.
   */
  @Override
  public @Nonnull JInternalFrameFixture resizeHeightTo(int height) {
    driver().resizeHeight(target(), height);
    return this;
  }

  /**
   * Simulates a user resizing this fixture's {@code JInternalFrame}.
   *
   * @param size the size that the target {@code JInternalFrame} should have after being resized.
   * @return this fixture.
   */
  @Override
  public @Nonnull JInternalFrameFixture resizeTo(@Nonnull Dimension size) {
    driver().resizeTo(target(), size);
    return this;
  }

  /**
   * Simulates a user moving this fixture's {@code JInternalFrame} to the given point.
   *
   * @param p the point to move this fixture's {@code JInternalFrame} to.
   * @return this fixture.
   */
  @Override
  public @Nonnull JInternalFrameFixture moveTo(@Nonnull Point p) {
    driver().move(target(), p);
    return this;
  }
}
