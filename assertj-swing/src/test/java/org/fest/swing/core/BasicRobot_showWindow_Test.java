/*
 * Created on Jul 25, 2009
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Strings.concat;

import java.util.logging.Logger;

import javax.swing.JFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#showWindow(java.awt.Window)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_showWindow_Test extends EDTSafeTestCase {
  private static final Logger LOGGER = Logger.getAnonymousLogger();

  private BasicRobot robot;

  @Before
  public void setUp() {
    robot = (BasicRobot) BasicRobot.robotWithNewAwtHierarchy();
  }

  @After
  public void tearDown() {
    robot.cleanUp();
  }

  @Test
  public void should_throw_error_if_window_never_shown() {
    try {
      AlwaysInvisibleFrame window = AlwaysInvisibleFrame.createNew();
      LOGGER.info(concat("Waiting for ", AlwaysInvisibleFrame.class.getSimpleName(), " to show up"));
      robot.showWindow(window);
      failWhenExpectingException();
    } catch (WaitTimedOutError e) {
      assertThat(e.getMessage()).contains("Timed out waiting for Window to open");
    }
  }

  private static class AlwaysInvisibleFrame extends JFrame {
    @RunsInEDT
    static AlwaysInvisibleFrame createNew() {
      return execute(new GuiQuery<AlwaysInvisibleFrame>() {
        @Override
        protected AlwaysInvisibleFrame executeInEDT() {
          return new AlwaysInvisibleFrame();
        }
      });
    }

    @Override
    public void setVisible(boolean b) {
      super.setVisible(false);
    }
  }
}
