/*
 * Created on Feb 26, 2008
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
package org.fest.swing.driver;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.FILES_AND_DIRECTORIES;
import static javax.swing.JFileChooser.FILES_ONLY;
import static javax.swing.JFileChooser.OPEN_DIALOG;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.task.ComponentSetEnabledTask.disable;
import static org.fest.util.Files.temporaryFolder;

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link JFileChooserDriver}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class JFileChooserDriver_TestCase extends RobotBasedTestCase {
  JFileChooserDriver driver;
  MyWindow window;
  JFileChooser fileChooser;

  @Override
  protected final void onSetUp() {
    driver = new JFileChooserDriver(robot);
    window = MyWindow.createNew(getClass());
    fileChooser = window.fileChooser;
  }

  final void showWindow() {
    robot.showWindow(window);
  }

  final File fakeFile() {
    return new File("Fake");
  }

  @RunsInEDT
  final void makeFileChooserSelectDirectoriesOnly() {
    setFileSelectionMode(DIRECTORIES_ONLY);
  }

  @RunsInEDT
  final void makeFileChooserSelectFilesAndDirectories() {
    setFileSelectionMode(fileChooser, FILES_AND_DIRECTORIES);
  }

  @RunsInEDT
  final void makeFileChooserSelectFilesOnly() {
    setFileSelectionMode(FILES_ONLY);
  }

  private void setFileSelectionMode(int filesOnly) {
    setFileSelectionMode(fileChooser, filesOnly);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setFileSelectionMode(final JFileChooser fileChooser, final int mode) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        fileChooser.setFileSelectionMode(mode);
      }
    });
  }

  @RunsInEDT
  final void disableFileChooser() {
    disable(fileChooser);
    robot.waitForIdle();
  }

  static class MyWindow extends TestWindow {
    final JFileChooser fileChooser = new JFileChooser();

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
      fileChooser.setCurrentDirectory(temporaryFolder());
      fileChooser.setDialogType(OPEN_DIALOG);
      addComponents(fileChooser);
    }
  }
}
