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

import static org.assertj.swing.driver.JProgressBarIndeterminateQuery.isIndeterminate;
import static org.assertj.swing.format.Formatting.format;
import static org.assertj.swing.timing.Pause.pause;

import javax.annotation.Nonnull;
import javax.swing.JProgressBar;

import org.assertj.core.description.Description;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiLazyLoadingDescription;
import org.assertj.swing.timing.Condition;
import org.assertj.swing.timing.Timeout;

/**
 * EDT-safe task that waits until the value of a {@code JProgressBar} is equal to the given expected value.
 * 
 * @author Alex Ruiz
 */
final class JProgressBarWaitUntilIsDeterminate {
  @RunsInEDT
  static void waitUntilValueIsDeterminate(final @Nonnull JProgressBar progressBar, final @Nonnull Timeout timeout) {
    pause(new Condition(untilIsDeterminate(progressBar)) {
      @Override
      public boolean test() {
        return !isIndeterminate(progressBar);
      }
    }, timeout);
  }

  private static Description untilIsDeterminate(final @Nonnull JProgressBar progressBar) {
    return new GuiLazyLoadingDescription() {
      @Override
      protected @Nonnull String loadDescription() {
        return format(progressBar) + " to be in determinate mode";
      }
    };
  }

  private JProgressBarWaitUntilIsDeterminate() {
  }
}
