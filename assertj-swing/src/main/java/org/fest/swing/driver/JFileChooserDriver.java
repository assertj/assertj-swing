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

import static org.fest.swing.core.matcher.JButtonMatcher.withText;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JFileChooserApproveButtonTextQuery.approveButtonTextFrom;
import static org.fest.swing.driver.JFileChooserCancelButtonTextQuery.cancelButtonText;
import static org.fest.swing.driver.JFileChooserSelectFileTask.setSelectedFile;
import static org.fest.swing.driver.JFileChooserSelectFileTask.setSelectedFiles;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.util.InternalApi;

/**
 * Supports functional testing of {@code JFileChooser}s.
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@InternalApi
public class JFileChooserDriver extends JComponentDriver {
  private static final String APPROVE_BUTTON = "Approve";
  private static final String CANCEL_BUTTON = "Cancel";

  /**
   * Creates a new {@link JFileChooserDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JFileChooserDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Selects the given file in the {@code JFileChooser}.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @param file the file to select.
   * @throws NullPointerException if the given file is {@code null}.
   * @throws IllegalStateException if the {@code JFileChooser} is disabled.
   * @throws IllegalStateException if the {@code JFileChooser} is not showing on the screen.
   * @throws IllegalArgumentException if the {@code JFileChooser} can select directories only and the file to select is
   *           not a directory.
   * @throws IllegalArgumentException if the {@code JFileChooser} cannot select directories and the file to select is a
   *           directory.
   */
  @RunsInEDT
  public void selectFile(@Nonnull JFileChooser fileChooser, @Nonnull File file) {
    setSelectedFile(fileChooser, checkNotNull(file));
  }

  /**
   * Selects the given file in the {@code JFileChooser}.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @param files the files to select.
   * @throws NullPointerException if the given array of files is {@code null}.
   * @throws IllegalArgumentException if the given array of files is empty.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} is disabled.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} is not showing on the screen.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} does not support multiple selection and there
   *           is more than one file to select.
   * @throws IllegalArgumentException if this fixture's {@code JFileChooser} can select directories only and any of the
   *           files to select is not a directory.
   * @throws IllegalArgumentException if this fixture's {@code JFileChooser} cannot select directories and any of the
   *           files to select is a directory.
   */
  public void selectFiles(@Nonnull JFileChooser fileChooser, @Nonnull File[] files) {
    checkNotNull(files);
    for (File file : checkNotNullOrEmpty(files)) {
      checkNotNull(file);
    }
    setSelectedFiles(fileChooser, files);
  }

  /**
   * Sets the current directory in the {@code JFileChooser} to the given one.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @param dir the directory to set as current.
   * @throws IllegalStateException if the {@code JFileChooser} is disabled.
   * @throws IllegalStateException if the {@code JFileChooser} is not showing on the screen.
   */
  @RunsInEDT
  public void setCurrentDirectory(@Nonnull final JFileChooser fileChooser, @Nonnull final File dir) {
    execute(new GuiTask() {
      @Override protected void executeInEDT() {
        checkEnabledAndShowing(fileChooser);
        fileChooser.setCurrentDirectory(dir);
      }
    });
  }

  /**
   * Returns the text field where the user can enter the name of the file to select.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @return the found text field.
   * @throws ComponentLookupException if a matching text field could not be found.
   */
  @RunsInEDT
  public JTextField fileNameTextBox(@Nonnull JFileChooser fileChooser) {
    return robot.finder().findByType(fileChooser, JTextField.class);
  }

  /**
   * Finds and clicks the "Cancel" button in the given {@code JFileChooser}.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   * @throws IllegalStateException if the "Cancel" button is disabled.
   * @throws IllegalStateException if the "Cancel" button is not showing on the screen.
   */
  @RunsInEDT
  public void clickCancelButton(@Nonnull JFileChooser fileChooser) {
    click(cancelButton(fileChooser));
  }

  /**
   * Finds the "Cancel" button in the given {@code JFileChooser}.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton cancelButton(@Nonnull JFileChooser fileChooser) {
    return findButton(fileChooser, CANCEL_BUTTON, cancelButtonText());
  }

  /**
   * Finds and clicks the "Approve" button in the given {@code JFileChooser}.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   * @throws IllegalStateException if the "Approve" button is disabled.
   * @throws IllegalStateException if the "Approve" button is not showing on the screen.
   */
  @RunsInEDT
  public void clickApproveButton(@Nonnull JFileChooser fileChooser) {
    click(approveButton(fileChooser));
  }

  /**
   * Finds the "Approve" button in the given {@code JFileChooser}.
   *
   * @param fileChooser the target {@code JFileChooser}.
   * @return the found "Approve" button.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  @RunsInEDT
  public @Nonnull JButton approveButton(@Nonnull JFileChooser fileChooser) {
    return findButton(fileChooser, APPROVE_BUTTON, approveButtonTextFrom(fileChooser));
  }

  @RunsInEDT
  private @Nonnull JButton findButton(@Nonnull JFileChooser fileChooser, @Nonnull String logicalName,
      @Nullable String text) {
    return robot.finder().find(fileChooser, withText(text).andShowing());
  }
}
