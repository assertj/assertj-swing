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
package org.assertj.swing.driver;

import static org.assertj.core.error.ShouldMatchPattern.shouldMatch;
import static org.assertj.swing.util.Strings.areEqualOrMatch;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.internal.Failures;

/**
 * Assertion methods related to text.
 * 
 * @author Alex Ruiz
 */
class TextAssert extends AbstractCharSequenceAssert<TextAssert, String> {
  static @Nonnull TextAssert assertThat(@Nullable String s) {
    return new TextAssert(s);
  }

  static @Nonnull TextAssert verifyThat(@Nullable String s) {
    return new TextAssert(s);
  }

  TextAssert(@Nullable String actual) {
    super(actual, TextAssert.class);
  }

  void isEqualOrMatches(@Nullable String s) {
    if (areEqualOrMatch(s, actual)) {
      return;
    }
    throw Failures.instance().failure(info, shouldMatch(actual, s));
  }
}
