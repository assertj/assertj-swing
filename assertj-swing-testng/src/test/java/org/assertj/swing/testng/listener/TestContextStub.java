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

import java.util.Collection;
import java.util.Date;

import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

/**
 * Understands an <code>{@link ITestContext}</code> stub for testing purposes.
 * 
 * @author Alex Ruiz
 */
public class TestContextStub implements ITestContext {

  private static final long serialVersionUID = 1L;

  private String outputDirectory;

  /** @see org.testng.ITestContext#getAllTestMethods() */
  @Override
  public ITestNGMethod[] getAllTestMethods() {
    return null;
  }

  /** @see org.testng.ITestContext#getEndDate() */
  @Override
  public Date getEndDate() {
    return null;
  }

  /** @see org.testng.ITestContext#getExcludedGroups() */
  @Override
  public String[] getExcludedGroups() {
    return null;
  }

  /** @see org.testng.ITestContext#getExcludedMethods() */
  @Override
  public Collection<ITestNGMethod> getExcludedMethods() {
    return null;
  }

  /** @see org.testng.ITestContext#getFailedButWithinSuccessPercentageTests() */
  @Override
  public IResultMap getFailedButWithinSuccessPercentageTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getFailedConfigurations() */
  @Override
  public IResultMap getFailedConfigurations() {
    return null;
  }

  /** @see org.testng.ITestContext#getFailedTests() */
  @Override
  public IResultMap getFailedTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getHost() */
  @Override
  public String getHost() {
    return null;
  }

  /** @see org.testng.ITestContext#getIncludedGroups() */
  @Override
  public String[] getIncludedGroups() {
    return null;
  }

  /** @see org.testng.ITestContext#getName() */
  @Override
  public String getName() {
    return null;
  }

  /** @see org.testng.ITestContext#getOutputDirectory() */
  @Override
  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  /** @see org.testng.ITestContext#getPassedConfigurations() */
  @Override
  public IResultMap getPassedConfigurations() {
    return null;
  }

  /** @see org.testng.ITestContext#getPassedTests() */
  @Override
  public IResultMap getPassedTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getSkippedConfigurations() */
  @Override
  public IResultMap getSkippedConfigurations() {
    return null;
  }

  /** @see org.testng.ITestContext#getSkippedTests() */
  @Override
  public IResultMap getSkippedTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getStartDate() */
  @Override
  public Date getStartDate() {
    return null;
  }

  /** @see org.testng.ITestContext#getSuite() */
  @Override
  public ISuite getSuite() {
    return null;
  }

  /** @see org.testng.ITestContext#getAttribute(java.lang.String) */
  @Override
  public Object getAttribute(String arg0) {
    return null;
  }

  /** @see org.testng.ITestContext#setAttribute(java.lang.String, java.lang.Object) */
  @Override
  public void setAttribute(String arg0, Object arg1) {
  }
}
