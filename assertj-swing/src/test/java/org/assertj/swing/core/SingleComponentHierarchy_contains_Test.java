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
import static org.assertj.swing.test.builder.JButtons.button;
import static org.mockito.Mockito.when;

import javax.swing.JButton;

import org.assertj.swing.hierarchy.SingleComponentHierarchy;
import org.junit.Test;

/**
 * Tests for {@link SingleComponentHierarchy#contains(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class SingleComponentHierarchy_contains_Test extends SingleComponentHierarchy_TestCase {
  @Test
  public void should_Return_True_If_Delegate_Contains_Component_And_Root_Contains_Component() {
    JButton button = button().createNew();
    root.add(button);
    when(hierarchyDelegate.contains(button)).thenReturn(true);
    assertThat(hierarchy.contains(button)).isTrue();
  }

  @Test
  public void should_Return_False_If_Delegate_Contains_Component_And_Root_Does_Not_Contain_Component() {
    JButton button = button().createNew();
    when(hierarchyDelegate.contains(button)).thenReturn(true);
    assertThat(hierarchy.contains(button)).isFalse();
  }

  @Test
  public void should_Return_False_If_Delegate_Does_Not_Contain_Component_But_Root_Does() {
    JButton button = button().createNew();
    root.add(button);
    when(hierarchyDelegate.contains(button)).thenReturn(false);
    assertThat(hierarchy.contains(button)).isFalse();
  }

  @Test
  public void should_Return_False_If_Both_Delegate_And_Root_Do_Not_Contain_Component() {
    JButton button = button().createNew();
    when(hierarchyDelegate.contains(button)).thenReturn(false);
    assertThat(hierarchy.contains(button)).isFalse();
  }
}
