/*
 * Created on Jul 8, 2008
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
package org.fest.swing.fixture;

import javax.annotation.Nonnull;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;

/**
 * Simulates mouse input on an AWT or Swing {@code Component}.
 * 
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * 
 * @author Alex Ruiz
 */
public interface MouseInputSimulationFixture<S> {
  /**
   * Simulates a user clicking this fixture's {@code Component}.
   * 
   * @return this fixture.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @Nonnull S click();

  /**
   * Simulates a user clicking this fixture's {@code Component}.
   * 
   * @param button the button to click.
   * @return this fixture.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @Nonnull S click(@Nonnull MouseButton button);

  /**
   * Simulates a user clicking this fixture's {@code Component}.
   * 
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @Nonnull S click(@Nonnull MouseClickInfo mouseClickInfo);

  /**
   * Simulates a user double-clicking this fixture's {@code Component}.
   * 
   * @return this fixture.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @Nonnull S doubleClick();

  /**
   * Simulates a user right-clicking this fixture's {@code Component}.
   * 
   * @return this fixture.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @Nonnull S rightClick();
}