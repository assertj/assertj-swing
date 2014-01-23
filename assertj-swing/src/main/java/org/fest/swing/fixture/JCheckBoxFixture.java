/*
 * Created on Jun 10, 2007
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
import javax.swing.JCheckBox;

import org.fest.swing.core.Robot;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JCheckBox}es.
 * 
 * @author Alex Ruiz
 */
public class JCheckBoxFixture extends AbstractTwoStateButtonFixture<JCheckBoxFixture, JCheckBox> {
  /**
   * Creates a new {@link JCheckBoxFixture}.
   * 
   * @param robot performs simulation of user events on the given {@code JCheckBox}.
   * @param target the {@code JCheckBox} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JCheckBoxFixture(@Nonnull Robot robot, @Nonnull JCheckBox target) {
    super(JCheckBoxFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JCheckBoxFixture}.
   * 
   * @param robot performs simulation of user events on a {@code JCheckBox}.
   * @param checkBoxName the name of the {@code JCheckBox} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JCheckBox} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JCheckBox} is found.
   */
  public JCheckBoxFixture(@Nonnull Robot robot, @Nonnull String checkBoxName) {
    super(JCheckBoxFixture.class, robot, checkBoxName, JCheckBox.class);
  }
}
