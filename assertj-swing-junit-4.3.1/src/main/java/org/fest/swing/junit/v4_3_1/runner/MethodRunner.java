/*
 * Created on Mar 16, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.v4_3_1.runner;

import org.fest.swing.junit.runner.FailureScreenshotTaker;
import org.junit.internal.runners.TestMethodRunner;
import org.junit.runner.notification.RunNotifier;

class MethodRunner extends TestMethodRunner {

  private final TestInfo testInfo;
  private final FailureScreenshotTaker screenshotTaker;

  public MethodRunner(TestInfo testInfo, RunNotifier notifier, FailureScreenshotTaker screenshotTaker) {
    super(testInfo.test(), testInfo.method(), notifier, testInfo.description());
    this.testInfo = testInfo;
    this.screenshotTaker = screenshotTaker;
  }

  @Override
  protected void addFailure(Throwable e) {
    if (testInfo.isGUITest()) saveScreenshot();
    super.addFailure(e);
  }

  private void saveScreenshot() {
    screenshotTaker.saveScreenshot(testInfo.screenshotFileName());
  }
}