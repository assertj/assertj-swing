/*
 * Created on Mar 13, 2009
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
package org.fest.swing.junit.v4_5.runner;

import org.fest.swing.junit.runner.FailureScreenshotTaker;
import org.fest.swing.junit.runner.ImageFolderCreator;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.*;

/**
 * Understands a JUnit 4.5 test runner that takes a screenshot of a failed GUI test.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GUITestRunner extends BlockJUnit4ClassRunner {

  private final FailureScreenshotTaker screenshotTaker;

  /**
   * Creates a new <code>{@link GUITestRunner}</code>.
   * @param testClass the class containing the tests to run.
   * @throws InitializationError if something goes wrong when creating this runner.
   */
  public GUITestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    screenshotTaker = new FailureScreenshotTaker(new ImageFolderCreator().createImageFolder());
  }

  /**
   * Returns a <code>{@link Statement}</code> that invokes {@code method} on {@code test}. The created statement will
   * take and save the screenshot of the desktop in case of a failure.
   */
  @Override   protected Statement methodInvoker(FrameworkMethod method, Object test) {
    return new MethodInvoker(method, test, screenshotTaker);
  }
}
