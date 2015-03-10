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
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.assertj.core.util.Files.newTemporaryFolder;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.io.File;

import javax.swing.JFileChooser;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#selectFile(JFileChooser, File)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_selectFile_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_Select_File() {
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
  public void should_Throw_Error_If_JFileChooser_Is_Disabled() {
    disableFileChooser();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.selectFile(fileChooser, fakeFile());
  }

  @Test
  public void should_Throw_Error_If_JFileChooser_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.selectFile(fileChooser, fakeFile());
  }

  @Test
  public void should_Throw_Error_When_Selecting_File_While_JFileChooser_Can_Only_Select_Folders() {
    makeFileChooserSelectDirectoriesOnly();
    showWindow();
    thrown.expectIllegalArgumentException("the file chooser can only open directories");
    driver.selectFile(fileChooser, fakeFile());
  }

  @Test
  public void should_Throw_Error_When_Selecing_Folder_While_JFileChooser_Can_Only_Select_Files() {
    File temporaryFolder = newTemporaryFolder();
    makeFileChooserSelectFilesOnly();
    showWindow();
    thrown.expectIllegalArgumentException("the file chooser can only open files");
    try {
      driver.selectFile(fileChooser, temporaryFolder);
    } finally {
      temporaryFolder.delete();
    }
  }
}
