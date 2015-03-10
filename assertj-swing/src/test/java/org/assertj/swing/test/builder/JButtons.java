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
package org.assertj.swing.test.builder;

import static org.assertj.core.util.Arrays.isNullOrEmpty;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Factory of {@link JButton}s.
 * 
 * @author Alex Ruiz
 */
public final class JButtons {
  private JButtons() {
  }

  public static JButtonFactory button() {
    return new JButtonFactory();
  }

  public static class JButtonFactory {
    ActionListener[] actionListeners;
    boolean enabled;
    String name;
    String text;

    public JButtonFactory withActionListeners(ActionListener... newActionListeners) {
      actionListeners = newActionListeners;
      return this;
    }

    public JButtonFactory enabled(boolean isEnabled) {
      enabled = isEnabled;
      return this;
    }

    public JButtonFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JButtonFactory withText(String newText) {
      text = newText;
      return this;
    }

    @RunsInEDT
    public JButton createNew() {
      return execute(new GuiQuery<JButton>() {
        @Override
        protected JButton executeInEDT() {
          JButton button = new JButton();
          if (!isNullOrEmpty(actionListeners)) {
            for (ActionListener l : actionListeners) {
              button.addActionListener(l);
            }
          }
          button.setEnabled(enabled);
          button.setName(name);
          button.setText(text);
          return button;
        }
      });
    }
  }
}