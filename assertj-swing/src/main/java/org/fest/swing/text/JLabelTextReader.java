/*
 * Created on Jul 29, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.text;

import javax.annotation.Nonnull;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Reads the text displayed in a {@code JLabel}.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.3
 */
public class JLabelTextReader extends TextReader<JLabel> {
  /**
   * @return {@code JLabel.class}.
   */
  @Override
  public @Nonnull Class<JLabel> supportedComponent() {
    return JLabel.class;
  }

  /**
   * Indicates whether the given {@code JLabel} displays the given text.
   * 
   * @param label the given {@code JLabel}.
   * @param text the given text.
   * @return {@code true} if the given {@code JLabel} displays the given text; {@code false} otherwise.
   */
  @RunsInCurrentThread
  @Override
  protected boolean checkContainsText(@Nonnull JLabel label, @Nonnull String text) {
    String labelText = label.getText();
    if (labelText == null) {
      return false;
    }
    return labelText.contains(text);
  }
}
