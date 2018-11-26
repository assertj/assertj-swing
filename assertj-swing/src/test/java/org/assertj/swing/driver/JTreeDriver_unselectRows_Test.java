/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.driver;

import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#unselectRows(javax.swing.JTree, int[])}.
 *
 * @author Christian Rösch
 */
public class JTreeDriver_unselectRows_Test extends JTreeDriver_selectCell_TestCase {
  @Test
  public void should_Unselect_Cells() {
    showWindow();
    clearTreeSelection();
    updateTreeWithDefaultSelectionModel();
    driver.selectRows(tree, new int[] { 0, 1, 2 });
    requireSelectedRows(new int[] { 0, 1, 2 });
    driver.unselectRows(tree, new int[] { 0, 2 });
    requireSelectedRows(new int[] { 1 });
  }

  @Test
  public void should_Throw_Error_If_JTree_Is_Disabled() {
    disableTree();
    int[] rows = { 0, 1, 2 };
    thrown.expectIllegalStateIsDisabledComponent();
    driver.unselectRows(tree, rows);
  }

  @Test
  public void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
    int[] rows = { 0, 1, 2 };
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.unselectRows(tree, rows);
  }
}
