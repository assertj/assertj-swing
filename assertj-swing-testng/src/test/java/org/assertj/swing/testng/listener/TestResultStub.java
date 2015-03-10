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

import org.testng.ITestResult;

/**
 * Understands an <code>{@link ITestResult}</code> stub for testing purposes.
 * 
 * @author Alex Ruiz
 */
public class TestResultStub implements ITestResult {

  private static final long serialVersionUID = 1L;

  private final ClassStub testClass = new ClassStub();
  private final TestNGMethodStub method = new TestNGMethodStub();

  private Object[] parameters;

  /** @see org.testng.ITestResult#getEndMillis() */
  @Override
  public long getEndMillis() {
    return 0;
  }

  /** @see org.testng.ITestResult#getHost() */
  @Override
  public String getHost() {
    return null;
  }

  /** @see org.testng.ITestResult#getMethod() */
  @Override
  public TestNGMethodStub getMethod() {
    return method;
  }

  /** @see org.testng.ITestResult#getName() */
  @Override
  public String getName() {
    return null;
  }

  /** @see org.testng.ITestResult#getParameters() */
  @Override
  public Object[] getParameters() {
    return parameters;
  }

  /** @see org.testng.ITestResult#getStartMillis() */
  @Override
  public long getStartMillis() {
    return 0;
  }

  /** @see org.testng.ITestResult#getStatus() */
  @Override
  public int getStatus() {
    return 0;
  }

  /** @see org.testng.ITestResult#getTestClass() */
  @Override
  public ClassStub getTestClass() {
    return testClass;
  }

  /** @see org.testng.ITestResult#getThrowable() */
  @Override
  public Throwable getThrowable() {
    return null;
  }

  /** @see org.testng.ITestResult#isSuccess() */
  @Override
  public boolean isSuccess() {
    return false;
  }

  /** @see org.testng.ITestResult#setEndMillis(long) */
  @Override
  public void setEndMillis(long millis) {
  }

  /** @see org.testng.ITestResult#setParameters(java.lang.Object[]) */
  @Override
  public void setParameters(Object[] newParameters) {
    parameters = newParameters;
  }

  /** @see org.testng.ITestResult#setStatus(int) */
  @Override
  public void setStatus(int status) {
  }

  /** @see org.testng.ITestResult#setThrowable(java.lang.Throwable) */
  @Override
  public void setThrowable(Throwable throwable) {
  }

}
