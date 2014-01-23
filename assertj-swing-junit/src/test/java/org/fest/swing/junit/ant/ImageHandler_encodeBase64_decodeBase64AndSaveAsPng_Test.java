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

import static java.io.File.separator;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.util.Files.newTemporaryFolder;
import static org.fest.util.Strings.concat;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for <code>{@link ImageHandler#encodeBase64(java.awt.image.BufferedImage)}</code> and
 * <code>{@link ImageHandler#decodeBase64AndSaveAsPng(String, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageHandler_encodeBase64_decodeBase64AndSaveAsPng_Test extends ImageHandler_TestCase {

  @Test
  public void should_encode_image_and_save_it_decoded_as_file() throws IOException {
    String path = concat(newTemporaryFolder(), separator, "image.png");
    BufferedImage imageToEncode = screenshotTaker.takeDesktopScreenshot();
    String encoded = ImageHandler.encodeBase64(imageToEncode);
    assertThat(ImageHandler.decodeBase64AndSaveAsPng(encoded, path)).isEmpty();
    BufferedImage savedImage = read(path);
    assertThat(savedImage).isEqualTo(imageToEncode);
  }
}
