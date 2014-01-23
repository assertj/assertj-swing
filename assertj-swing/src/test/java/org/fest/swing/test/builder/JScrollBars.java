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

import static java.awt.Adjustable.HORIZONTAL;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JScrollBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JScrollBar}s.
 * 
 * @author Alex Ruiz
 */
public final class JScrollBars {
  private JScrollBars() {}

  public static JScrollBarFactory scrollBar() {
    return new JScrollBarFactory();
  }

  public static class JScrollBarFactory {
    int blockIncrement;
    int orientation = HORIZONTAL;
    int maximum;
    int minimum;
    String name;
    int value;

    public JScrollBarFactory withBlockIncrement(int newBlockIncrement) {
      blockIncrement = newBlockIncrement;
      return this;
    }

    public JScrollBarFactory withOrientation(int newOrientation) {
      orientation = newOrientation;
      return this;
    }

    public JScrollBarFactory withMaximum(int newMaximum) {
      maximum = newMaximum;
      return this;
    }

    public JScrollBarFactory withMinimum(int newMinimum) {
      minimum = newMinimum;
      return this;
    }

    public JScrollBarFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JScrollBarFactory withValue(int newValue) {
      value = newValue;
      return this;
    }

    @RunsInEDT
    public JScrollBar createNew() {
      return execute(new GuiQuery<JScrollBar>() {
        @Override
        protected JScrollBar executeInEDT() {
          JScrollBar scrollBar = new JScrollBar();
          scrollBar.setBlockIncrement(blockIncrement);
          scrollBar.setOrientation(orientation);
          scrollBar.setMaximum(maximum);
          scrollBar.setMinimum(minimum);
          scrollBar.setName(name);
          scrollBar.setValue(value);
          return scrollBar;
        }
      });
    }
  }
}