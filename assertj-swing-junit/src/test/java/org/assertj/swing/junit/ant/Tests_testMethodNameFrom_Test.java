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
package org.assertj.swing.junit.ant;

import static org.assertj.core.api.Assertions.assertThat;
import junit.framework.JUnit4TestCaseFacade;
import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Test;

/**
 * Tests for <code>{@link Tests}</code>.
 * 
 * @author Alex Ruiz
 */
public class Tests_testMethodNameFrom_Test extends Tests_TestCase {

  @Test
  public void should_Return_Word_Unknown_If_Test_Is_Null() {
    assertThat(Tests.testMethodNameFrom(null)).isEqualTo("unknown");
  }

  @Test
  public void should_Return_ToString_If_Test_Is_JUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello");
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("hello");
  }

  @Test
  public void should_Return_ToString_Without_Class_Name_If_Test_Is_JUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello(world)");
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("hello");
  }

  @Test
  public void should_Return_Name_If_Test_Is_TestCase() {
    TestCase test = new TestCase("Leia") {
    };
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("Leia");
  }

  @Test
  public void should_Return_Name_By_Calling_Name_Method() {
    MyTestWithName test = new MyTestWithName();
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("name");
  }

  private static class MyTestWithName implements junit.framework.Test {
    @SuppressWarnings("unused")
    public String name() {
      return "name";
    }

    @Override
    public int countTestCases() {
      return 0;
    }

    @Override
    public void run(TestResult result) {
    }
  }

  @Test
  public void should_Return_Name_By_Calling_GetName_Method() {
    MyTestWithGetName test = new MyTestWithGetName();
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("name");
  }

  private static class MyTestWithGetName implements junit.framework.Test {
    @SuppressWarnings("unused")
    public String getName() {
      return "name";
    }

    @Override
    public int countTestCases() {
      return 0;
    }

    @Override
    public void run(TestResult result) {
    }
  }

  @Test
  public void should_Return_Word_Unknown_If_Test_Does_Not_Have_Name_Or_GetName_Methods() {
    junit.framework.Test test = new junit.framework.Test() {
      @Override
      public int countTestCases() {
        return 0;
      }

      @Override
      public void run(TestResult result) {
      }
    };
    assertThat(Tests.testMethodNameFrom(test)).isEqualTo("unknown");
  }
}
