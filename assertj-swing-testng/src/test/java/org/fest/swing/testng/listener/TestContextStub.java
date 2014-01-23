/*
 * Created on May 19, 2007
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

import java.util.Collection;
import java.util.Date;

import org.testng.*;

/**
 * Understands an <code>{@link ITestContext}</code> stub for testing purposes.
 *
 * @author Alex Ruiz
 */
public class TestContextStub implements ITestContext {

  private static final long serialVersionUID = 1L;

  private String outputDirectory;

  /** @see org.testng.ITestContext#getAllTestMethods() */
  public ITestNGMethod[] getAllTestMethods() {
    return null;
  }

  /** @see org.testng.ITestContext#getEndDate() */
  public Date getEndDate() {
    return null;
  }

  /** @see org.testng.ITestContext#getExcludedGroups() */
  public String[] getExcludedGroups() {
    return null;
  }

  /** @see org.testng.ITestContext#getExcludedMethods() */
  public Collection<ITestNGMethod> getExcludedMethods() {
    return null;
  }

  /** @see org.testng.ITestContext#getFailedButWithinSuccessPercentageTests() */
  public IResultMap getFailedButWithinSuccessPercentageTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getFailedConfigurations() */
  public IResultMap getFailedConfigurations() {
    return null;
  }

  /** @see org.testng.ITestContext#getFailedTests() */
  public IResultMap getFailedTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getHost() */
  public String getHost() {
    return null;
  }

  /** @see org.testng.ITestContext#getIncludedGroups() */
  public String[] getIncludedGroups() {
    return null;
  }

  /** @see org.testng.ITestContext#getName() */
  public String getName() {
    return null;
  }

  /** @see org.testng.ITestContext#getOutputDirectory() */
  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  /** @see org.testng.ITestContext#getPassedConfigurations() */
  public IResultMap getPassedConfigurations() {
    return null;
  }

  /** @see org.testng.ITestContext#getPassedTests() */
  public IResultMap getPassedTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getSkippedConfigurations() */
  public IResultMap getSkippedConfigurations() {
    return null;
  }

  /** @see org.testng.ITestContext#getSkippedTests() */
  public IResultMap getSkippedTests() {
    return null;
  }

  /** @see org.testng.ITestContext#getStartDate() */
  public Date getStartDate() {
    return null;
  }

  /** @see org.testng.ITestContext#getSuite() */
  public ISuite getSuite() {
    return null;
  }

  /** @see org.testng.ITestContext#getAttribute(java.lang.String) */
  public Object getAttribute(String arg0) {
    return null;
  }

  /** @see org.testng.ITestContext#setAttribute(java.lang.String, java.lang.Object) */
  public void setAttribute(String arg0, Object arg1) {}
}
