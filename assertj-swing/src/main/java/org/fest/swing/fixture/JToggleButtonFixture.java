/*
 * Created on Nov 22, 2007
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

import javax.annotation.Nonnull;
import javax.swing.JToggleButton;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JToggleButton}s.
 *
 * @author Alex Ruiz
 */
public class JToggleButtonFixture extends AbstractTwoStateButtonFixture<JToggleButtonFixture, JToggleButton> {
  /**
   * Creates a new {@link JToggleButtonFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JToggleButton}.
   * @param target the {@code JToggleButton} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JToggleButtonFixture(@Nonnull Robot robot, @Nonnull JToggleButton target) {
    super(JToggleButtonFixture.class, robot, target);
  }

  /**
   * Creates a new {@link org.fest.swing.fixture.JToggleButtonFixture}.
   *
   * @param robot performs simulation of user events on a {@code JToggleButton}.
   * @param toggleButtonName the name of the {@code JToggleButton} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JToggleButton} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JToggleButton} is found.
   */
  public JToggleButtonFixture(@Nonnull Robot robot, @Nonnull String toggleButtonName) {
    super(JToggleButtonFixture.class, robot, toggleButtonName, JToggleButton.class);
  }
}