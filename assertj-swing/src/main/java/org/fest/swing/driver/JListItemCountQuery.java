/*
 * Created on Nov 20, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JList;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns the number of items in a given {@code JList}. This query is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
@RunsInEDT
final class JListItemCountQuery {
  static int itemCountIn(final @Nonnull JList list) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected @Nullable Integer executeInEDT() {
        return list.getModel().getSize();
      }
    });
    return checkNotNull(result);
  }

  private JListItemCountQuery() {}
}
