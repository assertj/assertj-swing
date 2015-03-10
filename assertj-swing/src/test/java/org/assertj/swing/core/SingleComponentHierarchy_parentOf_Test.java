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
import static org.assertj.swing.test.builder.JFrames.frame;
import static org.assertj.swing.test.builder.JLabels.label;
import static org.mockito.Mockito.when;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.assertj.swing.hierarchy.SingleComponentHierarchy;
import org.junit.Test;

/**
 * Tests for {@link SingleComponentHierarchy#parentOf(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class SingleComponentHierarchy_parentOf_Test extends SingleComponentHierarchy_TestCase {
  @Test
  public void should_Return_Parent_Of_Component() {
    JFrame p = frame().createNew();
    JLabel c = label().createNew();
    when(hierarchyDelegate.parentOf(c)).thenReturn(p);
    Container foundParent = hierarchy.parentOf(c);
    assertThat(foundParent).isSameAs(p);
  }
}
