/*
 * Created on Jul 16, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Arrays.array;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#requireSelection(javax.swing.JTree, String[])}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_requireSelectedPaths_Test extends JTreeDriver_selectCell_TestCase {
  @Test
  public void should_pass_if_single_cell_is_selected() {
    selectFirstChildOfRoot();
    driver.requireSelection(tree, array("root/branch1"));
  }

  @Test
  public void should_pass_if_cells_are_selected() {
    selectBranch1AndBranch1_1();
    driver.requireSelection(tree, array("root/branch1", "root/branch1/branch1.1"));
  }

  @RunsInEDT
  private void selectBranch1AndBranch1_1() {
    DefaultMutableTreeNode root = rootOf(tree);
    TreeNode branch1 = firstChildOf(root);
    TreePath root_branch1 = new TreePath(array(root, branch1));
    TreePath root_branch1_Branch1_1 = new TreePath(array(root, branch1, firstChildOf(branch1)));
    select(root_branch1, root_branch1_Branch1_1);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_expected_array_of_paths_is_null() {
    driver.requireSelection(tree, (String[]) null);
  }

  @Test
  public void should_fail_if_JTree_does_not_have_selection() {
    clearTreeSelection();
    try {
      driver.requireSelection(tree, array("root/branch1"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'").contains("No selection");
    }
  }

  @Test
  public void should_fail_if_selection_is_not_equal_to_expected() {
    selectFirstChildOfRoot();
    try {
      driver.requireSelection(tree, array("root/branch2"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'selection'").contains(
          "expecting selection:<['root/branch2']> but was:<[[root, branch1]]>");
    }
  }
}
