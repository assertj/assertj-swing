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
package org.assertj.swing.format;

import static org.assertj.core.util.Strings.quote;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JTable;

/**
 * Formatter for {@code JTable}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableFormatter extends ComponentFormatterTemplate {
  /**
   * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JTable}.
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code JTable}.
   */
  @Override
  protected @Nonnull String doFormat(@Nonnull Component c) {
    JTable table = (JTable) c;
    String format = "%s[name=%s, rowCount=%d, columnCount=%d, enabled=%b, visible=%b, showing=%b]";
    return String.format(format, getRealClassName(c), quote(table.getName()), table.getRowCount(),
                         table.getColumnCount(), table.isEnabled(), table.isVisible(), table.isShowing());
  }

  /**
   * @return {@code JTable.class}.
   */
  @Override
  public @Nonnull Class<? extends Component> targetType() {
    return JTable.class;
  }
}
