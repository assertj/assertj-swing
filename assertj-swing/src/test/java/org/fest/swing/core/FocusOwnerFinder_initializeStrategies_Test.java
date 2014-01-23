/*
 * Created on Jul 28, 2009
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

import java.util.List;

import org.junit.Test;

/**
 * Tests for {@link FocusOwnerFinder#initializeStrategies()}.
 * 
 * @author Alex Ruiz
 */
public class FocusOwnerFinder_initializeStrategies_Test {
  @Test
  public void should_initialize_correct_strategies() {
    assertThatFocusOwnerFinderHasStrategiesOfType(ReflectionBasedFocusOwnerFinder.class,
        HierarchyBasedFocusOwnerFinder.class);
  }

  private static void assertThatFocusOwnerFinderHasStrategiesOfType(Class<?>... types) {
    List<FocusOwnerFinderStrategy> strategies = FocusOwnerFinder.strategies();
    int typeCount = types.length;
    assertThat(strategies).hasSize(typeCount);
    for (int i = 0; i < typeCount; i++) {
      assertThat(strategies.get(i)).isInstanceOf(types[i]);
    }
  }
}
