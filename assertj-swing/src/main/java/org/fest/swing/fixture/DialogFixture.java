/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Dialog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.DialogDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code Dialog}s.
 *
 * @author Alex Ruiz
 */
public class DialogFixture extends AbstractWindowFixture<DialogFixture, Dialog, DialogDriver> {
  /**
   * Creates a new {@link DialogFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param target the {@code Dialog} to be managed by this fixture.
   * @throws NullPointerException if {@code target} is {@code null}.
   * @see BasicRobot#robotWithCurrentAwtHierarchy()
   */
  public DialogFixture(@Nonnull Dialog target) {
    super(DialogFixture.class, target);
  }

  /**
   * Creates a new {@link DialogFixture}.
   *
   * @param robot performs simulation of user events on the given {@code Dialog}.
   * @param target the {@code Dialog} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public DialogFixture(@Nonnull Robot robot, @Nonnull Dialog target) {
    super(DialogFixture.class, robot, target);
  }

  /**
   * Creates a new {@link DialogFixture}.
   *
   * @param robot performs simulation of user events on a {@code Dialog}.
   * @param dialogName the name of the {@code Dialog} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a {@code Dialog} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code Dialog} having a matching name is found.
   */
  public DialogFixture(@Nonnull Robot robot, @Nullable String dialogName) {
    super(DialogFixture.class, robot, dialogName, Dialog.class);
  }

  /**
   * Creates a new {@link DialogFixture}. This constructor creates a new {@link Robot} containing the current AWT
   * hierarchy.
   *
   * @param dialogName the name of the {@code Dialog} to find.
   * @throws ComponentLookupException if a {@code Dialog} having a matching name could not be found.
   * @throws ComponentLookupException if more than one {@code Dialog} having a matching name is found.
   * @see BasicRobot#robotWithCurrentAwtHierarchy()
   */
  public DialogFixture(@Nullable String dialogName) {
    super(DialogFixture.class, dialogName, Dialog.class);
  }

  @Override
  protected @Nonnull DialogDriver createDriver(@Nonnull Robot robot) {
    return new DialogDriver(robot);
  }

  /**
   * Asserts that this fixture's {@code Dialog} is modal.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Dialog} is not modal.
   */
  public @Nonnull DialogFixture requireModal() {
    driver().requireModal(target());
    return this;
  }
}
