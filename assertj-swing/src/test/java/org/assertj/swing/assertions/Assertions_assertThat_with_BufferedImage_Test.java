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

import static org.assertj.core.api.AssertionInternalData.getActual;
import static org.assertj.swing.assertions.Assertions.assertThat;
import static org.assertj.swing.test.awt.AwtTestData.fivePixelBlueImage;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.awt.image.BufferedImage;

import org.junit.Test;

/**
 * Tests for <code>{@link AwtAssertions#assertThat(BufferedImage)}</code>.
 * 
 * @author Alex Ruiz
 */
public class Assertions_assertThat_with_BufferedImage_Test {

  @Test
  public void should_Create_Assert() {
    ImageAssert assertions = assertThat(fivePixelBlueImage());
    assertNotNull(assertions);
  }

  @Test
  public void should_Pass_Actual() {
    BufferedImage actual = fivePixelBlueImage();
    ImageAssert assertions = assertThat(actual);
    assertSame(actual, getActual(assertions));
  }
}
