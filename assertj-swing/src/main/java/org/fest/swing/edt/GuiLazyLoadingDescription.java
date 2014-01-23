/*
 * Created on Oct 27, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * {@code Description} that loads its text lazily, in the event dispatch thread (EDT.)
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class GuiLazyLoadingDescription implements Description {
  /**
   * Executes {@link #loadDescription()} in the event dispatch thread (EDT.)
   *
   * @return the text loaded in the event dispatch thread (EDT.)
   */
  @Override
  public final @Nonnull String value() {
    String result = execute(new GuiQuery<String>() {
      @Override
      protected @Nullable String executeInEDT() {
        return loadDescription();
      }
    });
    return checkNotNull(result);
  }

  /**
   * <p>
   * Returns the lazy-loaded text of this description.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @return the lazy-loaded text of this description.
   */
  @RunsInCurrentThread
  protected abstract @Nonnull String loadDescription();
}
