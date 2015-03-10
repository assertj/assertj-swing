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
package org.assertj.swing.test;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Allows in-test specification of expected exception types and messages.
 * 
 * @author Alex Ruiz
 */
public class ExpectedException implements TestRule {
  private final org.junit.rules.ExpectedException delegate = org.junit.rules.ExpectedException.none();

  public static ExpectedException none() {
    return new ExpectedException();
  }

  private ExpectedException() {
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return delegate.apply(base, description);
  }

  public void expectAssertionError(String message) {
    expect(AssertionError.class, message);
  }

  public void expectAssertionError(String property, String expected, String actual) {
    expectAssertionErrorForProperty(property, doubleQuote(expected), doubleQuote(actual));
  }

  private String doubleQuote(String string) {
    return "\"" + string + "\"";
  }

  public void expectAssertionError(String property, String[] expected, String[] actual) {
    expectAssertionErrorForProperty(property, buildStringForMessage(expected), buildStringForMessage(actual));
  }

  private void expectAssertionErrorForProperty(String property, String expected, String actual) {
    expect(AssertionError.class);
    expectMessageToContain("property:'" + property + "'");
    expectMessageToContain("expected:<" + expected + "> but was:<" + actual + ">");
  }

  public void expectAssertionError(String property, String content, Pattern pattern) {
    expect(AssertionError.class);
    expectMessageToContain("property:'" + property + "'");
    expectMessageToContain("\nExpecting:\n \"" + content + "\"\nto match pattern:\n \"" + pattern.pattern() + "\"");
  }

  private String buildStringForMessage(String[] array) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < array.length; ++i) {
      sb.append("\"").append(array[i]).append("\"");
      if (i + 1 < array.length) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

  public void expectNullPointerException(String message) {
    expect(NullPointerException.class, message);
  }

  public void expectIllegalArgumentException(String message) {
    expect(IllegalArgumentException.class, message);
  }

  public void expectIndexOutOfBoundsException(String message) {
    expect(IndexOutOfBoundsException.class, message);
  }

  public void expectUnsupportedOperationException(String message) {
    expect(UnsupportedOperationException.class, message);
  }

  public void expect(Class<? extends Throwable> type, String message) {
    expect(type);
    expectMessage(message);
  }

  public void expect(Throwable error) {
    expect(error.getClass());
    expectMessage(error.getMessage());
  }

  public void expect(Class<? extends Throwable> type) {
    delegate.expect(type);
  }

  public void expectMessage(String message) {
    delegate.expectMessage(message);
  }

  public void expectMessageToContain(final String... strings) {
    delegate.expectMessage(new TypeSafeMatcher<String>() {
      @Override
      public void describeTo(org.hamcrest.Description description) {
        description.appendText("containing: " + Arrays.toString(strings));
      }

      @Override
      public boolean matchesSafely(String item) {
        for (String s : strings) {
          if (!item.contains(s)) {
            return false;
          }
        }
        return true;
      }
    });
  }

  public void expectMessageNotToContain(final String... strings) {
    delegate.expectMessage(new TypeSafeMatcher<String>() {
      @Override
      public void describeTo(org.hamcrest.Description description) {
        description.appendText("not containing: " + Arrays.toString(strings));
      }

      @Override
      public boolean matchesSafely(String item) {
        for (String s : strings) {
          if (item.contains(s)) {
            return false;
          }
        }
        return true;
      }
    });
  }

  public void expectWrappingException(Class<? extends Throwable> type, final Throwable wrapped) {
    expect(type);
    delegate.expect(new TypeSafeMatcher<Throwable>() {

      @Override
      public void describeTo(org.hamcrest.Description description) {
        description.appendText("cause is: " + wrapped);
      }

      @Override
      public boolean matchesSafely(Throwable item) {
        return item.getCause() == wrapped;
      }
    });
  }

  public void expectIllegalStateIsNotShowingComponent() {
    expect(IllegalStateException.class, "Expecting component");
    expectMessageToContain("to be showing on the screen");
  }

  public void expectIllegalStateIsNotResizableComponent() {
    expect(IllegalStateException.class, "Expecting component");
    expectMessageToContain("to be resizable by the user");
  }

  public void expectIllegalStateIsDisabledComponent() {
    expect(IllegalStateException.class, "Expecting component");
    expectMessageToContain("to be enabled");
  }
}
