/*
 * Created on Apr 10, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "A@Nonnull S IS" BASIS, WITHOUT WARRANTIE@Nonnull S OR CONDITION@Nonnull S OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.annotation.Nonnull;

/**
 * Simulates user events on {@code Component}s that accept text input from the user.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 *
 * @author Alex Ruiz
 */
public interface TextInputFixture<S> extends TextDisplayFixture<S>, EditableComponentFixture<S> {
  /**
   * Simulates a user entering the given text in the {@code Component} managed by this fixture.
   * @param text the text to enter.
   * @return this fixture.
   */
  @Nonnull S enterText(@Nonnull String text);

  /**
   * Simulates a user deleting all the text in the {@code Component} managed by this fixture.
   * @return this fixture.
   */
  @Nonnull S deleteText();

  /**
   * Simulates a user selecting all the text contained in the {@code Component} managed by this fixture.
   * @return this fixture.
   */
  @Nonnull S selectAll();

  /**
   * Simulates a user selecting a portion of the text contained in the {@code Component} managed by this
   * fixture.
   * @param start index where selection should start.
   * @param end index where selection should end.
   * @return this fixture.
   */
  @Nonnull S selectText(int start, int end);

  /**
   * Simulates a user selecting the given text contained in the {@code Component} managed by this fixture.
   * @param text the text to select.
   * @return this fixture.
   */
  @Nonnull S select(@Nonnull String text);
}
