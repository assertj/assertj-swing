/*
 * Created on Mar 16, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.v4_3_1.runner;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reportMatcher;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.util.Objects.areEqual;

import java.lang.reflect.Method;

import org.easymock.IArgumentMatcher;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.runner.FailureScreenshotTaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * Tests for <code>{@link MethodRunner#addFailure(Throwable)}</code>.
 *
 * @author Alex Ruiz
 */
public class MethodRunner_addFailure_Test {

  private Class<?> testClass;
  private RunNotifier notifier;
  private FailureScreenshotTaker screenshotTaker;
  private TestInfo testInfo;
  private MethodRunner runner;
  private Exception exception;
  private Failure expectedFailure;

  @Before public void setUp() {
    testClass = SomeGuiTestFake.class;
    notifier = createMock(RunNotifier.class);
    screenshotTaker = createMock(FailureScreenshotTaker.class);
    exception = new Exception();
  }

  @Test
  public void should_take_screenshot_if_test_fails_and_is_GUI_test() throws Exception {
    setUpUsing("failedGUITest");
    new EasyMockTemplate(notifier, screenshotTaker) {
      protected void expectations() {
        screenshotTaker.saveScreenshot(testInfo.screenshotFileName());
        expectLastCall().once();
        reportMatcherForFailure();
        expectNotifierToFireTestFailure();
      }

      protected void codeToTest() {
        runner.addFailure(exception);
      }
    }.run();
  }

  @Test
  public void should_not_take_screenshot_if_test_fails_and_is_not_GUI_test() throws Exception {
    setUpUsing("failedNonGUITest");
    new EasyMockTemplate(notifier, screenshotTaker) {
      protected void expectations() {
        reportMatcherForFailure();
        expectNotifierToFireTestFailure();
      }

      protected void codeToTest() {
        runner.addFailure(exception);
      }
    }.run();
  }

  private void setUpUsing(String methodName) throws Exception {
    testInfo = new TestInfo(new Object(), testClass, method(methodName));
    runner = new MethodRunner(testInfo, notifier, screenshotTaker);
    expectedFailure = new Failure(testInfo.description(), exception);
  }

  private Method method(String name) throws Exception {
    return testClass.getDeclaredMethod(name);
  }

  private void reportMatcherForFailure() {
    reportMatcher(new FailureMatcher(expectedFailure));
  }

  private void expectNotifierToFireTestFailure() {
    notifier.fireTestFailure(expectedFailure);
    expectLastCall().once();
  }

  private static class FailureMatcher implements IArgumentMatcher {
    private final Failure expected;

    FailureMatcher(Failure expected) {
      this.expected = expected;
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append(expected.getClass().getName()).append("[");
      buffer.append("description=").append(expected.getDescription()).append(",");
      buffer.append("exception=").append(expected.getException().getMessage()).append("]");
    }

    public boolean matches(Object argument) {
      if (!(argument instanceof Failure)) return false;
      Failure actual = (Failure) argument;
      if (!areEqual(expected.getDescription(), actual.getDescription())) return false;
      return areEqual(expected.getException(), actual.getException());
    }
  }
}
