/*
 * Created on Dec 12, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.edt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Strings.concat;

import org.junit.Test;

/**
 * Tests for <a href="http://code.google.com/p/fest/issues/detail?id=247" target="_blank">Bug 247</a>.
 * 
 * @author Alex Ruiz
 */
public class Bug247_NotEnoughInfoInFailureInEDT_Test {
  private static String TEST_NAME = Bug247_NotEnoughInfoInFailureInEDT_Test.class.getName();

  @Test
  public void should_show_method_call_in_current_thread_when_failing_in_EDT() {
    boolean testClassInStackTrace = false;
    try {
      execute(new GuiTask() {
        @Override
        protected void executeInEDT() {
          throw new RuntimeException("Thrown on purpose");
        }
      });
      failWhenExpectingException();
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
