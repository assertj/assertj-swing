/*
 * Created on Dec 2, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.test.builder;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JProgressBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JProgressBar}s.
 * 
 * @author Alex Ruiz
 */
public final class JProgressBars {
  public static JProgressBarFactory progressBar() {
    return new JProgressBarFactory();
  }

  public static class JProgressBarFactory {
    boolean indeterminate;
    int maximum = 100;
    int minimum;
    String name;
    boolean showingText;
    String text;
    int value;

    public JProgressBarFactory indeterminate() {
      indeterminate = true;
      return this;
    }

    public JProgressBarFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JProgressBarFactory withMaximum(int newMaximum) {
      maximum = newMaximum;
      return this;
    }

    public JProgressBarFactory withMinimum(int newMinimum) {
      minimum = newMinimum;
      return this;
    }

    public JProgressBarFactory withText(String newText) {
      text = newText;
      return this;
    }

    public JProgressBarFactory withValue(int newValue) {
      value = newValue;
      return this;
    }

    public JProgressBarFactory showingText() {
      showingText = true;
      return this;
    }

    @RunsInEDT
    public JProgressBar createNew() {
      return execute(new GuiQuery<JProgressBar>() {
        @Override
        protected JProgressBar executeInEDT() {
          JProgressBar progressBar = new JProgressBar();
          progressBar.setIndeterminate(indeterminate);
          progressBar.setMaximum(maximum);
          progressBar.setMinimum(minimum);
          progressBar.setName(name);
          progressBar.setString(text);
          progressBar.setStringPainted(showingText);
          progressBar.setValue(value);
          return progressBar;
        }
      });
    }
  }

  private JProgressBars() {}
}