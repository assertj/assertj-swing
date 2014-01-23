/*
 * Created on Aug 1, 2008
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

import java.awt.Component;
import java.awt.Container;

import javax.annotation.Nonnull;

import org.fest.swing.core.Robot;

/**
 * An "extension method" for implementations of {@link AbstractContainerFixture}. This extension creates new
 * {@link AbstractComponentFixture}s that can handle {@code Component}s inside a {@link AbstractContainerFixture}'s
 * {@code Container}.
 *
 * @param <C> the type of {@code Component} the {@code ComponentFixture} to create can handle.
 * @param <F> the type of {@code ComponentFixture} this extension can create.
 *
 * @author Alex Ruiz
 */
public abstract class ComponentFixtureExtension<C extends Component, F extends AbstractComponentFixture<?, C, ?>> {
  /**
   * Creates a new {@link AbstractComponentFixture}.
   *
   * @param robot the {@code Robot} to pass to the new fixture.
   * @param root the container where the component to handle by the created fixture is contained.
   * @return the created {@code ComponentFixture}.
   */
  public abstract @Nonnull F createFixture(@Nonnull Robot robot, @Nonnull Container root);
}
