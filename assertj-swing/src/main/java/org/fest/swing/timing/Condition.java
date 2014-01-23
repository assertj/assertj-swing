/*
 * Created on Apr 14, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.timing;

import static org.fest.util.Strings.concat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.assertions.BasicDescription;
import org.fest.assertions.Description;

/**
 * A condition to verify, usually used in the method {@link Pause#pause(Condition)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class Condition {
  protected static final String EMPTY_TEXT = "";

  private final Description description;

  /**
   * Creates a new {@link Condition}.
   *
   * @param description describes this condition.
   */
  public Condition(@Nonnull String description) {
    this(new BasicDescription(description));
  }

  /**
   * Creates a new {@link Condition}.
   *
   * @param description describes this condition.
   */
  public Condition(@Nullable Description description) {
    this.description = description;
  }

  /**
   * Checks if the condition has been satisfied.
   *
   * @return {@code true} if the condition has been satisfied, otherwise {@code false}.
   */
  public abstract boolean test();

  /**
   * Returns the {@code String} representation of this condition, which is its description.
   *
   * @return the description of this condition.
   */
  @Override
  public final @Nonnull String toString() {
    String descriptionText = description != null ? description.value() : defaultDescription();
    String addendum = descriptionAddendum();
    return concat(descriptionText, addendum);
  }

  private String defaultDescription() {
    return String.format("condition of type [%s]", getClass().getName());
  }

  /**
   * Returns any text to be added to this condition's description. The default value is an empty {@code String}.
   *
   * @return by default, an empty {@code String}.
   */
  protected @Nonnull String descriptionAddendum() {
    return EMPTY_TEXT;
  }

  /**
   * Notification that this condition has been evaluated. This method is invoked by {@link Pause#pause(Condition)} (and
   * all overloaded methods) when this condition is evaluated (either it was satisfied or it timed-out.) This is a good
   * place to do any necessary resource cleanup.
   */
  protected void done() {}
}
