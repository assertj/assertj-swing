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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Point;

import javax.swing.JPopupMenu;

import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.assertj.swing.test.recorder.ToolkitClickRecorder;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test for {@link ComponentDriver#invokePopupMenu(java.awt.Component, java.awt.Point)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentDriver_invokePopupAtPoint_Test extends ComponentDriver_invokePopup_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  public void should_Show_JPopupMenu() {
    showWindow();
    Point p = new Point(8, 6);
    ToolkitClickRecorder recorder = clickRecorder.attachToToolkitFor(window.textField);
    JPopupMenu found = driver.invokePopupMenu(window.textField, p);
    assertThat(found).isSameAs(popupMenu);
    recorder.wasRightClicked().timesClicked(1).clickedAt(p);
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Point_Is_Null() {
    driver.invokePopupMenu(window.textField, null);
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Disabled() {
    disableTextField();
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.invokePopupMenu(window.textField, new Point(8, 6));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.invokePopupMenu(window.textField, new Point(8, 6));
    } finally {
      assertThatTextFieldIsEmpty();
    }
  }
}
