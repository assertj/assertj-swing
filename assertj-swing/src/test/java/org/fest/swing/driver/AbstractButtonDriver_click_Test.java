/*
 * Created on Aug 9, 2009
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.fest.assertions.AssertExtension;
import org.junit.Test;

/**
 * Tests for {@link AbstractButtonDriver#click(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class AbstractButtonDriver_click_Test extends AbstractButtonDriver_TestCase {
  @Test
  public void should_click_button() {
    showWindow();
    ActionPerformedRecorder recorder = ActionPerformedRecorder.attachTo(checkBox);
    driver.click(checkBox);
    assertThat(recorder).wasPerformed();
  }

  @Test
  public void should_throw_error_if_AbstractButton_is_disabled() {
    disableCheckBox();
    ActionPerformedRecorder action = ActionPerformedRecorder.attachTo(checkBox);
    try {
      driver.click(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
    assertThat(action).wasNotPerformed();
  }

  @Test
  public void should_throw_error_if_AbstractButton_is_not_showing_on_the_screen() {
    ActionPerformedRecorder action = ActionPerformedRecorder.attachTo(checkBox);
    try {
      driver.click(checkBox);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
    assertThat(action).wasNotPerformed();
  }

  private static class ActionPerformedRecorder implements ActionListener, AssertExtension {
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
