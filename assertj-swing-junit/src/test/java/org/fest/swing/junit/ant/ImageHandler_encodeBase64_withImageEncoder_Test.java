/*
 * Created on Aug 24, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.image.BufferedImage;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageHandler#encodeBase64(BufferedImage, ImageEncoder)}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageHandler_encodeBase64_withImageEncoder_Test extends ImageHandler_TestCase {

  private ImageEncoder encoder;
  private BufferedImage image;

  @Before public void setUp() {
    encoder = createMock(ImageEncoder.class);
    image = mockImage();
  }

  @Test
  public void should_not_rethrow_error() {
    new EasyMockTemplate(encoder) {
      @Override protected void expectations() throws Throwable {
        expect(encoder.encodeBase64(image)).andThrow(thrownOnPurpose());
      }

      @Override protected void codeToTest() {
        assertThat(ImageHandler.encodeBase64(image, encoder)).isNull();
      }
    }.run();
  }
}
