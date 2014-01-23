/*
 * Created on Aug 8, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Files.newTemporaryFile;
import static org.fest.util.Files.temporaryFolder;

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserSelectFileTask#setSelectedFile(JFileChooser, File)}.
 * 
 * @author Yvonne Wang
 */
public class JFileChooserSelectFileTask_validateAndSelectFile_Test extends RobotBasedTestCase {
  private JFileChooser fileChooser;
  private File fileToSelect;

  @Override
  protected void onSetUp() {
    fileToSelect = newTemporaryFile();
    MyWindow window = MyWindow.createNew();
    robot.showWindow(window);
    fileChooser = window.fileChooser;
  }

  @Override
  protected void onTearDown() {
    fileToSelect.delete();
  }

  @Test
  public void should_select_file() {
    JFileChooserSelectFileTask.setSelectedFile(fileChooser, fileToSelect);
    robot.waitForIdle();
    assertThat(selectedFile()).isEqualTo(fileToSelect);
  }

  @RunsInEDT
  private File selectedFile() {
    return selectedFileOf(fileChooser);
  }

  @RunsInEDT
  private static File selectedFileOf(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      @Override
      protected File executeInEDT() {
        return fileChooser.getSelectedFile();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JFileChooser fileChooser = new JFileChooser(temporaryFolder());

    private MyWindow() {
      super(JFileChooserSelectFileTask.class);
      add(fileChooser);
    }
  }
}
