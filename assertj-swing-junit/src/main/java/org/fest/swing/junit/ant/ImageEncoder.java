/*
 * Created on Apr 26, 2009
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

import static org.fest.swing.image.ImageFileExtensions.PNG;
import static org.fest.swing.junit.ant.CommonConstants.UTF_8;
import static org.fest.util.Files.flushAndClose;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * Understands how to encode a given image using the base64 algorithm.
 *
 * @author Alex Ruiz
 */
class ImageEncoder {

  String encodeBase64(BufferedImage image) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, PNG, out);
      byte[] encoded = Base64.encodeBase64(out.toByteArray());
      return new String(encoded, UTF_8);
    } finally {
      flushAndClose(out);
    }
  }
}
