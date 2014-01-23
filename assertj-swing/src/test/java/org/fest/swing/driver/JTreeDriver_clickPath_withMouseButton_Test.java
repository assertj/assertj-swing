/*
 * Created on Dec 27, 2009
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
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.fest.swing.core.MouseButton;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#clickPath(javax.swing.JTree, String, org.fest.swing.core.MouseButton)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_clickPath_withMouseButton_Test extends JTreeDriver_clickCell_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_MouseButton_is_null() {
    MouseButton button = null;
    driver.clickPath(tree, "root", button);
  }

  @Test
  public void should_click_path() {
    showWindow();
    ClickRecorder recorder = ClickRecorder.attachTo(tree);
    driver.clickPath(tree, "root/branch1/branch1.1/branch1.1.1", RIGHT_BUTTON);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(1);
    String clickedPath = pathAtPoint(tree, recorder.pointClicked(), driver.separator());
    assertThat(clickedPath).isEqualTo("root/branch1/branch1.1/branch1.1.1");
  }

  @Test
  public void should_throw_error_if_path_not_found() {
    showWindow();
    try {
      driver.clickPath(tree, "another", RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_disabled() {
    disableTree();
    try {
      driver.clickPath(tree, "root/branch1", RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_not_showing_on_the_screen() {
    try {
      driver.clickPath(tree, "root/branch1", RIGHT_BUTTON);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
