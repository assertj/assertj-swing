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
import static org.fest.swing.core.MouseClickInfo.rightButton;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#clickPath(javax.swing.JTree, String, org.fest.swing.core.MouseClickInfo)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_clickPath_withMouseClickInfo_Test extends JTreeDriver_clickCell_TestCase {
  private static MouseClickInfo mouseClickInfo = rightButton().times(2);

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_MouseClickInfo_is_null() {
    MouseClickInfo info = null;
    driver.clickPath(tree, "root", info);
  }

  @Test
  public void should_click_path() {
    showWindow();
    ClickRecorder recorder = ClickRecorder.attachTo(tree);
    driver.clickPath(tree, "root/branch1/branch1.1/branch1.1.1", mouseClickInfo);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(2);
    String clickedPath = pathAtPoint(tree, recorder.pointClicked(), driver.separator());
    assertThat(clickedPath).isEqualTo("root/branch1/branch1.1/branch1.1.1");
  }

  @Test
  public void should_throw_error_if_path_not_found() {
    showWindow();
    try {
      driver.clickPath(tree, "another", mouseClickInfo);
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find path 'another'");
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_disabled() {
    disableTree();
    try {
      driver.clickPath(tree, "root/branch1", mouseClickInfo);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTree_is_not_showing_on_the_screen() {
    try {
      driver.clickPath(tree, "root/branch1", mouseClickInfo);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
