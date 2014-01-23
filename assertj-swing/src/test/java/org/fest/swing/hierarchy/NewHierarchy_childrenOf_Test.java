/*
 * Created on Nov 12, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.hierarchy.JFrameContentPaneQuery.contentPaneOf;

import org.junit.Test;

/**
 * Tests for {@link NewHierarchy#childrenOf(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class NewHierarchy_childrenOf_Test extends NewHierarchy_TestCase {
  @Test
  public void should_return_empty_Collection_if_Component_is_ignored() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, true);
    assertThat(hierarchy.childrenOf(window)).isEmpty();
  }

  @Test
  public void should_return_children_of_Component() {
    NewHierarchy hierarchy = new NewHierarchy(toolkit, filter, false);
    filter.ignore(window.textField);
    assertThat(hierarchy.childrenOf(contentPaneOf(window))).containsOnly(window.comboBox);
  }
}
