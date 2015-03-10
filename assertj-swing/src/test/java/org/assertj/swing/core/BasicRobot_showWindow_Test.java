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

import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;

import java.util.logging.Logger;

import javax.swing.JFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#showWindow(java.awt.Window)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_showWindow_Test extends EDTSafeTestCase {
  @Rule
  public ExpectedException thrown = none();

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
  public void should_Throw_Error_If_Window_Never_Shown() {
    AlwaysInvisibleFrame window = AlwaysInvisibleFrame.createNew();
    LOGGER.info(concat("Waiting for ", AlwaysInvisibleFrame.class.getSimpleName(), " to show up"));
    thrown.expect(WaitTimedOutError.class, "Timed out waiting for Window to open");
    robot.showWindow(window);
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
