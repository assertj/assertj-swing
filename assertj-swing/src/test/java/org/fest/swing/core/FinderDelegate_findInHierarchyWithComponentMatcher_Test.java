/*
 * Created on Jul 27, 2009
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

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.Test;

/**
 * Tests for {@link FinderDelegate#find(org.fest.swing.hierarchy.ComponentHierarchy, ComponentMatcher)}.
 *
 * @author Alex Ruiz
 */
public class FinderDelegate_findInHierarchyWithComponentMatcher_Test extends FinderDelegate_TestCase {
  @Test
  public void should_return_components_matching_ComponentMatcher() {
    ComponentMatcher matcher = new ComponentMatcher() {
      @Override
      public boolean matches(@Nullable Component c) {
        return c instanceof JTextField && "textBox".equals(c.getName());
      }
    };
    Collection<Component> found = finder.find(hierarchy, matcher);
    assertThat(found).containsOnly(window.textField);
  }

  @Test
  public void should_return_empty_collection_if_matching_Components_not_found() {
    ComponentMatcher matcher = new ComponentMatcher() {
      @Override
      public boolean matches(@Nullable Component c) {
        return c instanceof JButton;
      }
    };
    Collection<Component> found = finder.find(hierarchy, matcher);
    assertThat(found).isEmpty();
  }
}
