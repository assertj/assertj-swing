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
package org.assertj.swing.edt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=247" target="_blank">Bug 247</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug247_NotEnoughInfoInFailureInEDT_Test {
  private static String TEST_NAME = Bug247_NotEnoughInfoInFailureInEDT_Test.class.getName();

  @Test
  public void should_Show_Method_Call_In_Current_Thread_When_Failing_In_EDT() {
    boolean testClassInStackTrace = false;
    try {
      execute(new GuiTask() {
        @Override
        protected void executeInEDT() {
          throw new RuntimeException("Thrown on purpose");
        }
      });
      fail("Expecting exception");
    } catch (RuntimeException e) {
      StackTraceElement[] stackTrace = e.getStackTrace();
      StackTraceElement first = stackTrace[0];
      assertThat(first.getClassName()).isEqualTo(concat(TEST_NAME, "$1"));
      assertThat(first.getMethodName()).isEqualTo("executeInEDT");
      String expected = Bug247_NotEnoughInfoInFailureInEDT_Test.class.getName();
      for (StackTraceElement element : e.getStackTrace()) {
        if (!expected.equals(element.getClassName())) {
          continue;
        }
        testClassInStackTrace = true;
        break;
      }
    }
    assertThat(testClassInStackTrace).as("test class in stack trace").isTrue();
  }
}
