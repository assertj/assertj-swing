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
package org.assertj.swing.test.query;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Returns whether a {@code JInternalFrame} is currently iconified. This query is executed in the event dispatch thread
 * (EDT).
 * 
 * @author Christian Rösch
 */
public final class JInternalFrameIconifiedQuery {
  /**
   * @param internalFrame the given {@code JInternalFrame}.
   * @return <code>true</code> if the given {@link JInternalFrame} is currently iconified.
   */
  @RunsInEDT
  public static @Nonnull Boolean isIconified(final @Nonnull JInternalFrame internalFrame) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return internalFrame.isIcon();
      }
    });
    return checkNotNull(result);
  }

  private JInternalFrameIconifiedQuery() {
  }
}