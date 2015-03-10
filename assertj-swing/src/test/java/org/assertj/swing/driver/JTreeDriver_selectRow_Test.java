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
package org.assertj.swing.driver;

import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#selectRow(javax.swing.JTree, int)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_selectRow_Test extends JTreeDriver_selectCell_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  public void should_Select_Cell() {
    showWindow();
    clearTreeSelection();
    driver.selectRow(tree, 0);
    requireSelectedRows(0);
    driver.selectRow(tree, 1);
    requireSelectedRows(1);
    driver.selectRow(tree, 0);
    requireSelectedRows(0);
  }

  @Test
  public void should_Not_Do_Anything_If_Cell_Is_Already_Selected() {
    showWindow();
    clearTreeSelection();
    select(1);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tree);
    driver.selectRow(tree, 1);
    recorder.wasNotClicked();
    requireSelectedRows(1);
  }

  @Test
  public void should_Throw_Error_If_JTree_Is_Disabled() {
    disableTree();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectRow(tree, 0);
  }

  @Test
  public void should_Throw_Error_If_JTree_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectRow(tree, 0);
  }
}
