/*
 * Created on Jun 8, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static java.io.File.separator;
import static java.util.logging.Level.SEVERE;
import static org.fest.util.Strings.isEmpty;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import org.fest.swing.image.ImageFileWriter;

/**
 * Understands base64 encoding and decoding of an image.
 *
 * @author Alex Ruiz
 */
public final class ImageHandler {

  private static final String EMPTY_STRING = "";

  private static Logger logger = Logger.getAnonymousLogger();

  private static ImageEncoder imageEncoder = new ImageEncoder();
  private static ImageDecoder imageDecoder = new ImageDecoder();
  private static ImageFileWriter imageFileWriter = new ImageFileWriter();

  /**
   * Encodes the given image using the base64 algorithm. Failures in encoding an image are simply logged, no exceptions
   * are thrown.
   * @param image the image to encode.
   * @return base64 characters.
   */
  public static String encodeBase64(BufferedImage image) {
    return encodeBase64(image, imageEncoder);
  }

  // makes testing easier
  static String encodeBase64(BufferedImage image, ImageEncoder encoder) {
    try {
      return encoder.encodeBase64(image);
    } catch (Exception e) {
      logger.log(SEVERE, "Unable to encode image", e);
      return null;
    }
  }

  /**
   * Decodes the given base64 characters into an image. Failures in decoding base64 characters are simply logged, no
   * exceptions are thrown.
   * @param encoded the given base64 characters.
   * @return the decoded image.
   */
  public static BufferedImage decodeBase64(String encoded) {
    return decodeBase64(encoded, imageDecoder);
  }

  // makes testing easier
  static BufferedImage decodeBase64(String encoded, ImageDecoder decoder) {
    try {
      return decoder.decodeBase64(encoded);
    } catch (Exception e) {
      logger.log(SEVERE, "Unable to encode image", e);
      return null;
    }
  }

  /**
   * Decodes the given base64 characters into an image, and saves the decoded image as a file using the given path.
   * Failures in decoding or saving the image as a file are simply logged, no exceptions are thrown.
   * @param encoded the given base64 characters.
   * @param path the path where to save the image file.
   * @return empty <code>String</code>. This method is used by this extensions XSL stylesheets to decode the image in
   * the XML report.
   */
  public static String decodeBase64AndSaveAsPng(String encoded, String path) {
    return decodeBase64AndSaveAsPng(encoded, path, imageDecoder, imageFileWriter);
  }

  // makes testing easier
  static String decodeBase64AndSaveAsPng(String encoded, String path, ImageDecoder decoder, ImageFileWriter writer) {
    if (isEmpty(encoded)) return EMPTY_STRING;
    if (isEmpty(path)) return EMPTY_STRING;
    String realPath = path.replace("/", separator);
    BufferedImage image = decodeBase64(encoded, decoder);
    try {
      writer.writeAsPng(image, realPath);
    } catch (Exception ignored) {
      logger.log(SEVERE, ignored.getMessage());
    }
    return EMPTY_STRING;
  }

  private ImageHandler() {}
}
