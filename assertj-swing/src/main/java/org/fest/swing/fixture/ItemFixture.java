/*
 * Created on Dec 7, 2007
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

import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of single items inside {@code JComponents} (e.g. a cell in a {@code JTable} or a row in a
 * {@code JList}).
 * 
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public interface ItemFixture<S> extends MouseInputSimulationFixture<S> {
  /**
   * Simulates a user selecting this fixture's item.
   * 
   * @return this fixture.
   * @throws IllegalStateException if the component containing this fixture's item is disabled.
   * @throws IllegalStateException if the component containing this fixture's item is not showing on the screen.
   */
  @Nonnull S select();

  /**
   * @return the {@code String} representation of this fixture's item, or {@code null} if one can not be obtained.
   */
  @Nullable String value();

  /**
   * Simulates a user dragging this fixture's item.
   * 
   * @return this fixture.
   * @throws IllegalStateException if the component containing this fixture's item is disabled.
   * @throws IllegalStateException if the component containing this fixture's item is not showing on the screen.
   */
  @Nonnull S drag();

  /**
   * Simulates a user dropping into this fixture's item.
   * 
   * @return this fixture.
   * @throws IllegalStateException if the component containing this fixture's item is disabled.
   * @throws IllegalStateException if the component containing this fixture's item is not showing on the screen.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @Nonnull S drop();

  /**
   * Shows a pop-up menu using this fixture's item as the invoker of the pop-up menu.
   * 
   * @return a fixture that handles functional testing of the displayed pop-up menu.
   * @throws IllegalStateException if the component containing this fixture's item is disabled.
   * @throws IllegalStateException if the component containing this fixture's item is not showing on the screen.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Nonnull JPopupMenuFixture showPopupMenu();
}