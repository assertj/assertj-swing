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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.timing.Condition;

/**
 * {@link Condition} that is evaluated in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
public abstract class EdtSafeCondition extends Condition {
  /**
   * Creates a new {@link EdtSafeCondition}.
   * 
   * @param description describes this condition.
   */
  public EdtSafeCondition(@Nonnull String description) {
    super(description);
  }

  /**
   * Creates a new {@link EdtSafeCondition}.
   * 
   * @param description describes this condition.
   */
  public EdtSafeCondition(@Nullable Description description) {
    super(description);
  }

  /**
   * Checks if the condition has been satisfied.
   * 
   * @return {@code true} if the condition has been satisfied, otherwise {@code false}.
   */
  @Override
  public final boolean test() {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return testInEDT();
      }
    });
    return checkNotNull(result);
  }

  /**
   * Checks if the condition has been satisfied. This method is guaranteed to be executed in the event dispatch thread
   * (EDT.)
   * 
   * @return {@code true} if the condition has been satisfied, otherwise {@code false}.
   */
  @RunsInEDT
  protected abstract boolean testInEDT();
}
