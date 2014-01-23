/*
 * Created on Mar 19, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.junit.ant.CommonConstants.UNKNOWN;

import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 * Understands utility methods related to JUnit tests.
 *
 * @author Alex Ruiz
 */
final class Tests {

  private static final String JUNIT4_TEST_CASE_FACADE_TYPE = "junit.framework.JUnit4TestCaseFacade";

  private static Method testCaseName = nameMethodIn(TestCase.class);

  static String testMethodNameFrom(Test test) {
    if (test == null) return UNKNOWN;
    if (isJUnit4TestCaseFacade(test)) return trimClassNameFromMethodName(test.toString());
    if (test instanceof TestCase && testCaseName != null) return invokeNameMethod(testCaseName, test);
    return invokeNameMethod(nameMethodIn(test.getClass()), test);
  }

  private static boolean isJUnit4TestCaseFacade(Test test) {
    return isJunit4TestCaseFacade(test);
  }

  // Self-describing as of JUnit 4 (#38811). But trim "(ClassName)".
  private static String trimClassNameFromMethodName(String name) {
    if (!name.endsWith(")")) return name;
    return name.substring(0, name.lastIndexOf('('));
  }

  private static Method nameMethodIn(Class<?> type) {
    return methodInType(type, "getName", "name");
  }

  private static Method methodInType(Class<?> type, String name, String alternativeName) {
    Method m = methodInType(type, name);
    if (m != null) return m;
    return methodInType(type, alternativeName);
  }

  private static Method methodInType(Class<?> type, String name) {
    try {
      return type.getMethod(name, new Class[0]);
    } catch (Exception e) {
      return null;
    }
  }

  private static String invokeNameMethod(Method m, Object target) {
    if (m == null || m.getReturnType() != String.class) return UNKNOWN;
    try {
      return (String) m.invoke(target, new Object[0]);
    } catch (Exception e) {
      return UNKNOWN;
    }
  }

  static String testClassNameFrom(Test test) {
    String className = classNameOf(test);
    if (className.endsWith("VmExitErrorTest")) return classNameFromVmExitErrorTest(test);
    if (isJunit4TestCaseFacade(test)) return testClassNameFromJUnit4TestCaseFacade(test);
    return className;
  }

  private static String classNameFromVmExitErrorTest(Test test) {
    try {
      return method("getClassName").withReturnType(String.class).in(test).invoke();
    } catch (Exception e) {
      return UNKNOWN;
    }
  }

  private static boolean isJunit4TestCaseFacade(Test test) {
    return classNameOf(test).equals(JUNIT4_TEST_CASE_FACADE_TYPE);
  }

  // JUnit 4 wraps solo tests this way. We can extract the original test name with a little hack.
  private static String testClassNameFromJUnit4TestCaseFacade(Test test) {
    String name = test.toString();
    int i = name.lastIndexOf('(');
    if (i == -1) return classNameOf(test);
    if (!name.endsWith(")")) return classNameOf(test);
    return name.substring(i + 1, name.length() - 1);
  }

  private static String classNameOf(Test test) {
    return test.getClass().getName();
  }
}
