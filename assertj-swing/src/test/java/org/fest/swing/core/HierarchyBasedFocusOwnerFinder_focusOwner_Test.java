/*
 * Created on May 31, 2009
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
import static org.fest.swing.test.awt.TestComponents.singletonComponentMock;
import static org.fest.swing.test.awt.TestContainers.singletonContainerMock;
import static org.fest.util.Arrays.array;
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
  public void should_return_focus_owner_from_delegate() {
    Component focusOwner = singletonComponentMock();
    when(rootsSource.existingHierarchyRoots()).thenReturn(array(container));
    when(delegate.focusOwnerOf(container)).thenReturn(focusOwner);
    assertThat(finder.focusOwner()).isSameAs(focusOwner);
  }

  @Test
  public void should_return_null_if_delegate_did_not_find_focus_owner() {
    when(rootsSource.existingHierarchyRoots()).thenReturn(array(container));
    when(delegate.focusOwnerOf(container)).thenReturn(null);
    assertThat(finder.focusOwner()).isNull();
  }

  @Test
  public void should_return_null_if_there_are_not_any_root_Containers() {
    when(rootsSource.existingHierarchyRoots()).thenReturn(new Container[0]);
    assertThat(finder.focusOwner()).isNull();
  }
}
