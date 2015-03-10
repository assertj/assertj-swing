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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.test.awt.TestComponents.singletonComponentMock;
import static org.assertj.swing.test.awt.TestContainers.singletonContainerMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Container;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link HierarchyBasedFocusOwnerFinder#focusOwner()}.
 * 
 * @author Alex Ruiz
 */
public class HierarchyBasedFocusOwnerFinder_focusOwner_Test {
  private static Container container;

  private ContainerFocusOwnerFinder delegate;
  private HierarchyRootsSource rootsSource;
  private HierarchyBasedFocusOwnerFinder finder;

  @BeforeClass
  public static void setUpOnce() {
    container = singletonContainerMock();
  }

  @Before
  public void setUp() {
    delegate = mock(ContainerFocusOwnerFinder.class);
    rootsSource = mock(HierarchyRootsSource.class);
    finder = new HierarchyBasedFocusOwnerFinder(delegate, rootsSource);
  }

  @Test
  public void should_Return_Focus_Owner_From_Delegate() {
    Component focusOwner = singletonComponentMock();
    when(rootsSource.existingHierarchyRoots()).thenReturn(array(container));
    when(delegate.focusOwnerOf(container)).thenReturn(focusOwner);
    assertThat(finder.focusOwner()).isSameAs(focusOwner);
  }

  @Test
  public void should_Return_Null_If_Delegate_Did_Not_Find_Focus_Owner() {
    when(rootsSource.existingHierarchyRoots()).thenReturn(array(container));
    when(delegate.focusOwnerOf(container)).thenReturn(null);
    assertThat(finder.focusOwner()).isNull();
  }

  @Test
  public void should_Return_Null_If_There_Are_Not_Any_Root_Containers() {
    when(rootsSource.existingHierarchyRoots()).thenReturn(new Container[0]);
    assertThat(finder.focusOwner()).isNull();
  }
}
