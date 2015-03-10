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
package org.assertj.swing.junit.ant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.expect;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageHandler#decodeBase64(String, ImageDecoder)}</code>.
 * 
 * @author Alex Ruiz
 */
public class ImageHandler_decodeBase64_withImageDecoder_Test extends ImageHandler_TestCase {

  private ImageDecoder decoder;
  private String encoded;

  @Before
  public void setUp() {
    decoder = mockImageDecoder();
    encoded = "Hello";
  }

  @Test
  public void should_Not_Rethrow_Error() {
    new EasyMockTemplate(decoder) {
      @Override
      protected void expectations() throws Throwable {
        expect(decoder.decodeBase64(encoded)).andThrow(thrownOnPurpose());
      }

      @Override
      protected void codeToTest() {
        assertThat(ImageHandler.decodeBase64(encoded, decoder)).isNull();
      }
    }.run();
  }
}
