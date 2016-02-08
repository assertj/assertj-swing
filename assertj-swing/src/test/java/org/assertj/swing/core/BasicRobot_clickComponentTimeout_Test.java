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

import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.timing.Pause;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;

import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;

/**
 * Tests for {@link BasicRobot#click(java.awt.Component)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_clickComponentTimeout_Test extends BasicRobot_ClickTestCase {
  @Test
  public void should_Click_Component() throws InvocationTargetException, InterruptedException {
    EventQueue.invokeAndWait(() -> window().textField().setVisible(false));

    Executors.newSingleThreadExecutor().execute(() -> {
      Pause.pause(250);
      EventQueue.invokeLater(() -> window().textField().setVisible(true));
    });

    ClickRecorder recorder = clickRecorder.attachDirectlyTo(window().textField());
    robot().click(window().textField());
    recorder.clicked(LEFT_BUTTON).timesClicked(1);


  }
}
