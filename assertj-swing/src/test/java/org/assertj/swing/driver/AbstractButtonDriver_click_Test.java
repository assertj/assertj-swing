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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.junit.Test;

/**
 * Tests for {@link AbstractButtonDriver#click(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class AbstractButtonDriver_click_Test extends AbstractButtonDriver_TestCase {
  @Test
  public void should_Click_Button() {
    showWindow();
    ActionPerformedRecorder recorder = ActionPerformedRecorder.attachTo(checkBox);
    driver.click(checkBox);
    recorder.wasPerformed();
  }

  @Test
  public void should_Throw_Error_If_AbstractButton_Is_Disabled() {
    disableCheckBox();
    ActionPerformedRecorder action = ActionPerformedRecorder.attachTo(checkBox);
    thrown.expectIllegalStateIsDisabledComponent();
    try {
      driver.click(checkBox);
    } finally {
      action.wasNotPerformed();
    }
  }

  @Test
  public void should_Throw_Error_If_AbstractButton_Is_Not_Showing_On_The_Screen() {
    ActionPerformedRecorder action = ActionPerformedRecorder.attachTo(checkBox);
    thrown.expectIllegalStateIsNotShowingComponent();
    try {
      driver.click(checkBox);
    } finally {
      action.wasNotPerformed();
    }
  }

  private static class ActionPerformedRecorder implements ActionListener {
    private boolean actionPerformed;

    static ActionPerformedRecorder attachTo(AbstractButton button) {
      ActionPerformedRecorder r = new ActionPerformedRecorder();
      button.addActionListener(r);
      return r;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      actionPerformed = true;
    }

    ActionPerformedRecorder wasPerformed() {
      assertThat(actionPerformed).isTrue();
      return this;
    }

    ActionPerformedRecorder wasNotPerformed() {
      assertThat(actionPerformed).isFalse();
      return this;
    }
  }
}
