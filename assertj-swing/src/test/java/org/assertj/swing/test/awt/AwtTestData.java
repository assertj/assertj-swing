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
package org.assertj.swing.test.awt;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static org.assertj.swing.assertions.data.RgbColor.color;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.assertj.swing.assertions.data.RgbColor;

/**
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class AwtTestData {

  private static final RgbColor BLUE = color(0x0000FF);
  private static final RgbColor YELLOW = color(0xFFFF00);

  private AwtTestData() {
  }

  public static RgbColor blue() {
    return BLUE;
  }

  public static BufferedImage fivePixelBlueImage() {
    return fivePixelImage(Color.BLUE);
  }

  public static RgbColor yellow() {
    return YELLOW;
  }

  public static BufferedImage fivePixelYellowImage() {
    return fivePixelImage(Color.YELLOW);
  }

  private static BufferedImage fivePixelImage(Color color) {
    return newImage(5, 5, color);
  }

  public static BufferedImage newImage(int width, int height, Color color) {
    BufferedImage image = new BufferedImage(width, height, TYPE_INT_ARGB);
    Graphics graphics = image.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, width, height);
    return image;
  }
}
