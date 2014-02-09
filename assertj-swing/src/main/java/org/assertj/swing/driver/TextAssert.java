/*
 * Created on Jun 20, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.util.Strings.quote;
import static org.assertj.swing.util.Strings.areEqualOrMatch;
import static org.assertj.swing.util.Strings.match;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;

/**
 * Assertion methods related to text.
 * 
 * @author Alex Ruiz
 */
class TextAssert extends AbstractAssert<TextAssert, String> {
  static @Nonnull
  TextAssert assertThat(@Nullable String s) {
    return new TextAssert(s);
  }

  static @Nonnull
  TextAssert verifyThat(@Nullable String s) {
    return new TextAssert(s);
  }

  TextAssert(@Nullable String actual) {
    super(actual, TextAssert.class);
  }

  @Nonnull
  TextAssert isEqualOrMatches(@Nullable String s) {
    if (areEqualOrMatch(s, actual)) {
      return this;
    }
    String format = "actual value:<%s> is not equal to or does not match pattern:<%s>";
    String msg = String.format(format, quote(actual), quote(s));
    throw Failures.instance().failure(msg);
  }

  @Nonnull
  TextAssert matches(@Nonnull Pattern pattern) {
    if (match(pattern, actual)) {
      return this;
    }
    String format = "actual value:<%s> does not match pattern:<%s>";
    String msg = String.format(format, quote(actual), quote(pattern.pattern()));
    fail(msg);
    // dead code (cause fail throws an exception) - but needed for the compiler
    return this;
  }
}
