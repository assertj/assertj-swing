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
package org.assertj.swing.driver;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.swing.JTabbedPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Returns the titles of the tabs in a {@code JTabbedPane}. This query is executed in the event dispatch thread (EDT).
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTabbedPaneTabTitlesQuery {
  @RunsInEDT
  static @Nonnull String[] tabTitlesOf(final @Nonnull JTabbedPane tabbedPane) {
    String[] result = execute(new GuiQuery<String[]>() {
      @Override
      protected @Nonnull String[] executeInEDT() {
        int count = tabbedPane.getTabCount();
        String[] titles = new String[count];
        for (int i = 0; i < count; i++) {
          titles[i] = tabbedPane.getTitleAt(i);
        }
        return titles;
      }
    });
    return checkNotNull(result);
  }

  private JTabbedPaneTabTitlesQuery() {
  }
}