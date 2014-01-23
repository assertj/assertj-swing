/*
 * Created on Sep 18, 2007
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
import javax.annotation.Nullable;
import javax.swing.JRadioButton;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JRadioButton}s.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JRadioButtonFixture extends AbstractTwoStateButtonFixture<JRadioButtonFixture, JRadioButton> {
  /**
   * Creates a new {@link JRadioButtonFixture}.
   * 
   * @param robot performs simulation of user events on the given {@code JRadioButton}.
   * @param target the {@code JRadioButton} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JRadioButtonFixture(@Nonnull Robot robot, @Nonnull JRadioButton target) {
    super(JRadioButtonFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JRadioButtonFixture}.
   * 
   * @param robot performs simulation of user events on a {@code JRadioButton}.
   * @param buttonName the name of the {@code JRadioButton} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JRadioButton} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JRadioButton} is found.
   */
  public JRadioButtonFixture(@Nonnull Robot robot, @Nullable String buttonName) {
    super(JRadioButtonFixture.class, robot, buttonName, JRadioButton.class);
  }
}
