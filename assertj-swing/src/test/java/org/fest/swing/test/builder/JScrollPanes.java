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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JScrollPane}s.
 * 
 * @author Alex Ruiz
 */
public final class JScrollPanes {
  private JScrollPanes() {}

  public static JScrollPaneFactory scrollPane() {
    return new JScrollPaneFactory();
  }

  public static class JScrollPaneFactory {
    String name;
    Dimension preferredSize;
    Component view;

    public JScrollPaneFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JScrollPaneFactory withPreferredSize(Dimension newPreferredSize) {
      preferredSize = newPreferredSize;
      return this;
    }

    public JScrollPaneFactory withView(Component newView) {
      view = newView;
      return this;
    }

    @RunsInEDT
    public JScrollPane createNew() {
      return execute(new GuiQuery<JScrollPane>() {
        @Override
        protected JScrollPane executeInEDT() {
          JScrollPane scrollPane = view != null ? new JScrollPane(view) : new JScrollPane();
          scrollPane.setName(name);
          if (preferredSize != null) {
            scrollPane.setPreferredSize(preferredSize);
          }
          scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
          scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
          return scrollPane;
        }
      });
    }
  }
}