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

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.FILES_ONLY;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.format.Formatting.format;

import java.io.File;

import javax.annotation.Nonnull;
import javax.swing.JFileChooser;

import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;

/**
 * Selects a file in a {@code JFileChooser}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JFileChooserSelectFileTask {
  @RunsInEDT
  static void setSelectedFile(final @Nonnull JFileChooser fileChooser, final @Nonnull File file) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(fileChooser);
        checkSelectionMode(fileChooser, file);
        fileChooser.setSelectedFile(file);
      }
    });
  }

  @RunsInEDT
  static void setSelectedFiles(final @Nonnull JFileChooser fileChooser, final @Nonnull File[] files) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(fileChooser);
        if (files.length > 1 && !fileChooser.isMultiSelectionEnabled()) {
          throw new IllegalStateException(concat("Expecting file chooser ", format(fileChooser),
              " to handle multiple selection"));
        }
        for (File file : files) {
          checkSelectionMode(fileChooser, file);
        }
        fileChooser.setSelectedFiles(files);
      }
    });
  }

  @RunsInCurrentThread
  private static void checkSelectionMode(JFileChooser fileChooser, File file) {
    int mode = fileChooser.getFileSelectionMode();
    if (mode == FILES_ONLY && !file.isFile()) {
      throw cannotSelectFile(file, "the file chooser can only open files");
    }
    if (mode == DIRECTORIES_ONLY && !file.isDirectory()) {
      throw cannotSelectFile(file, "the file chooser can only open directories");
    }
  }

  private static IllegalArgumentException cannotSelectFile(File file, String reason) {
    String msg = String.format("Unabled to select file %s: %s", new StandardRepresentation().toStringOf(file), reason);
    return new IllegalArgumentException(msg);
  }

  private JFileChooserSelectFileTask() {
  }
}