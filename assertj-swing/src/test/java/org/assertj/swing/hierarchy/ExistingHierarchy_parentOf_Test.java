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
import static org.assertj.swing.test.builder.JTextFields.textField;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JTextField;

import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ExistingHierarchy#parentOf(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ExistingHierarchy_parentOf_Test extends SequentialEDTSafeTestCase {
  private ParentFinder parentFinder;
  private TestWindow window;
  private JTextField textField;
  private ExistingHierarchy hierarchy;

  @Override
  protected void onSetUp() {
    parentFinder = mock(ParentFinder.class);
    window = TestWindow.createNewWindow(getClass());
    textField = textField().createNew();
    hierarchy = new ExistingHierarchy(parentFinder, new ChildrenFinder());
  }

  @Override
  protected void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_Return_Parent_Of_Component() {
    when(parentFinder.parentOf(textField)).thenReturn(window);
    assertThat(hierarchy.parentOf(textField)).isSameAs(window);
  }
}
