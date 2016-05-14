/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.fixture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.AbstractButton;

import org.assertj.swing.core.Robot;

/**
 * Supports functional testing of {@code AbstractButton}s that have 2 states ("checked" and "unchecked.")
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <T> the type of {@code AbstractButton} that this fixture can manage.
 *
 * @author Alex Ruiz
 */
public abstract class AbstractTwoStateButtonFixture<S, T extends AbstractButton> extends AbstractButtonFixture<S, T> {
  /**
   * Creates a new {@link AbstractButtonFixture}.
   *
   * @param selfType the "self type."
   * @param target the {@code JButton} to be managed by this fixture.
   * @param robot performs simulation of user events on the given {@code AbstractButton}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public AbstractTwoStateButtonFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull T target) {
    super(selfType, robot, target);
  }

  /**
   * Creates a new {@link AbstractButtonFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code AbstractButton}.
   * @param buttonName the name of the {@code AbstractButton} to find using the given {@code RobotFixture}.
   * @param type the type of the {@code AbstractButton} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws org.assertj.swing.exception.ComponentLookupException if a matching {@code AbstractButton} could not be
   *           found. Or if more than one matching {@code AbstractButton} is found.
   */
  public AbstractTwoStateButtonFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String buttonName,
                                       @Nonnull Class<? extends T> type) {
    super(selfType, robot, buttonName, type);
  }

  /**
   * Checks (or unchecks) this fixture's {@code AbstractButton} according to the <code>checked</code> parameter.
   *
   * @param checked <code>true</code> if the {@link AbstractButton} should be checked.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code AbstractButton} is disabled.
   * @throws IllegalStateException if this fixture's {@code AbstractButton} is not showing on the screen.
   */
  public final @Nonnull S check(boolean checked) {
    if (checked) {
      return check();
    }
    return uncheck();
  }

  /**
   * Checks (or selects) this fixture's {@code AbstractButton} only it is not already checked.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code AbstractButton} is disabled.
   * @throws IllegalStateException if this fixture's {@code AbstractButton} is not showing on the screen.
   */
  public final @Nonnull S check() {
    driver().select(target());
    return myself();
  }

  /**
   * Unchecks this fixture's {@code AbstractButton} only if it is checked.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code AbstractButton} is disabled.
   * @throws IllegalStateException if this fixture's {@code AbstractButton} is not showing on the screen.
   */
  public final @Nonnull S uncheck() {
    driver().deselect(target());
    return myself();
  }

  /**
   * Verifies that this fixture's {@code AbstractButton} selection state is equal to the <code>selected</code>
   * parameter.
   *
   * @param selected <code>true</code> if the {@link AbstractButton} must be selected.
   * @return this fixture.
   * @throws AssertionError if the {@code AbstractButton} managed by this fixture is not selected.
   */
  public final @Nonnull S requireSelected(boolean selected) {
    if (selected) {
      return requireSelected();
    }
    return requireNotSelected();
  }

  /**
   * Verifies that this fixture's {@code AbstractButton} is selected.
   *
   * @return this fixture.
   * @throws AssertionError if the {@code AbstractButton} managed by this fixture is not selected.
   */
  public final @Nonnull S requireSelected() {
    driver().requireSelected(target());
    return myself();
  }

  /**
   * Verifies that this fixture's {@code AbstractButton} is not selected.
   *
   * @return this fixture.
   * @throws AssertionError if the {@code AbstractButton} managed by this fixture is selected.
   */
  public final @Nonnull S requireNotSelected() {
    driver().requireNotSelected(target());
    return myself();
  }

  /**
   * Verifies that this fixture's {@code AbstractButton} armed state is equal to the <code>armed</code> parameter.
   *
   * @param armed <code>true</code> if the {@link AbstractButton} must be armed.
   * @return this fixture.
   * @throws AssertionError if the {@code AbstractButton} managed by this fixture is not armed.
   */
  public final @Nonnull S requireArmed(boolean armed) {
    if (armed) {
      return requireArmed();
    }
    return requireNotArmed();
  }

  /**
   * Verifies that this fixture's {@code AbstractButton} is armed.
   *
   * @return this fixture.
   * @throws AssertionError if the {@code AbstractButton} managed by this fixture is not armed.
   */
  public final @Nonnull S requireArmed() {
    driver().requireArmed(target());
    return myself();
  }

  /**
   * Verifies that this fixture's {@code AbstractButton} is not armed.
   *
   * @return this fixture.
   * @throws AssertionError if the {@code AbstractButton} managed by this fixture is armed.
   */
  public final @Nonnull S requireNotArmed() {
    driver().requireNotArmed(target());
    return myself();
  }
}
