/*
 * Created on Mar 11, 2008
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

/**
 * Verifies the state of editable {@code Component}s.
 * 
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public interface EditableComponentFixture<S> {
  /**
   * Asserts that the {@code Component} is editable.
   * 
   * @throws AssertionError if the {@code Component} is not editable.
   * @return this fixture.
   */
  @Nonnull S requireEditable();

  /**
   * Asserts that the {@code Component} is not editable.
   * 
   * @throws AssertionError if the {@code Component} is editable.
   * @return this fixture.
   */
  @Nonnull S requireNotEditable();

}
