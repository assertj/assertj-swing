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
package org.assertj.swing.junit.runner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link Formatter#testNameFrom(Class, Method)}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Formatter_testNameFrom_Test {

  private Class<?> type;
  private String typeName;

  @Before
  public void setUp() {
    type = TestClass.class;
    typeName = type.getName();
  }

  @Test
  public void should_Format_Method_With_No_Parameters() throws Exception {
    Method m = type.getDeclaredMethod("methodWithNoParameters");
    assertThat(Formatter.testNameFrom(type, m)).isEqualTo(concat(typeName, ".methodWithNoParameters"));
  }

  @Test
  public void should_Format_Method_With_One_Parameter() throws Exception {
    Method m = type.getDeclaredMethod("methodWithOneParameter", float.class);
    assertThat(Formatter.testNameFrom(type, m)).isEqualTo(concat(typeName, ".methodWithOneParameter(float)"));
  }

  @Test
  public void should_Format_Method_With_Parameters() throws Exception {
    Method m = type.getDeclaredMethod("methodWithParameters", String.class, int.class);
    assertThat(Formatter.testNameFrom(type, m)).isEqualTo(
        concat(typeName, ".methodWithParameters(java.lang.String, int)"));
  }

  static class TestClass {
    void methodWithNoParameters() {
    }

    void methodWithOneParameter(float first) {
      if (first > 0)
        return;
    }

    void methodWithParameters(String first, int second) {
      if (first != null && second > 0)
        return;
    }
  }
}
