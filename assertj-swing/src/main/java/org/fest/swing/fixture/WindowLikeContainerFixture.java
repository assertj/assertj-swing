/*
 * Created on Dec 18, 2007
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

/**
 * Supports functional testing of window-like containers (not necessarily subclasses of {@code Window}).
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public interface WindowLikeContainerFixture<S> {
  /**
   * Simulates a user closing this fixture's window-like container.
   */
  void close();

  /**
   * Simulates a user resizing horizontally this fixture's window-like container.
   * 
   * @param width the width that this fixture's window-like container should have after being resized.
   * @return this fixture.
   */
  @Nonnull S resizeWidthTo(int width);

  /**
   * Simulates a user resizing vertically this fixture's window-like container.
   * 
   * @param height the height that this fixture's window-like container should have after being resized.
   * @return this fixture.
   */
  @Nonnull S resizeHeightTo(int height);

  /**
   * Simulates a user resizing this fixture's window-like container.
   * 
   * @param size the size that the target window should have after being resized.
   * @return this fixture.
   */
  @Nonnull S resizeTo(@Nonnull Dimension size);

  /**
   * Asserts that the size of this fixture's window-like container is equal to given one.
   * 
   * @param size the given size to match.
   * @return this fixture.
   * @throws AssertionError if the size of this fixture's window-like container is not equal to the given size.
   */
  @Nonnull S requireSize(@Nonnull Dimension size);

  /**
   * Simulates a user moving this fixture's window-like container to the given point.
   * 
   * @param p the point to move this fixture's window-like container to.
   * @return this fixture.
   */
  @Nonnull S moveTo(@Nonnull Point p);

  /**
   * Brings this fixture's window-like component to the front.
   * 
   * @return this fixture.
   */
  @Nonnull S moveToFront();

  /**
   * Sends this fixture's window-like component to the back.
   * 
   * @return this fixture.
   */
  @Nonnull S moveToBack();
}
