/*
 * Created on Aug 6, 2009
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

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link RobotEventGenerator#RobotEventGenerator(Settings)}.
 * 
 * @author Alex Ruiz
 */
public class RobotEventGenerator_constructorWithSettings_Test {
  private RobotEventGenerator eventGenerator;
  private Settings settings;

  @Before
  public void setUp() {
    settings = new Settings();
    eventGenerator = new RobotEventGenerator(settings);
  }

  @Test
  public void should_attach_Robot_to_Settings() {
    assertThat(eventGenerator.robot()).isSameAs(settings.robot());
  }
}
