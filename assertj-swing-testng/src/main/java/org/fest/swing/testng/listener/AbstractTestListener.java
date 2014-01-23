/*
 * Created on May 6, 2007
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
 * Copyright @2007-2009  the original author or authors.
 */
package org.fest.swing.testng.listener;

import org.testng.*;

/**
 * Understands a base class for TestNG listeners.
 *
 * @author Alex Ruiz
 */
public abstract class AbstractTestListener implements ITestListener {

  /**
   * Invoked after the test class is instantiated and before any configuration method is called.
   * @param context test context containing all the information for a given test run.
   */
  public void onStart(ITestContext context) {}

  /**
   * Invoked after all the tests have run and all their Configuration methods have been called.
   * @param context test context containing all the information for a given test run.
   */
  public void onFinish(ITestContext context) {}

  /**
   * Invoked each time a method fails but has been annotated with successPercentage and this failure still keeps it
   * within the success percentage requested.
   * @param result contains information about the run test.
   */
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  /**
   * Invoked each time before a test will be invoked. The <code>{@link ITestResult}</code> is only partially filled
   * with the references to class, method, start millis and status.
   * @param result the partially filled test result.
   */
  public void onTestStart(ITestResult result) {}

  /**
   * Invoked each time a test succeeds.
   * @param result contains information about the run test.
   */
  public void onTestSuccess(ITestResult result) {}

  /**
   * Invoked each time a test fails.
   * @param result contains information about the run test.
   */
  public void onTestFailure(ITestResult result) {}

  /**
   * Invoked each time a test is skipped.
   * @param result contains information about the run test.
   */
  public void onTestSkipped(ITestResult result) {}
}
