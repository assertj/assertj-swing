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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Arrays.array;
import static org.fest.util.Files.newTemporaryFile;
import static org.fest.util.Files.newTemporaryFolder;

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#selectFiles(javax.swing.JFileChooser, java.io.File[])}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_selectFiles_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_throw_error_if_JFileChooser_is_disabled() {
    try {
      driver.selectFiles(fileChooser, array(fakeFile()));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test
  public void should_throw_error_when_selecting_files_and_JFileChooser_cannot_handle_multiple_selection() {
    disableMultipleSelection();
    showWindow();
    try {
      driver.selectFiles(fileChooser, array(new File("Fake1"), new File("Fake2")));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("Expecting file chooser").contains("to handle multiple selection");
    }
  }

  @RunsInEDT
  private void disableMultipleSelection() {
    setMultipleSelectionEnabled(fileChooser, false);
    robot.waitForIdle();
  }

  @Test
  public void should_throw_error_when_selecing_folders_while_JFileChooser_can_only_select_files() {
    enableMultipleSelection();
    TemporaryFolderAndFile folderAndFile = new TemporaryFolderAndFile();
    makeFileChooserSelectDirectoriesOnly();
    showWindow();
    try {
      driver.selectFiles(fileChooser, folderAndFile.contents());
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open directories");
    } finally {
      folderAndFile.delete();
    }
  }

  @Test
  public void should_throw_error_when_selecting_files_while_JFileChooser_can_only_select_folders() {
    enableMultipleSelection();
    TemporaryFolderAndFile folderAndFile = new TemporaryFolderAndFile();
    makeFileChooserSelectFilesOnly();
    showWindow();
    try {
      driver.selectFiles(fileChooser, folderAndFile.contents());
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open files");
    } finally {
      folderAndFile.delete();
    }
  }

  @Test
  public void should_select_single_given_file_when_JFileChooser_cannot_handle_multiple_selection() {
    disableMultipleSelection();
    File temporaryFile = newTemporaryFile();
    showWindow();
    try {
      driver.selectFiles(fileChooser, array(temporaryFile));
      File[] selectedFiles = selectedFilesIn(fileChooser);
      assertThat(selectedFiles).containsOnly(temporaryFile);
    } finally {
      temporaryFile.delete();
    }
  }

  @Test
  public void should_select_files() {
    // TODO(Alex): Test in Windows
    enableMultipleSelection();
    TemporaryFolderAndFile folderAndFile = new TemporaryFolderAndFile();
    makeFileChooserSelectFilesAndDirectories();
    showWindow();
    try {
      driver.selectFiles(fileChooser, folderAndFile.contents());
      File[] selectedFiles = selectedFilesIn(fileChooser);
      assertThat(selectedFiles).containsOnly(folderAndFile.folder, folderAndFile.file);
    } finally {
      folderAndFile.delete();
    }
  }

  @RunsInEDT
  private void enableMultipleSelection() {
    setMultipleSelectionEnabled(fileChooser, true);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void setMultipleSelectionEnabled(final JFileChooser fileChooser, final boolean b) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        fileChooser.setMultiSelectionEnabled(b);
      }
    });
  }

  @RunsInEDT
  private static File[] selectedFilesIn(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File[]>() {
      @Override
      protected File[] executeInEDT() {
        return fileChooser.getSelectedFiles();
      }
    });
  }

  private static class TemporaryFolderAndFile {
    final File folder;
    final File file;

    TemporaryFolderAndFile() {
      folder = newTemporaryFolder();
      file = newTemporaryFile();
    }

    File[] contents() {
      return array(folder, file);
    }

    void delete() {
      folder.delete();
      file.delete();
    }
  }
}
