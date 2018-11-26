/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.image;

import java.awt.Component;
import java.awt.image.BufferedImage;

/**
 * @author Carsten Behring
 */
public interface ScreenshotTakerIF {
  /**
   * Takes a screenshot of the desktop and saves it as a PNG file.
   *
   * @param imageFilePath the path of the file to save the screenshot to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   * @throws IllegalArgumentException if the given file path belongs to a non-empty directory.
   * @throws org.assertj.swing.internal.annotation.IORuntimeException if an I/O error prevents the image from being
   *           saved as a file.
   */
  void saveDesktopAsPng(String imageFilePath);

  /**
   * Takes a screenshot of the desktop.
   *
   * @return the screenshot of the desktop.
   * @throws SecurityException if {@code readDisplayPixels} permission is not granted.
   */
  BufferedImage takeDesktopScreenshot();

  /**
   * Takes a screenshot of the given AWT or Swing {@code Component} and saves it as a PNG file.
   *
   * @param c the given {@code Component}.
   * @param imageFilePath the path of the file to save the screenshot to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   * @throws IllegalArgumentException if the given file path belongs to a non-empty directory.
   * @throws org.assertj.swing.internal.annotation.IORuntimeException if an I/O error prevents the image from being
   *           saved as a file.
   */
  void saveComponentAsPng(Component c, String imageFilePath);

  /**
   * Takes a screenshot of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return a screenshot of the given {@code Component}.
   * @throws SecurityException if {@code readDisplayPixels} permission is not granted.
   */
  BufferedImage takeScreenshotOf(Component c);

  /**
   * Saves the given image as a PNG file.
   *
   * @param image the image to save.
   * @param filePath the path of the file to save the image to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   * @throws IllegalArgumentException if the given file path belongs to a non-empty directory.
   * @throws org.assertj.swing.internal.annotation.IORuntimeException if an I/O error prevents the image from being
   *           saved as a file.
   */
  void saveImage(BufferedImage image, String filePath);
}