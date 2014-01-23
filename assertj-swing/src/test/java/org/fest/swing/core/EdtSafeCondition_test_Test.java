/*
 * Created on Mar 7, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.core;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.fest.swing.exception.EdtViolationException;
import org.fest.swing.test.data.BooleanProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link EdtSafeCondition#test()}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class EdtSafeCondition_test_Test {
  private final boolean conditionSatisfied;

  @Parameters
  public static @Nonnull Collection<Object[]> booleans() {
    return newArrayList(BooleanProvider.booleans());
  }

  public EdtSafeCondition_test_Test(boolean conditionSatisfied) {
    this.conditionSatisfied = conditionSatisfied;
  }

  private EdtSafeCondition condition;

  @Before
  public void setUp() {
    condition = new EdtSafeCondition("Hello World!") {
      @Override
      protected boolean testInEDT() {
        if (!isEventDispatchThread()) {
          throw new EdtViolationException("Method 'testInEDT' should be executed in the EDT");
        }
        return conditionSatisfied;
      }
    };
  }

  @Test
  public void should_test_condition_in_EDT() {
    assertThat(condition.test()).isEqualTo(conditionSatisfied);
  }
}
