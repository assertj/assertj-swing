/*
 * Created on Aug 28, 2008
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
package org.fest.swing.test.builder;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JToggleButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JToggleButton}s.
 * 
 * @author Alex Ruiz
 */
public final class JToggleButtons {
  private JToggleButtons() {}

  public static JToggleButtonFactory toggleButton() {
    return new JToggleButtonFactory();
  }

  public static class JToggleButtonFactory {
    String name;
    boolean selected;
    String text;

    public JToggleButtonFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JToggleButtonFactory selected(boolean isSelected) {
      selected = isSelected;
      return this;
    }

    public JToggleButtonFactory withText(String newText) {
      text = newText;
      return this;
    }

    @RunsInEDT
    public JToggleButton createNew() {
      return execute(new GuiQuery<JToggleButton>() {
        @Override
        protected JToggleButton executeInEDT() {
          JToggleButton toggleButton = new JToggleButton();
          toggleButton.setName(name);
          toggleButton.setSelected(selected);
          toggleButton.setText(text);
          return toggleButton;
        }
      });
    }
  }
}
