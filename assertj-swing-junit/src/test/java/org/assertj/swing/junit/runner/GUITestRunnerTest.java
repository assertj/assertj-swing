/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.junit.runner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GUITestRunnerTest {

  private String javaAwtHeadlessBefore;

  @Before
  public void saveProperty() {
    javaAwtHeadlessBefore = System.getProperty("java.awt.headless");
  }

  @After
  public void resetProperty() {
    if (javaAwtHeadlessBefore != null) {
      System.setProperty("java.awt.headless", javaAwtHeadlessBefore);
    }
  }

  @SuppressWarnings("unused")
  @Test
  public void should_Throw_No_Error_When_In_Headless_Environment() throws Exception {
    System.setProperty("java.awt.headless", "true");

    new GUITestRunner(ClassUnderTest.class);
  }

  public static class ClassUnderTest {
    @Test
    public void test() {
      // do nothing
    }
  }
}