/*
 * Created on Apr 5, 2008
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
package org.fest.swing.driver;

import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiLazyLoadingDescription;
import org.fest.swing.timing.Condition;

/**
 * Verifies that an AWT or Swing {@code Component} is enabled.
 * 
 * @author Yvonne Wang
 */
class ComponentEnabledCondition extends Condition {
  private Component c;

  static @Nonnull ComponentEnabledCondition untilIsEnabled(@Nonnull Component c) {
    return new ComponentEnabledCondition(c);
  }

  private ComponentEnabledCondition(@Nonnull Component c) {
    super(description(c));
    this.c = c;
  }

  private static @Nonnull Description description(final @Nonnull Component c) {
    return new GuiLazyLoadingDescription() {
      @Override
      protected @Nonnull String loadDescription() {
        return concat(format(c), " to be enabled");
      }
    };
  }

  @RunsInEDT
  @Override
  public boolean test() {
    return isEnabled(checkNotNull(c));
  }

  /** ${@inheritDoc} */
  @Override
  protected void done() {
    c = null;
  }
}