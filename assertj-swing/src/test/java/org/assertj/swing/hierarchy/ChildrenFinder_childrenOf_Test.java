/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.test.awt.TestComponents.newComponentMock;
import static org.assertj.swing.test.awt.TestContainers.newContainerMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ChildrenFinder#childrenOf(Component)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ChildrenFinder_childrenOf_Test extends EDTSafeTestCase {
  private Container container;
  private ChildrenFinderStrategy strategy1;
  private ChildrenFinderStrategy strategy2;

  private Component child1;
  private Component child2;
  private Component child3;

  private List<ChildrenFinderStrategy> originalStrategies;

  private ChildrenFinder finder;

  @Before
  public void setUp() {
    container = newContainerMock();
    strategy1 = childrenFinderStrategyMock();
    strategy2 = childrenFinderStrategyMock();
    child1 = newComponentMock();
    child2 = newComponentMock();
    child3 = newComponentMock();
    finder = new ChildrenFinder();
    originalStrategies = ChildrenFinder.strategies();
    ChildrenFinder.replaceStrategiesWith(newArrayList(strategy1, strategy2));
  }

  private ChildrenFinderStrategy childrenFinderStrategyMock() {
    return mock(ChildrenFinderStrategy.class);
  }

  @After
  public void tearDown() {
    ChildrenFinder.replaceStrategiesWith(originalStrategies);
  }

  @Test
  public void should_Return_Children_In_Container_And_Strategies() {
    when(container.getComponents()).thenReturn(Arrays.array(child1));
    when(strategy1.nonExplicitChildrenOf(container)).thenReturn(newArrayList(child2));
    when(strategy2.nonExplicitChildrenOf(container)).thenReturn(newArrayList(child3));
    Collection<Component> children = finder.childrenOf(container);
    assertThat(children).containsOnly(child1, child2, child3);
  }

  @Test
  public void should_Return_Empty_Collection_If_Component_Is_Not_Container() {
    Collection<Component> children = finder.childrenOf(child1);
    assertThat(children).isEmpty();
  }
}
