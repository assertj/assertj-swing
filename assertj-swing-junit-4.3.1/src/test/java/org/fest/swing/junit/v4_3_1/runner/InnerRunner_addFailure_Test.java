/*
 * Created on Apr 24, 2009
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
package org.fest.swing.junit.v4_3_1.runner;

import static org.easymock.EasyMock.*;
import static org.fest.util.Objects.areEqual;
import static org.junit.runner.Description.createSuiteDescription;

import org.easymock.IArgumentMatcher;
import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

/**
 * Tests for <code>{@link InnerRunner#addFailure(Throwable)}</code>.
 *
 * @author Alex Ruiz
 */
public class InnerRunner_addFailure_Test extends InnerRunner_TestCase {

  private Description description;
  private Exception cause;
  private Failure failure;

  @Override void onSetUp() {
    description = createSuiteDescription("Hello");
    cause = new Exception();
    failure = new Failure(description, cause);
  }

  @Test
  public void shouldNotifyWhenAddingFailure() {
    new EasyMockTemplate(delegate, notifier) {
      protected void expectations() {
        expect(delegate.getDescription()).andReturn(description);
        reportMatcher(new FailureMatcher(failure));
        notifier.fireTestFailure(failure);
        expectLastCall().once();
      }

      protected void codeToTest() {
        runner.addFailure(cause);
      }
    }.run();
  }

  private static class FailureMatcher implements IArgumentMatcher {
    private final Failure failure;

    FailureMatcher(Failure failure) {
      this.failure = failure;
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append(failure);
    }

    public boolean matches(Object argument) {
      if (!(argument instanceof Failure)) return false;
      Failure other = (Failure)argument;
      if (!areEqual(failure.getDescription(), other.getDescription())) return false;
      return areEqual(failure.getException(), other.getException());
    }
  }
}
