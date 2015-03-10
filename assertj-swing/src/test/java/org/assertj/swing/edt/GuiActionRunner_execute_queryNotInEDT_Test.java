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
import static org.assertj.swing.test.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.assertj.swing.exception.UnexpectedException;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.MethodInvocations;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link GuiActionRunner#execute(GuiQuery)}.
 * 
 * @author Alex Ruiz
 */
public class GuiActionRunner_execute_queryNotInEDT_Test extends SequentialEDTSafeTestCase {
  @Rule
  public ExpectedException thrown = none();

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
  public void should_Execute_Query() {
    String valueToReturnWhenExecuted = "Hello";
    TestGuiQuery query = new TestGuiQuery(valueToReturnWhenExecuted);
    GuiActionRunner.executeInEDT(false);
    String result = GuiActionRunner.execute(query);
    assertThat(result).isSameAs(valueToReturnWhenExecuted);
    assertThat(query.wasExecutedInEDT()).isFalse();
    query.requireInvoked("executeInEDT");
  }

  @Test
  public void should_Wrap_Any_Thrown_Exception() {
    TestGuiQuery query = mock(TestGuiQuery.class);
    RuntimeException error = expectedError();
    when(query.executeInEDT()).thenThrow(error);
    GuiActionRunner.executeInEDT(false);
    thrown.expectWrappingException(UnexpectedException.class, error);
    GuiActionRunner.execute(query);
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
