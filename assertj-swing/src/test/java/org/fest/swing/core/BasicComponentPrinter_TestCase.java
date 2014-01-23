/*
 * Created on Dec 22, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JButton;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.io.PrintStreamStub;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link BasicComponentPrinter}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class BasicComponentPrinter_TestCase extends SequentialEDTSafeTestCase {
  BasicComponentPrinter printer;
  MyWindow windowOne;
  MyWindow windowTwo;
  PrintStreamStub out;

  @Override
  protected final void onSetUp() {
    printer = (BasicComponentPrinter) BasicComponentPrinter.printerWithNewAwtHierarchy();
    windowOne = MyWindow.createNew(getClass());
    windowOne.buttonName("button1");
    windowTwo = MyWindow.createNew(getClass());
    windowTwo.buttonName("button2");
    out = new PrintStreamStub();
  }

  @Override
  protected final void onTearDown() {
    windowOne.destroy();
    windowTwo.destroy();
  }

  static class MyWindow extends TestWindow {
    final JButton button = new JButton("A button");

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
      addComponents(button);
    }

    void buttonName(final String buttonName) {
      execute(new GuiTask() {
        @Override
        protected void executeInEDT() {
          button.setName(buttonName);
        }
      });
    }
  }
}
