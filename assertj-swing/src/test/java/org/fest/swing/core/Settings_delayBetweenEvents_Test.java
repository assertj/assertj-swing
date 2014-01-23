/*
 * Created on Dec 19, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.awt.Robot;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link Settings#delayBetweenEvents(int)} and {@link Settings#delayBetweenEvents()}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class Settings_delayBetweenEvents_Test {
  private final int delay;

  private Settings settings;
  private java.awt.Robot robot;

  @Parameters
  public static Collection<Object[]> autoDelays() {
    return newArrayList(new Object[][] {
        { 100 },
        { 200 },
        { 68 }
      });
  }

  public Settings_delayBetweenEvents_Test(int delay) {
    this.delay = delay;
  }

  @Before
  public void setUp() throws Exception {
    settings = new Settings();
    robot = new Robot();
  }

  @Test
  public void shouldUpdateAndReturnDelayBetweenEvents() {
    settings.attachTo(robot);
    settings.delayBetweenEvents(delay);
    assertThat(robot.getAutoDelay()).isEqualTo(settings.delayBetweenEvents());
  }
}
