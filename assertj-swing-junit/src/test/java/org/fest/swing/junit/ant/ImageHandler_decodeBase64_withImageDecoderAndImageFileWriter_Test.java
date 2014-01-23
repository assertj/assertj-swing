/*
 * Created on Aug 24, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.image.BufferedImage;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.image.ImageFileWriter;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageHandler#decodeBase64AndSaveAsPng(String, String, ImageDecoder, org.fest.swing.image.ImageFileWriter)}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageHandler_decodeBase64_withImageDecoderAndImageFileWriter_Test extends ImageHandler_TestCase {

  private ImageDecoder decoder;
  private String encoded;
  private ImageFileWriter writer;
  private BufferedImage image;
  private String path;

  @Before
  public void setUp() {
    encoded = "Hello";
    decoder = mockImageDecoder();
    image = mockImage();
    writer = createMock(ImageFileWriter.class);
    path = "somePath";
  }

  @Test
  public void should_not_rethrow_error() {
    new EasyMockTemplate(decoder, writer) {
      @Override protected void expectations() throws Throwable {
        expect(decoder.decodeBase64(encoded)).andReturn(image);
        writer.writeAsPng(image, path);
        expectLastCall().andThrow(thrownOnPurpose());
      }

      @Override protected void codeToTest() {
        assertThat(ImageHandler.decodeBase64AndSaveAsPng(encoded, path, decoder, writer)).isEmpty();
      }
    }.run();
  }
}
