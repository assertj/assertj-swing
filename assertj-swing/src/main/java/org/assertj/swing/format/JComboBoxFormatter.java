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
import java.util.List;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;

import org.assertj.core.util.Lists;
import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.util.Arrays;

/**
 * Formatter for {@code JComboBox}es.
 *
 * @author Yvonne Wang
 */
public class JComboBoxFormatter extends ComponentFormatterTemplate {
  /**
   * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JComboBox}.
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code JComboBox}.
   */
  @RunsInCurrentThread
  @Override
  protected @Nonnull String doFormat(@Nonnull Component c) {
    JComboBox<?> comboBox = (JComboBox<?>) c;
    String format = "%s[name=%s, selectedItem=%s, contents=%s, editable=%b, enabled=%b, visible=%b, showing=%b]";
    return String.format(format, getRealClassName(c), quote(comboBox.getName()),
                         quote(comboBox.getSelectedItem()), Arrays.format(contentsOf(comboBox)), comboBox.isEditable(),
                         comboBox.isEnabled(), comboBox.isVisible(), comboBox.isShowing());
  }

  @RunsInCurrentThread
  private @Nonnull Object[] contentsOf(@Nonnull JComboBox<?> comboBox) {
    List<Object> contents = Lists.newArrayList();
    int count = comboBox.getItemCount();
    for (int i = 0; i < count; i++) {
      contents.add(comboBox.getItemAt(i));
    }
    return contents.toArray();
  }

  /**
   * @return {@code JComboBox.class}.
   */
  @Override
  public @Nonnull Class<? extends Component> targetType() {
    return JComboBox.class;
  }
}
