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
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Arrays.array;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#selectPath(javax.swing.JTree, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_selectPath_Test extends JTreeDriver_selectCell_TestCase {
  @Test
  public void should_throw_error_if_path_not_found() {
    showWindow();
    try {
      driver.selectPath(tree, "another");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_disabled() {
    disableTree();
    try {
      driver.selectPath(tree, "root/branch1");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_not_do_anything_if_cell_is_already_selected() {
    showWindow();
    clearTreeSelection();
    select(pathToBranch_1_1_1());
    ClickRecorder recorder = ClickRecorder.attachTo(tree);
    driver.selectPath(tree, "root/branch1/branch1.1/branch1.1.1");
    assertThat(recorder).wasNotClicked();
    requireSelectedPaths("root/branch1/branch1.1/branch1.1.1");
  }

  @RunsInEDT
  private TreePath pathToBranch_1_1_1() {
    DefaultMutableTreeNode root = rootOf(tree);
    DefaultMutableTreeNode branch_1 = firstChildOf(root);
    DefaultMutableTreeNode branch_1_1 = firstChildOf(branch_1);
    return new TreePath(array(root, branch_1, branch_1_1, firstChildOf(branch_1_1)));
  }

  @Test
  public void should_throw_error_if_JTree_is_not_showing_on_the_screen() {
    try {
      driver.selectPath(tree, "root/branch1");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
