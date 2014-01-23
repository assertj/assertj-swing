/*
 * Created on Apr 27, 2009
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

import static org.fest.swing.junit.ant.CommonConstants.UTF_8;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * Understands how to decode base64 characters into an image.
 *
 * @author Alex Ruiz
 */
class ImageDecoder {

  BufferedImage decodeBase64(String encoded) throws IOException {
    ByteArrayInputStream in = null;
    byte[] toDecode = encoded.getBytes(UTF_8);
    in = new ByteArrayInputStream(Base64.decodeBase64(toDecode));
    return ImageIO.read(in);
  }
}
