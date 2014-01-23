/*
 * Created on Jun 6, 2009
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
import static org.fest.swing.test.awt.TestComponents.newComponentMock;
import static org.mockito.Mockito.when;

import java.awt.Component;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AbstractComponentMatcher#requireShowingMatches(Component)}.
 *
 * @author Alex Ruiz
 */
public class AbstractComponentMatcher_requireShowingMatches_Test {
  private Component component;

  @Before
  public void setUp() {
    component = newComponentMock();
  }

  @Test
  public void should_always_match_if_requireShowing_is_false() {
    AbstractComponentMatcher matcher = new ConcreteComponentMatcher(false);
    assertThat(matcher.requireShowingMatches(component)).isTrue();
  }

  @Test
  public void should_match_if_requireShowing_is_true_and_Component_is_showing() {
    AbstractComponentMatcher matcher = new ConcreteComponentMatcher(true);
    when(component.isShowing()).thenReturn(true);
    assertThat(matcher.requireShowingMatches(component)).isTrue();
  }

  @Test
  public void should_not_match_if_requireShowing_is_true_and_Component_is_not_showing() {
    AbstractComponentMatcher matcher = new ConcreteComponentMatcher(true);
    when(component.isShowing()).thenReturn(false);
    assertThat(matcher.requireShowingMatches(component)).isFalse();
  }
}
