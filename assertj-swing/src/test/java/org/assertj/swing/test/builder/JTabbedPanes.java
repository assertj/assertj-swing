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
import static org.assertj.swing.test.builder.JTabbedPanes.Tab.tab;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Factory of {@code JTabbedPane}s.
 * 
 * @author Alex Ruiz
 */
public final class JTabbedPanes {
  private JTabbedPanes() {
  }

  public static JTabbedPaneFactory tabbedPane() {
    return new JTabbedPaneFactory();
  }

  public static class JTabbedPaneFactory {
    String name;
    Tab[] tabs;
    int selectedIndex = -1;

    public JTabbedPaneFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JTabbedPaneFactory withTabs(String... tabTitles) {
      int tabCount = tabTitles.length;
      Tab[] newTabs = new Tab[tabCount];
      for (int i = 0; i < tabCount; i++) {
        newTabs[i] = tab(tabTitles[i]);
      }
      return withTabs(newTabs);
    }

    public JTabbedPaneFactory withTabs(Tab... newTabs) {
      tabs = newTabs;
      return this;
    }

    public JTabbedPaneFactory withSelection(int tabIndex) {
      selectedIndex = tabIndex;
      return this;
    }

    @RunsInEDT
    public JTabbedPane createNew() {
      return execute(new GuiQuery<JTabbedPane>() {
        @Override
        protected JTabbedPane executeInEDT() {
          JTabbedPane tabbedPane = new JTabbedPane();
          tabbedPane.setName(name);
          if (!isNullOrEmpty(tabs)) {
            for (Tab tab : tabs) {
              tabbedPane.addTab(tab.title, tab.component);
            }
          }
          tabbedPane.setSelectedIndex(selectedIndex);
          return tabbedPane;
        }
      });
    }
  }

  public static class Tab {
    final String title;
    final Component component;

    @RunsInEDT
    public static Tab tab(String title) {
      return new Tab(title, createPanel());
    }

    @RunsInEDT
    private static JPanel createPanel() {
      return execute(() -> new JPanel());
    }

    public static Tab tab(String title, Component component) {
      return new Tab(title, component);
    }

    private Tab(String title, Component component) {
      this.title = title;
      this.component = component;
    }
  }
}