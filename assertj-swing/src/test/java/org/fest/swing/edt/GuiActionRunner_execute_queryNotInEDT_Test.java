/*
 * Created on Oct 17, 2008
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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.test.core.MethodInvocations;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link GuiActionRunner#execute(GuiQuery)}.
 *
 * @author Alex Ruiz
 */
public class GuiActionRunner_execute_queryNotInEDT_Test extends SequentialEDTSafeTestCase {
  private boolean executeInEDT;

  @Override
  protected final void onSetUp() {
    executeInEDT = GuiActionRunner.executeInEDT();
    GuiActionRunner.executeInEDT(true);
  }

  @Override
  protected void onTearDown() {
    GuiActionRunner.executeInEDT(executeInEDT);
  }

  @Test
  public void should_execute_query() {
    String valueToReturnWhenExecuted = "Hello";
    TestGuiQuery query = new TestGuiQuery(valueToReturnWhenExecuted);
    GuiActionRunner.executeInEDT(false);
    String result = GuiActionRunner.execute(query);
    assertThat(result).isSameAs(valueToReturnWhenExecuted);
    assertThat(query.wasExecutedInEDT()).isFalse();
    query.requireInvoked("executeInEDT");
  }

  @Test
  public void should_wrap_any_thrown_Exception() {
    TestGuiQuery query = mock(TestGuiQuery.class);
    RuntimeException error = expectedError();
    when(query.executeInEDT()).thenThrow(error);
    try {
      GuiActionRunner.executeInEDT(false);
      GuiActionRunner.execute(query);
      failWhenExpectingException();
    } catch (UnexpectedException e) {
      assertThat(e.getCause()).isSameAs(error);
    }
  }

  private RuntimeException expectedError() {
    return new RuntimeException("Thrown on purpose");
  }

  private static class TestGuiQuery extends GuiQuery<String> {
    private final MethodInvocations methodInvocations = new MethodInvocations();
    private final String valueToReturnWhenExecuted;

    TestGuiQuery(String valueToReturnWhenExecuted) {
      this.valueToReturnWhenExecuted = valueToReturnWhenExecuted;
    }

    @Override
    protected String executeInEDT() {
      methodInvocations.invoked("executeInEDT");
      return valueToReturnWhenExecuted;
    }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
