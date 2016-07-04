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

import static javax.swing.JFileChooser.CUSTOM_DIALOG;
import static javax.swing.JFileChooser.OPEN_DIALOG;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import static org.assertj.core.util.Strings.quote;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JFileChooser;

import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.swing.annotation.RunsInCurrentThread;

/**
 * Formatter for {@code JFileChooser}s.
 *
 * @author Yvonne Wang
 */
public class JFileChooserFormatter extends ComponentFormatterTemplate {
  private static final IntEnum DIALOG_TYPES = new IntEnum();
  static {
    DIALOG_TYPES.put(OPEN_DIALOG, "OPEN_DIALOG").put(SAVE_DIALOG, "SAVE_DIALOG").put(CUSTOM_DIALOG, "CUSTOM_DIALOG");
  }

  /**
   * Returns the {@code String} representation of the given {@code Component}, which should be a {@code JFileChooser}.
   *
   * @param c the given {@code Component}.
   * @return the {@code String} representation of the given {@code JFileChooser}.
   */
  @RunsInCurrentThread
  @Override
  protected @Nonnull String doFormat(@Nonnull Component c) {
    JFileChooser fileChooser = (JFileChooser) c;
    String format = "%s[name=%s, dialogTitle=%s, dialogType=%s, currentDirectory=%s, enabled=%b, visible=%b, showing=%b";
    return String.format(format, getRealClassName(c), quote(fileChooser.getName()),
                         quote(fileChooser.getDialogTitle()), DIALOG_TYPES.get(fileChooser.getDialogType()),
                         new StandardRepresentation().toStringOf(fileChooser.getCurrentDirectory()),
                         fileChooser.isEnabled(),
                         fileChooser.isVisible(), fileChooser.isShowing());
  }

  /**
   * @return {@code JFileChooser.class}.
   */
  @Override
  public @Nonnull Class<? extends Component> targetType() {
    return JFileChooser.class;
  }
}
