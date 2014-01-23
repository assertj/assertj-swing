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

import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#selectRows(javax.swing.JTree, int[])}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_selectRows_Test extends JTreeDriver_selectCell_TestCase {
  @Test
  public void should_select_cells() {
    showWindow();
    clearTreeSelection();
    updateTreeWithDefaultSelectionModel();
    requireNoSelection();
    int[] rows = { 0, 1, 2 };
    driver.selectRows(tree, rows);
    requireSelectedRows(rows);
  }

  @Test
  public void should_throw_error_if_JTree_is_disabled() {
    disableTree();
    int[] rows = { 0, 1, 2 };
    try {
      driver.selectRows(tree, rows);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_not_showing_on_the_screen() {
    int[] rows = { 0, 1, 2 };
    try {
      driver.selectRows(tree, rows);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
