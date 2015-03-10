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
package org.assertj.swing.testng.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Understands a base class for TestNG listeners.
 * 
 * @author Alex Ruiz
 */
public abstract class AbstractTestListener implements ITestListener {

  /**
   * Invoked after the test class is instantiated and before any configuration method is called.
   * 
   * @param context test context containing all the information for a given test run.
   */
  @Override
  public void onStart(ITestContext context) {
  }

  /**
   * Invoked after all the tests have run and all their Configuration methods have been called.
   * 
   * @param context test context containing all the information for a given test run.
   */
  @Override
  public void onFinish(ITestContext context) {
  }

  /**
   * Invoked each time a method fails but has been annotated with successPercentage and this failure still keeps it
   * within the success percentage requested.
   * 
   * @param result contains information about the run test.
   */
  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
  }

  /**
   * Invoked each time before a test will be invoked. The <code>{@link ITestResult}</code> is only partially filled with
   * the references to class, method, start millis and status.
   * 
   * @param result the partially filled test result.
   */
  @Override
  public void onTestStart(ITestResult result) {
  }

  /**
   * Invoked each time a test succeeds.
   * 
   * @param result contains information about the run test.
   */
  @Override
  public void onTestSuccess(ITestResult result) {
  }

  /**
   * Invoked each time a test fails.
   * 
   * @param result contains information about the run test.
   */
  @Override
  public void onTestFailure(ITestResult result) {
  }

  /**
   * Invoked each time a test is skipped.
   * 
   * @param result contains information about the run test.
   */
  @Override
  public void onTestSkipped(ITestResult result) {
  }
}
