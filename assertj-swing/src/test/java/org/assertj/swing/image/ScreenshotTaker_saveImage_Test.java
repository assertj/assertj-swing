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

import static org.assertj.swing.image.TestImageFileWriters.newImageFileWriterMock;
import static org.assertj.swing.test.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.assertj.swing.internal.annotation.IORuntimeException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.util.RobotFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ScreenshotTaker#saveImage(BufferedImage, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker_saveImage_Test {
  @Rule
  public ExpectedException thrown = none();

  private BufferedImage image;
  private String path;
  private ImageFileWriter writer;
  private IOException error;
  private ScreenshotTaker taker;

  @Before
  public void setUp() {
    image = mock(BufferedImage.class);
    path = "image.png";
    writer = newImageFileWriterMock();
    error = new IOException("On Purpose");
    taker = new ScreenshotTaker(writer, new RobotFactory());
  }

  @Test
  public void should_Throw_Wrapped_Exception_Thrown_When_Writing_Image_To_File() throws IOException {
    when(writer.writeAsPng(image, path)).thenThrow(error);
    thrown.expectWrappingException(IORuntimeException.class, error);
    taker.saveImage(image, path);
  }
}
