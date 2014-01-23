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

import javax.swing.JTree;

import org.fest.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#collapsePath(JTree, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_collapsePath_Test extends JTreeDriver_toggleCell_TestCase {
  @Test
  public void should_collapse_cell() {
    showWindow();
    requireRowExpanded(0);
    driver.collapsePath(tree, "root");
    requireRowCollapsed(0);
  }

  @Test
  public void should_not_do_anything_if_cell_already_collapsed() {
    showWindow();
    requireRowCollapsed(2);
    driver.collapsePath(tree, "root");
    requireRowCollapsed(2);
  }

  @Test
  public void should_throw_error_if_given_path_does_not_exist() {
    showWindow();
    try {
      driver.collapsePath(tree, "somePath");
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'somePath'");
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_disabled() {
    disableTree();
    try {
      driver.collapsePath(tree, "root");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_not_showing_on_the_screen() {
    try {
      driver.collapsePath(tree, "root");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
