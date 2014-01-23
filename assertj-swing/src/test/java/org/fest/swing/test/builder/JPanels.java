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

import java.awt.Color;

import javax.swing.JPanel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JPanel}s.
 * 
 * @author Alex Ruiz
 */
public final class JPanels {
  private JPanels() {}

  public static JPanelFactory panel() {
    return new JPanelFactory();
  }

  public static class JPanelFactory {
    Color background;
    String name;

    public JPanelFactory withBackground(Color newBackground) {
      background = newBackground;
      return this;
    }

    public JPanelFactory withName(String newName) {
      name = newName;
      return this;
    }

    @RunsInEDT
    public JPanel createNew() {
      return execute(new GuiQuery<JPanel>() {
        @Override
        protected JPanel executeInEDT() {
          JPanel panel = new JPanel();
          if (background != null) {
            panel.setBackground(background);
          }
          panel.setName(name);
          return panel;
        }
      });
    }
  }
}