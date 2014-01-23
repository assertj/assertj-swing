/*
 * Created on Mar 11, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.swing.junit.v4_5.runner;

import static org.fest.swing.annotation.GUITestFinder.isGUITest;
import static org.fest.swing.junit.runner.Formatter.testNameFrom;

import java.lang.reflect.Method;

import org.fest.swing.junit.runner.FailureScreenshotTaker;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Understands execution of a test method. This statement will save take and save the screenshot of the desktop in case
 * of a failure.
 *
 * @author Alex Ruiz
 */
class MethodInvoker extends Statement {

  private final FrameworkMethod testMethod;
  private final Object target;
  private final FailureScreenshotTaker screenshotTaker;

  MethodInvoker(FrameworkMethod testMethod, Object target, FailureScreenshotTaker screenshotTaker) {
    this.testMethod= testMethod;
    this.target= target;
    this.screenshotTaker = screenshotTaker;
  }

  @Override public void evaluate() throws Throwable {
    try {
      testMethod.invokeExplosively(target);
    } catch (Throwable t) {
      takeScreenshot();
      throw t;
    }
  }

  private void takeScreenshot() {
    Method realMethod = testMethod.getMethod();
    final Class<?> testClass = realMethod.getDeclaringClass();
    if (!(isGUITest(testClass, realMethod))) return;
    screenshotTaker.saveScreenshot(testNameFrom(testClass, realMethod));
  }
}
