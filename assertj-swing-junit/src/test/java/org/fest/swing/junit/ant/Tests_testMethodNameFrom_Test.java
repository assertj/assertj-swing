/*
 * Created on Mar 19, 2009
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

import static org.fest.assertions.Assertions.assertThat;
import junit.framework.*;

import org.junit.Test;

/**
 * Tests for <code>{@link Tests}</code>.
 *
 * @author Alex Ruiz
 */
public class Tests_testMethodNameFrom_Test extends Tests_TestCase {

  @Test
  public void should_return_word_unknown_if_test_is_null() {
    assertThat(Tests.testMethodNameFrom(null)).isEqualTo("unknown");
  }

  @Test
  public void should_return_toString_if_test_is_JUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello");
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("hello");
  }

  @Test
  public void should_return_toString_without_class_name_if_test_is_JUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello(world)");
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("hello");
  }

  @Test
  public void should_return_name_if_test_is_TestCase() {
    TestCase test = new TestCase("Leia") {};
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("Leia");
  }

  @Test
  public void should_return_name_by_calling_name_method() {
    MyTestWithName test = new MyTestWithName();
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("name");
  }

  private static class MyTestWithName implements junit.framework.Test {
    @SuppressWarnings("unused")
    public String name() { return "name"; }
    public int countTestCases() { return 0; }
    public void run(TestResult result) {}
  }

  @Test
  public void should_return_name_by_calling_getName_method() {
    MyTestWithGetName test = new MyTestWithGetName();
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("name");
  }

  private static class MyTestWithGetName implements junit.framework.Test {
    @SuppressWarnings("unused")
    public String getName() { return "name"; }
    public int countTestCases() { return 0; }
    public void run(TestResult result) {}
  }

  @Test
  public void should_return_word_unknown_if_test_does_not_have_name_or_getName_methods() {
    junit.framework.Test test = new junit.framework.Test() {
      public int countTestCases() { return 0; }
      public void run(TestResult result) {}
    };
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("unknown");
  }
}
