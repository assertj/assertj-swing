/*
 * Created on Jul 24, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.fest.swing.test.data.BooleanProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link AbstractComponentMatcher#AbstractComponentMatcher(boolean)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class AbstractComponentMatcher_constructorWithRequireShowing_Test {
  private final boolean requireShowing;

  @Parameters
  public static @Nonnull Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public AbstractComponentMatcher_constructorWithRequireShowing_Test(boolean requireShowing) {
    this.requireShowing = requireShowing;
  }

  @Test
  public void should_require_showing_as_specified_in_constructor() {
    AbstractComponentMatcher matcher = new ConcreteComponentMatcher(requireShowing);
    assertThat(matcher.requireShowing()).isEqualTo(requireShowing);
  }
}
