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
package org.assertj.swing.assertions.error;

import java.awt.Dimension;

import org.assertj.core.error.BasicErrorMessageFactory;
import org.assertj.core.error.ErrorMessageFactory;

/**
 * Creates an error message indicating that an assertion that verifies that a value have certain dimension failed.
 * 
 * @author Alex Ruiz
 */
public class ShouldHaveDimension extends BasicErrorMessageFactory {

  /**
   * Creates a new </code>{@link ShouldHaveDimension}</code>.
   * 
   * @param actual the actual value in the failed assertion.
   * @param actualSize the size of {@code actual}.
   * @param expectedSize the expected size.
   * @return the created {@code ErrorMessageFactory}.
   */
  public static ErrorMessageFactory shouldHaveDimension(Object actual, Dimension actualSize, Dimension expectedSize) {
    return new ShouldHaveDimension(actual, actualSize, expectedSize);
  }

  private ShouldHaveDimension(Object actual, Dimension actualSize, Dimension expectedSize) {
    super("expected size:<%sx%s> but was:<%sx%s> in:<%s>", expectedSize.width, expectedSize.height, actualSize.width,
        actualSize.height, actual);
  }
}