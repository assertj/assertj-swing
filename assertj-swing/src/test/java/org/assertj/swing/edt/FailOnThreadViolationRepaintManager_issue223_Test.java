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
 * Copyright 2018 the original author or authors.
 */
package org.assertj.swing.edt;

import org.junit.Test;

import javax.swing.*;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.fail;

/**
 * See https://github.com/joel-costigliola/assertj-swing/issues/223
 */
public class FailOnThreadViolationRepaintManager_issue223_Test {
  /**
   * This test fails with the original code, and passes with the fixed code
   */
  @Test
  public void calling_repaint_with_SwingWorker_should_not_be_EDT_Access_Violation() {
    FailOnThreadViolationRepaintManager.install();

    Runnable edtTask = () -> {
      JLabel label = new JLabel("Test");
      SwingWorker notEDTTask = new SwingWorker() {
        @Override
        protected Object doInBackground() {
          // it is OK to call repaint() from here
          // because repaint() is thread-safe
          label.repaint();
          return null;
        }
      };
      notEDTTask.execute();
      try {
        // blocks until the doInBackground() method returns
        notEDTTask.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        fail();
      }
    };
    try {
      EventQueue.invokeAndWait(edtTask);
    } catch (InterruptedException | InvocationTargetException e) {
      e.printStackTrace();
      fail();
    }
  }
}
