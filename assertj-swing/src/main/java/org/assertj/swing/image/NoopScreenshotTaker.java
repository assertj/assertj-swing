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
package org.assertj.swing.image;

import java.awt.Component;
import java.awt.image.BufferedImage;

/**
 * Screenshot taker, which does nothing.
 * It's used in headless mode
 * 
 * @author Carsten Behring
 */
public class NoopScreenshotTaker implements ScreenshotTakerIF {

  private static final BufferedImage EMPTY_BUFFERED_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

  @Override
  public void saveDesktopAsPng(String imageFilePath) {
    // do nothing
  }

  @Override
  public BufferedImage takeDesktopScreenshot() {
    return EMPTY_BUFFERED_IMAGE;
  }

  @Override
  public void saveComponentAsPng(Component c, String imageFilePath) {
    // do nothing
  }

  @Override
  public BufferedImage takeScreenshotOf(Component c) {
    return EMPTY_BUFFERED_IMAGE;
  }

  @Override
  public void saveImage(BufferedImage image, String filePath) {
    // do nothing
  }
}