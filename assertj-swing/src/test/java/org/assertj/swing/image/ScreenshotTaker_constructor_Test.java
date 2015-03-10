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
package org.assertj.swing.image;

import static org.assertj.swing.image.TestImageFileWriters.singletonImageFileWriterMock;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.util.TestRobotFactories.newRobotFactoryMock;
import static org.mockito.Mockito.when;

import java.awt.AWTException;

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.util.RobotFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ScreenshotTaker#ScreenshotTaker(ImageFileWriter, RobotFactory)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker_constructor_Test {
  @Rule
  public ExpectedException thrown = none();

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
  public void should_Throw_Wrapped_Exception_Thrown_When_Creating_Robot() throws AWTException {
    when(robotFactory.newRobotInPrimaryScreen()).thenThrow(toThrow);
    thrown.expectWrappingException(ImageException.class, toThrow);
    new ScreenshotTaker(writer, robotFactory);
  }
}
