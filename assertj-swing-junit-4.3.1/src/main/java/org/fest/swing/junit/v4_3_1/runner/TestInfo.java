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

import static org.fest.swing.junit.runner.Formatter.testNameFrom;
import static org.junit.runner.Description.createTestDescription;

import java.lang.reflect.Method;

import org.fest.swing.annotation.GUITestFinder;
import org.junit.runner.Description;

/**
 * Understands the necessary information FEST-Swing JUnit extension needs to take a screenshot of a failed GUI test.
 *
 * @author Alex Ruiz
 */
class TestInfo {

  private final Object test;
  private final Class<?> type;
  private final Method method;

  TestInfo(Object test, Class<?> type, Method method) {
    this.test = test;
    this.type = type;
    this.method = method;
  }

  Object test() { return test; }

  Class<?> type() { return type; }

  Method method() { return method; }

  Description description() {
    return createTestDescription(type, method.getName());
  }

  boolean isGUITest() {
    return GUITestFinder.isGUITest(type, method);
  }

  String screenshotFileName() {
    return testNameFrom(type, method);
  }
}
