/*
 * Created on Jun 7, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.assertj.swing.test.assertion;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static org.fest.util.Objects.areEqual;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.internal.Failures;

/**
 * Understands assertion methods for images. To create a new instance of this class use the method
 * <code>{@link Assertions#assertThat(BufferedImage)}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author Ansgar Konermann
 */
public class ImageAssert extends ObjectAssert<BufferedImage> {

  /**
   * Reads the image in the specified path.
   * 
   * @param imageFilePath the path of the image to read.
   * @return the read image.
   * @throws NullPointerException if the given path is <code>null</code>.
   * @throws IllegalArgumentException if the given path does not belong to a file.
   * @throws IOException if any I/O error occurred while reading the image.
   */
  public static BufferedImage read(String imageFilePath) throws IOException {
    if (imageFilePath == null)
      throw new NullPointerException("The path of the image to read should not be null");
    File imageFile = new File(imageFilePath);
    if (!imageFile.isFile())
      throw new IllegalArgumentException(concat("The path ", quote(imageFilePath), " does not belong to a file"));
    return ImageIO.read(imageFile);
  }

  /**
   * Creates a new </code>{@link ImageAssert}</code>.
   * 
   * @param actual the target to verify.
   */
  protected ImageAssert(BufferedImage actual) {
    super(actual);
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if they have the same size and the
   * pixels at the same coordinates have the same color.
   * 
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   */
  public ImageAssert isEqualTo(BufferedImage expected) {
    return isEqualTo(expected, 0);
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if:
   * <ol>
   * <li>they have the same size</li>
   * <li>the difference between the RGB values of the color of each pixel is less than or equal to the given threshold</li>
   * </ol>
   * 
   * @param expected the given image to compare the actual image to.
   * @param threshold the threshold to use to decide if the color of two pixels are similar: two pixels that are
   *          identical to the human eye may still have slightly different color values. For example, by using a
   *          threshold of 1 we can indicate that a blue value of 60 is similar to a blue value of 61.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   * @since 1.1
   */
  public ImageAssert isEqualTo(BufferedImage expected, int threshold) {
    if (areEqual(actual, expected))
      return this;
    failIfNull(expected);
    failIfNotEqual(sizeOf(actual), sizeOf(expected));
    failIfNotEqualColor(expected, threshold);
    return this;
  }

  private void failIfNull(BufferedImage expected) {
    if (expected != null) {
      return;
    }
    throw Failures.instance().failure(actual + " is null");
  }

  private void failIfNotEqual(Dimension a, Dimension e) {
    if (areEqual(a, e))
      return;
    throw Failures.instance().failure(concat("image size, expected:[", e, "] but was:[", a, "]"));
  }

  private void failIfNotEqualColor(BufferedImage expected, int threshold) {
    int w = actual.getWidth();
    int h = actual.getHeight();
    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        failIfNotEqual(new RGBColor(actual.getRGB(x, y)), new RGBColor(expected.getRGB(x, y)), threshold, x, y);
      }
    }
  }

  private void failIfNotEqual(RGBColor a, RGBColor e, int threshold, int x, int y) {
    if (a.isEqualTo(e, threshold)) {
      return;
    }
    throw Failures.instance().failure(
        concat("expected:[", a, "] but was:[", e, "] at pixel [", valueOf(x), ",", valueOf(y), "]"));
  }

  /**
   * Verifies that the actual image is not equal to the given one. Two images are equal if they have the same size and
   * the pixels at the same coordinates have the same color.
   * 
   * @param image the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is equal to the given one.
   */
  public ImageAssert isNotEqualTo(BufferedImage image) {
    if (areEqual(actual, image)) {
      throw Failures.instance().failure(actual + " and " + image + " expected not to be equal.");
    }
    if (image == null)
      return this;
    if (areEqual(sizeOf(actual), sizeOf(image)) && hasEqualColor(image)) {
      throw Failures.instance().failure("images are equal");
    }
    return this;
  }

  private static Dimension sizeOf(BufferedImage image) {
    return new Dimension(image.getWidth(), image.getHeight());
  }

  private boolean hasEqualColor(BufferedImage expected) {
    int w = actual.getWidth();
    int h = actual.getHeight();
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++) {
        if (actual.getRGB(x, y) != expected.getRGB(x, y)) {
          return false;
        }
      }
    return true;
  }

  /**
   * Verifies that the size of the actual image is equal to the given one.
   * 
   * @param expected the expected size of the actual image.
   * @return this assertion object.
   * @throws AssertionError if the actual image is <code>null</code>.
   * @throws NullPointerException if the given size is <code>null</code>.
   * @throws AssertionError if the size of the actual image is not equal to the given one.
   */
  public ImageAssert hasSize(Dimension expected) {
    isNotNull();
    if (expected == null) {
      throw new NullPointerException("The size to compare to should not be null");
    }
    Dimension actualDimension = new Dimension(actual.getWidth(), actual.getHeight());
    Assertions.assertThat(actualDimension).isEqualTo(expected);
    return this;
  }

  private static final class RGBColor {

    private final int r;
    private final int g;
    private final int b;

    RGBColor(int rgb) {
      this(r(rgb), g(rgb), b(rgb));
    }

    RGBColor(int r, int g, int b) {
      this.r = r;
      this.g = g;
      this.b = b;
    }

    private static int r(int rgb) {
      return (rgb >> 16) & 0xFF;
    }

    private static int g(int rgb) {
      return (rgb >> 8) & 0xFF;
    }

    private static int b(int rgb) {
      return (rgb >> 0) & 0xFF;
    }

    int r() {
      return r;
    }

    int g() {
      return g;
    }

    int b() {
      return b;
    }

    boolean isEqualTo(RGBColor color, int threshold) {
      if (abs(r - color.r) > threshold)
        return false;
      if (abs(g - color.g) > threshold)
        return false;
      return abs(b - color.b) <= threshold;
    }

    @Override
    public String toString() {
      return concat("color[", "r=", valueOf(r), ",", "g=", valueOf(g), ",", "b=", valueOf(b), "]");
    }
  }
}
