/*
 * Created on Dec 16, 2006
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JButton;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JButton}s.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JButtonFixture extends AbstractButtonFixture<JButtonFixture, JButton> {
  /**
   * Creates a new {@link JButtonFixture}.
   * 
   * @param target the {@code JButton} to be managed by this fixture.
   * @param robot performs simulation of user events on the given {@code JButton}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JButtonFixture(@Nonnull Robot robot, @Nonnull JButton target) {
    super(JButtonFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JButtonFixture}.
   * 
   * @param robot performs simulation of user events on a {@code JButton}.
   * @param buttonName the name of the {@code JButton} to find using the given {@code RobotFixture}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JButton} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JButton} is found.
   */
  public JButtonFixture(@Nonnull Robot robot, @Nullable String buttonName) {
    super(JButtonFixture.class, robot, buttonName, JButton.class);
  }
}
