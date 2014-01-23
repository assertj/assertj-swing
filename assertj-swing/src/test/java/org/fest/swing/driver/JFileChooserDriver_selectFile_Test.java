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
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Files.newTemporaryFile;
import static org.fest.util.Files.newTemporaryFolder;

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#selectFile(JFileChooser, File)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_selectFile_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_select_file() {
    showWindow();
    File temporaryFile = newTemporaryFile();
    try {
      driver.selectFile(fileChooser, temporaryFile);
      File selectedFile = selectedFileIn(fileChooser);
      assertThat(selectedFile).isSameAs(temporaryFile);
    } finally {
      temporaryFile.delete();
    }
  }

  @RunsInEDT
  private static File selectedFileIn(final JFileChooser fileChooser) {
    return execute(new GuiQuery<File>() {
      @Override
      protected File executeInEDT() {
        return fileChooser.getSelectedFile();
      }
    });
  }

  @Test
  public void should_throw_error_if_JFileChooser_is_disabled() {
    disableFileChooser();
    try {
      driver.selectFile(fileChooser, fakeFile());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JFileChooser_is_not_showing_on_the_screen() {
    try {
      driver.selectFile(fileChooser, fakeFile());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  @Test
  public void should_throw_error_when_selecting_file_while_JFileChooser_can_only_select_folders() {
    makeFileChooserSelectDirectoriesOnly();
    showWindow();
    try {
      driver.selectFile(fileChooser, fakeFile());
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open directories");
    }
  }

  @Test
  public void should_throw_error_when_selecing_folder_while_JFileChooser_can_only_select_files() {
    File temporaryFolder = newTemporaryFolder();
    makeFileChooserSelectFilesOnly();
    showWindow();
    try {
      driver.selectFile(fileChooser, temporaryFolder);
      failWhenExpectingException();
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("the file chooser can only open files");
    } finally {
      temporaryFolder.delete();
    }
  }
}
