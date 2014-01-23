/*
 * Created on Feb 21, 2013
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
 * Copyright @2013 the original author or authors.
 */
package org.fest.swing.test.core;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.swing.core.GenericTypeMatcher;

/**
 * A matcher that always returns {@code false} in {@link #isMatching(Component)}.
 * @param <T> the type of {@code Component} this matcher supports.
 *
 * @author Alex Ruiz
 */
public class NeverMatchingComponentMatcher<T extends Component> extends GenericTypeMatcher<T> {
  /**
   * Creates a new {@link NeverMatchingComponentMatcher}.
   *
   * @param supportedType the type supported by the matcher.
   * @return the created matcher.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public static @Nonnull <T extends Component> NeverMatchingComponentMatcher<T> neverMatches(
      @Nonnull Class<T> supportedType) {
    return new NeverMatchingComponentMatcher<T>(supportedType);
  }

  private NeverMatchingComponentMatcher(@Nonnull Class<T> supportedType) {
    super(supportedType);
  }

  @Override
  protected boolean isMatching(@Nonnull T component) {
    return false;
  }
}
