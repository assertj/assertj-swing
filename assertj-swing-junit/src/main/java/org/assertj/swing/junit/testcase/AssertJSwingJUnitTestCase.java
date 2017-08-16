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
package org.assertj.swing.junit.testcase;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Understands a template for test cases that use AssertJ-Swing and JUnit. This template installs a
 * <code>{@link FailOnThreadViolationRepaintManager}</code> to catch violations of Swing thread rules and manages both
 * creation and clean up of a <code>{@link org.assertj.swing.core.Robot}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class AssertJSwingJUnitTestCase extends AssertJSwingTestCaseTemplate {

  /**
   * Installs a <code>{@link FailOnThreadViolationRepaintManager}</code> to catch violations of Swing threading rules.
   */
  @BeforeClass
  public static final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }

  /**
   * Sets up this test's fixture, starting from creation of a new <code>{@link org.assertj.swing.core.Robot}</code>.
   *
   * @see #setUpRobot()
   * @see #onSetUp()
   * @throws Exception when the set up of the test fails which results in the complete test fails
   */
  @Before
  public final void setUp() throws Exception {
    setUpRobot();
    onSetUp();
  }

  /**
   * Subclasses need set up their own test fixture in this method. This method is called <strong>after</strong>
   * executing <code>{@link #setUp()}</code>.
   *
   * @throws Exception when the set up of the test fails which results in the complete test fails
   */
  protected abstract void onSetUp() throws Exception;

  /**
   * Removes the <code>{@link FailOnThreadViolationRepaintManager}</code> again to allow EDT violating and EDT safe
   * tests in the same suite.
   */
  @AfterClass
  public static final void tearDownOnce() {
    FailOnThreadViolationRepaintManager.uninstall();
  }

  /**
   * Cleans up any resources used in this test. After calling <code>{@link #onTearDown()}</code>, this method cleans up
   * resources used by this test's <code>{@link org.assertj.swing.core.Robot}</code>.
   *
   * @see #cleanUp()
   * @see #onTearDown()
   * @throws Exception when the tear down of the test fails which results in that the test will not be cleaned up properly
   */
  @After
  public final void tearDown() throws Exception {
    try {
      onTearDown();
    } finally {
      cleanUp();
    }
  }

  /**
   * Subclasses need to clean up resources in this method. This method is called <strong>before</strong> executing
   * <code>{@link #tearDown()}</code>.
   * 
   * @throws Exception when the tear down of the test fails which results in that the test will not be cleaned up properly
   */
  protected void onTearDown() throws Exception {}
}
