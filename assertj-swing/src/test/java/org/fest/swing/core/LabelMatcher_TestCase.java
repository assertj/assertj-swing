/*
 * Created on Jan 10, 2008
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
package org.fest.swing.core;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Tests for {@link LabelMatcher}.
 *
 * @author Alex Ruiz
 */
public abstract class LabelMatcher_TestCase extends SequentialEDTSafeTestCase {
  static final String LABEL_TEXT = "Hello";

  MyWindow window;

  @Override
  protected final void onSetUp() {
    window = MyWindow.createNew(getClass());
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  protected static class MyWindow extends TestWindow {
    final JLabel buttonLabel = new JLabel(LABEL_TEXT);
    final JButton button = new JButton("A Button");
    final JLabel label = new JLabel(LABEL_TEXT);

    @RunsInEDT
    static MyWindow createNew(final Class<?> testClass) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(testClass);
        }
      });
    }

    private MyWindow(Class<?> testClass) {
      super(testClass);
      addComponents(buttonLabel, button, label);
      buttonLabel.setLabelFor(button);
    }
  }
}
