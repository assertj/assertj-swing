/*
 * Created on May 6, 2007
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
package org.fest.swing.image;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.image.TestImageFileWriters.singletonImageFileWriterMock;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.mockito.Mockito.when;

import java.awt.AWTException;

import org.fest.swing.util.RobotFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ScreenshotTaker#ScreenshotTaker(ImageFileWriter, RobotFactory)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker_constructor_Test {
  private ImageFileWriter writer;
  private RobotFactory robotFactory;
  private AWTException toThrow;

  @Before
  public void setUp() {
    writer = singletonImageFileWriterMock();
    robotFactory = newRobotFactoryMock();
    toThrow = new AWTException("Thrown on purpose");
  }

  @Test
  public void should_throw_wrapped_Exception_thrown_when_creating_Robot() throws AWTException {
    when(robotFactory.newRobotInPrimaryScreen()).thenThrow(toThrow);
    try {
      new ScreenshotTaker(writer, robotFactory);
      failWhenExpectingException();
    } catch (ImageException e) {
      assertThat(e.getCause()).isSameAs(toThrow);
    }
  }
}
