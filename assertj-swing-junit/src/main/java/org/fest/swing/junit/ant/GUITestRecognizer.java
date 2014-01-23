/*
 * Created on Mar 20, 2009
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
package org.fest.swing.junit.ant;

import java.lang.reflect.Method;

import org.fest.swing.annotation.GUITestFinder;

/**
 * Understands utility methods related to GUI tests. A GUI test is a class or method annotated with
 * <code>{@link org.fest.swing.annotation.GUITest}</code>.
 *
 * @author Alex Ruiz
 */
class GUITestRecognizer {

  // assumes the test method does not take parameters.
  boolean isGUITest(String className, String methodName) {
    try {
      Class<?> testClass = Class.forName(className);
      Method testMethod = testClass.getDeclaredMethod(methodName, new Class<?>[0]);
      return GUITestFinder.isGUITest(testClass, testMethod);
    } catch (Exception e) {
      return false;
    }
  }
}
