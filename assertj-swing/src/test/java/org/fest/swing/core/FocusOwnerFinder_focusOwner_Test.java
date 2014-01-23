/*
 * Created on Apr 1, 2008
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;

import org.fest.swing.test.builder.JLabels;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link FocusOwnerFinder#focusOwner()}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FocusOwnerFinder_focusOwner_Test {
  private FocusOwnerFinderStrategy strategy1;
  private FocusOwnerFinderStrategy strategy2;

  @Before
  public void setUp() {
    strategy1 = mockFocusOwnerFinderStrategy();
    strategy2 = mockFocusOwnerFinderStrategy();
    FocusOwnerFinder.replaceStrategiesWith(strategy1, strategy2);
  }

  private static FocusOwnerFinderStrategy mockFocusOwnerFinderStrategy() {
    return mock(FocusOwnerFinderStrategy.class);
  }

  @After
  public void tearDown() {
    FocusOwnerFinder.initializeStrategies();
  }

  @Test
  public void should_try_next_strategy_if_focus_owner_not_found() {
    Component focusOwner = JLabels.label().createNew();
    when(strategy1.focusOwner()).thenThrow(new RuntimeException());
    when(strategy2.focusOwner()).thenReturn(focusOwner);
    assertThat(FocusOwnerFinder.focusOwner()).isSameAs(focusOwner);
  }

  @Test
  public void should_return_null_if_strategies_do_not_find_focus_owner() {
    when(strategy1.focusOwner()).thenThrow(new RuntimeException());
    when(strategy2.focusOwner()).thenThrow(new RuntimeException());
    assertThat(FocusOwnerFinder.focusOwner()).isNull();
  }
}
