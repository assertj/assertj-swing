/*
 * Created on Dec 4, 2009
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
package org.fest.swing.driver;

import static org.fest.swing.driver.JProgressBarValueQuery.valueOf;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.timing.Pause.pause;

import javax.annotation.Nonnull;
import javax.swing.JProgressBar;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiLazyLoadingDescription;
import org.fest.swing.timing.Condition;
import org.fest.swing.timing.Timeout;

/**
 * Waits until the value of a {@code JProgressBar} is equal to the given expected value. This task is executed in the
 * event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JProgressBarWaitUntilValueIsEqualToExpectedTask {
  @RunsInEDT
  static void waitUntilValueIsEqualToExpected(final @Nonnull JProgressBar progressBar, final int expected,
      final @Nonnull Timeout timeout) {
    pause(new Condition(untilValueIsEqualTo(progressBar, expected)) {
      @Override
      public boolean test() {
        return valueOf(progressBar) == expected;
      }
    }, timeout);
  }

  private static Description untilValueIsEqualTo(final @Nonnull JProgressBar progressBar, final int expected) {
    return new GuiLazyLoadingDescription() {
      @Override
      protected @Nonnull String loadDescription() {
        return String.format("value of %s to be equal to %d", format(progressBar), expected);
      }
    };
  }

  private JProgressBarWaitUntilValueIsEqualToExpectedTask() {}
}
