/*
 * Created on Dec 23, 2007
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
package org.fest.swing.format;

import static org.fest.swing.format.SwingIntEnums.SELECTION_MODES;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Strings.quote;

import java.awt.Component;
import java.util.List;

import javax.annotation.Nonnull;
import javax.swing.JList;
import javax.swing.ListModel;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.util.Arrays;

/**
 * Formatter for {@code JList}s.
 * 
 * @author Yvonne Wang
 */
public class JListFormatter extends ComponentFormatterTemplate {
  /**
   * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JList}.
   * 
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code JList}.
   */
  @RunsInCurrentThread
  @Override
  protected @Nonnull String doFormat(@Nonnull Component c) {
    JList list = (JList) c;
    String format = "%s[name=%s, selectedValues=%s, contents=%s, selectionMode=%s, enabled=%b, visible=%b, showing=%b]";
    return String.format(format, list.getClass().getName(), quote(list.getName()),
        Arrays.format(list.getSelectedValues()), Arrays.format(contentsOf(list)),
        SELECTION_MODES.get(list.getSelectionMode()), list.isEnabled(), list.isVisible(), list.isShowing());
  }

  private @Nonnull Object[] contentsOf(JList list) {
    List<Object> contents = newArrayList();
    ListModel model = list.getModel();
    int size = model.getSize();
    for (int i = 0; i < size; i++) {
      contents.add(model.getElementAt(i));
    }
    return contents.toArray();
  }

  /**
   * @return {@code JList.class}.
   */
  @Override
  public @Nonnull Class<? extends Component> targetType() {
    return JList.class;
  }
}
