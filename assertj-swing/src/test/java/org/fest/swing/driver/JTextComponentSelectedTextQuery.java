/*
 * Created on Nov 22, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns the selected text of a {@code JTextComponent}. This query is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JTextComponentSelectedTextQuery {
  @RunsInEDT
  static @Nullable
  String selectedTextOf(final @Nonnull JTextComponent textBox) {
    return execute(new GuiQuery<String>() {
      @Override
      protected @Nullable
      String executeInEDT() {
        return textBox.getSelectedText();
      }
    });
  }

  private JTextComponentSelectedTextQuery() {}
}