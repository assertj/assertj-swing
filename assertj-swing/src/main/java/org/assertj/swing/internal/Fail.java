package org.assertj.swing.internal;

import org.assertj.core.internal.Failures;

public class Fail {
  public static AssertionError fail(String message) {
    return Failures.instance().failure(message);
  }
}
