/*
 * Created on Feb 8, 2007
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

import java.awt.Frame;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.FrameDriver;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code Frame}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FrameFixture extends AbstractWindowFixture<FrameFixture, Frame, FrameDriver> implements
    FrameLikeFixture<FrameFixture> {
  /**
   * Creates a new {@link FrameFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param target the {@code Frame} to be managed by this fixture.
   * @throws NullPointerException if the given frame is {@code null}.
   * @see BasicRobot#robotWithCurrentAwtHierarchy()
   */
  public FrameFixture(@Nonnull Frame target) {
    super(FrameFixture.class, target);
  }

  /**
   * Creates a new {@link FrameFixture}.
   *
   * @param robot performs user events on the given window and verifies expected output.
   * @param target the {@code Frame} to be managed by this fixture.
   * @throws NullPointerException if the given robot is {@code null}.
   * @throws NullPointerException if the given frame is {@code null}.
   */
  public FrameFixture(@Nonnull Robot robot, @Nonnull Frame target) {
    super(FrameFixture.class, robot, target);
  }

  /**
   * Creates a new {@link FrameFixture}.
   *
   * @param robot performs user events on the given window and verifies expected output.
   * @param name the name of the {@code Frame} to find using the given {@code Robot}.
   * @throws NullPointerException if the given robot is {@code null}.
   * @throws ComponentLookupException if a {@code Frame} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code Frame} having a matching name is found.
   */
  public FrameFixture(@Nonnull Robot robot, @Nullable String name) {
    super(FrameFixture.class, robot, name, Frame.class);
  }

  /**
   * Creates a new {@link FrameFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param name the name of the {@code Frame} to find.
   * @throws ComponentLookupException if a {@code Frame} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code Frame} having a matching name is found.
   */
  public FrameFixture(@Nullable String name) {
    super(FrameFixture.class, name, Frame.class);
  }

  @Override
  protected @Nonnull FrameDriver createDriver(@Nonnull Robot robot) {
    return new FrameDriver(robot);
  }

  /**
   * Simulates a user iconifying this fixture's {@code Frame}.
   *
   * @return this fixture.
   */
  @Override
  public @Nonnull FrameFixture iconify() {
    driver().iconify(target());
    return this;
  }

  /**
   * Simulates a user deiconifying this fixture's {@code Frame}.
   *
   * @return this fixture.
   */
  @Override
  public @Nonnull FrameFixture deiconify() {
    driver().deiconify(target());
    return this;
  }

  /**
   * Simulates a user maximizing this fixture's {@code Frame}.
   *
   * @return this fixture.
   * @throws ActionFailedException if the operating system does not support maximizing frames.
   */
  @Override
  public @Nonnull FrameFixture maximize() {
    driver().maximize(target());
    return this;
  }

  /**
   * Simulates a user normalizing this fixture's {@code Frame}.
   *
   * @return this fixture.
   */
  @Override
  public @Nonnull FrameFixture normalize() {
    driver().normalize(target());
    return this;
  }
}
