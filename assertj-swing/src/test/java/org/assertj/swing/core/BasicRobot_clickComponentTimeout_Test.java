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
package org.assertj.swing.core;

import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import static org.assertj.swing.timing.Pause.pause;
import static org.mockito.Matchers.anyObject;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_clickComponentTimeout_Test extends BasicRobot_ClickTestCase {
  @Test
  public void should_click_component_after_it_is_visible() throws InvocationTargetException, InterruptedException {
    EventQueue.invokeAndWait(() -> window().textField().setVisible(false));

    Executors.newSingleThreadExecutor().execute(() -> {
      pause(250);
      EventQueue.invokeLater(() -> window().textField().setVisible(true));
    });

    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window().textField());
    robot().click(window().textField());
    recorder.clicked(LEFT_BUTTON).timesClicked(1);
  }

  @Test
  public void should_click_component_after_it_resizes_to_visible() throws InvocationTargetException, InterruptedException {
    EventQueue.invokeAndWait(() -> window().textField().setSize(-10, -10));

    Executors.newSingleThreadExecutor().execute(() -> {
      pause(300);
      EventQueue.invokeLater(() -> window().textField().setSize(10, 10));
    });

    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window().textField());
    robot().click(window().textField());
    recorder.clicked(LEFT_BUTTON).timesClicked(1);
  }

  @Test
  public void should_not_click_if_component_size_is_negative() throws InvocationTargetException, InterruptedException, ActionFailedException {
    robot().settings().timeoutToBeVisible(300);
    EventQueue.invokeAndWait(() -> window().textField().setSize(-10, -10));

    AWTEventListener listener = Mockito.mock(AWTEventListener.class);
    Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);
    try {
      robot().click(window().textField());
    } catch (ActionFailedException e) {}
    Mockito.verify(listener, Mockito.never()).eventDispatched(anyObject());
  }

  @Test(expected = ActionFailedException.class)
  public void should_throw_exception_after_timeout() throws InvocationTargetException, InterruptedException {
    robot().settings().timeoutToBeVisible(300);
    EventQueue.invokeAndWait(() -> window().textField().setSize(-10, -10));
      robot().click(window().textField());
  }
}
