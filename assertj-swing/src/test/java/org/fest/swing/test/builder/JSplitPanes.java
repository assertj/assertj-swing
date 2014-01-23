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

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.swing.JSplitPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JSplitPane}s.
 * 
 * @author Alex Ruiz
 */
public final class JSplitPanes {
  private JSplitPanes() {}

  public static JSplitPaneFactory splitPane() {
    return new JSplitPaneFactory();
  }

  public static class JSplitPaneFactory {
    int orientation = HORIZONTAL_SPLIT;
    String name;
    Component leftComponent;
    Component rightComponent;

    public JSplitPaneFactory withOrientation(int newOrientation) {
      orientation = newOrientation;
      return this;
    }

    public JSplitPaneFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JSplitPaneFactory withLeftComponent(Component newLeftComponent) {
      leftComponent = newLeftComponent;
      return this;
    }

    public JSplitPaneFactory withRightComponent(Component newRightComponent) {
      rightComponent = newRightComponent;
      return this;
    }

    @RunsInEDT
    public JSplitPane createNew() {
      return execute(new GuiQuery<JSplitPane>() {
        @Override
        protected JSplitPane executeInEDT() {
          JSplitPane splitPane = new JSplitPane();
          splitPane.setOrientation(orientation);
          splitPane.setName(name);
          if (leftComponent != null) {
            splitPane.setLeftComponent(leftComponent);
          }
          if (rightComponent != null) {
            splitPane.setRightComponent(rightComponent);
          }
          return splitPane;
        }
      });
    }
  }
}