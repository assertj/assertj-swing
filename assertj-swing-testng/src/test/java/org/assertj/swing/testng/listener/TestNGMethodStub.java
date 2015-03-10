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

import java.lang.reflect.Method;

import org.testng.IClass;
import org.testng.IRetryAnalyzer;
import org.testng.ITestClass;
import org.testng.ITestNGMethod;

/**
 * Understands an <code>{@link ITestNGMethod}</code> stub for testing purposes.
 * 
 * @author Alex Ruiz
 */
public class TestNGMethodStub implements ITestNGMethod {

  private static final long serialVersionUID = 1L;

  private String methodName;
  private Method method;

  /** @see org.testng.ITestNGMethod#addMethodDependedUpon(java.lang.String) */
  @Override
  public void addMethodDependedUpon(String newMethodName) {
  }

  /** @see org.testng.ITestNGMethod#canRunFromClass(org.testng.IClass) */
  @Override
  public boolean canRunFromClass(IClass testClass) {
    return false;
  }

  /** @see org.testng.ITestNGMethod#getAfterGroups() */
  @Override
  public String[] getAfterGroups() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getBeforeGroups() */
  @Override
  public String[] getBeforeGroups() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getCurrentInvocationCount() */
  @Override
  public int getCurrentInvocationCount() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#getDate() */
  @Override
  public long getDate() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#getDescription() */
  @Override
  public String getDescription() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getGroups() */
  @Override
  public String[] getGroups() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getGroupsDependedUpon() */
  @Override
  public String[] getGroupsDependedUpon() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getId() */
  @Override
  public String getId() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getInstanceHashCodes() */
  @Override
  public long[] getInstanceHashCodes() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getInstances() */
  @Override
  public Object[] getInstances() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getInvocationCount() */
  @Override
  public int getInvocationCount() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#getMethod() */
  @Override
  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  /** @see org.testng.ITestNGMethod#getMethodName() */
  @Override
  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  /** @see org.testng.ITestNGMethod#getMethodsDependedUpon() */
  @Override
  public String[] getMethodsDependedUpon() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getMissingGroup() */
  @Override
  public String getMissingGroup() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getParameterInvocationCount() */
  @Override
  public int getParameterInvocationCount() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#getRealClass() */
  @Override
  public Class<?> getRealClass() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getSuccessPercentage() */
  @Override
  public int getSuccessPercentage() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#getTestClass() */
  @Override
  public ITestClass getTestClass() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getThreadPoolSize() */
  @Override
  public int getThreadPoolSize() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#getTimeOut() */
  @Override
  public long getTimeOut() {
    return 0;
  }

  /** @see org.testng.ITestNGMethod#incrementCurrentInvocationCount() */
  @Override
  public void incrementCurrentInvocationCount() {
  }

  /** @see org.testng.ITestNGMethod#isAfterClassConfiguration() */
  @Override
  public boolean isAfterClassConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isAfterGroupsConfiguration() */
  @Override
  public boolean isAfterGroupsConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isAfterMethodConfiguration() */
  @Override
  public boolean isAfterMethodConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isAfterSuiteConfiguration() */
  @Override
  public boolean isAfterSuiteConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isAfterTestConfiguration() */
  @Override
  public boolean isAfterTestConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isAlwaysRun() */
  @Override
  public boolean isAlwaysRun() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isBeforeClassConfiguration() */
  @Override
  public boolean isBeforeClassConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isBeforeGroupsConfiguration() */
  @Override
  public boolean isBeforeGroupsConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isBeforeMethodConfiguration() */
  @Override
  public boolean isBeforeMethodConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isBeforeSuiteConfiguration() */
  @Override
  public boolean isBeforeSuiteConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isBeforeTestConfiguration() */
  @Override
  public boolean isBeforeTestConfiguration() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#isTest() */
  @Override
  public boolean isTest() {
    return false;
  }

  /** @see org.testng.ITestNGMethod#setDate(long) */
  @Override
  public void setDate(long date) {
  }

  /** @see org.testng.ITestNGMethod#setId(java.lang.String) */
  @Override
  public void setId(String arg0) {
  }

  /** @see org.testng.ITestNGMethod#setInvocationCount(int) */
  @Override
  public void setInvocationCount(int arg0) {
  }

  /** @see org.testng.ITestNGMethod#setMissingGroup(java.lang.String) */
  @Override
  public void setMissingGroup(String group) {
  }

  /** @see org.testng.ITestNGMethod#setParameterInvocationCount(int) */
  @Override
  public void setParameterInvocationCount(int arg0) {
  }

  /** @see org.testng.ITestNGMethod#setTestClass(org.testng.ITestClass) */
  @Override
  public void setTestClass(ITestClass cls) {
  }

  /** @see org.testng.ITestNGMethod#setThreadPoolSize(int) */
  @Override
  public void setThreadPoolSize(int arg0) {
  }

  /** @see java.lang.Comparable#compareTo(java.lang.Object) */
  @Override
  public int compareTo(Object o) {
    return 0;
  }

  @Override
  public ITestNGMethod clone() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#getRetryAnalyzer() */
  @Override
  public IRetryAnalyzer getRetryAnalyzer() {
    return null;
  }

  /** @see org.testng.ITestNGMethod#setRetryAnalyzer(org.testng.IRetryAnalyzer) */
  @Override
  public void setRetryAnalyzer(IRetryAnalyzer arg0) {
  }

  /** @see org.testng.ITestNGMethod#setSkipFailedInvocations(boolean) */
  @Override
  public void setSkipFailedInvocations(boolean arg0) {
  }

  /** @see org.testng.ITestNGMethod#skipFailedInvocations() */
  @Override
  public boolean skipFailedInvocations() {
    return false;
  }
}
