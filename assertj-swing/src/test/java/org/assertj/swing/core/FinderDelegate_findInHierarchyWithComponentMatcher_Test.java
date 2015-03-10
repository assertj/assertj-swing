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

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.Test;

/**
 * Tests for {@link FinderDelegate#find(org.assertj.swing.hierarchy.ComponentHierarchy, ComponentMatcher)}.
 * 
 * @author Alex Ruiz
 */
public class FinderDelegate_findInHierarchyWithComponentMatcher_Test extends FinderDelegate_TestCase {
  @Test
  public void should_Return_Components_Matching_ComponentMatcher() {
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
  public void should_Return_Empty_Collection_If_Matching_Components_Not_Found() {
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
