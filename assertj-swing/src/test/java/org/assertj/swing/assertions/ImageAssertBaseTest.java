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
package org.assertj.swing.assertions;

import static org.assertj.swing.test.awt.AwtTestData.fivePixelBlueImage;
import static org.mockito.Mockito.mock;

import java.awt.image.BufferedImage;

import org.assertj.core.api.BaseTestTemplate;
import org.assertj.swing.internal.assertions.Images;

/**
 * Base class for {@link ImageAssert} tests.
 * 
 * @author Olivier Michallat
 */
public abstract class ImageAssertBaseTest extends BaseTestTemplate<ImageAssert, BufferedImage> {
  protected Images images;

  @Override
  protected ImageAssert create_assertions() {
    return new ImageAssert(fivePixelBlueImage());
  }

  @Override
  protected void inject_internal_objects() {
    super.inject_internal_objects();
    images = mock(Images.class);
    assertions.images = images;
  }

  protected Images getImages(ImageAssert someAssertions) {
    return someAssertions.images;
  }
}
