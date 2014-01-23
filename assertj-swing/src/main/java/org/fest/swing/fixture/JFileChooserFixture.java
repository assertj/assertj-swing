/*
 * Created on Jul 9, 2007
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
package org.fest.swing.fixture;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JFileChooser;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JFileChooserDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Supports functional testing of {@code JFileChooser}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserFixture extends
    AbstractJComponentFixture<JFileChooserFixture, JFileChooser, JFileChooserDriver> {
  /**
   * Creates a new {@link JFileChooserFixture}.
   *
   * @param robot performs simulation of user events on a {@code JFileChooser}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JFileChooser} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JFileChooser} is found.
   */
  public JFileChooserFixture(@Nonnull Robot robot) {
    super(JFileChooserFixture.class, robot, JFileChooser.class);
  }

  /**
   * Creates a new {@link JFileChooserFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JFileChooser}.
   * @param target the {@code JFileChooser} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JFileChooserFixture(@Nonnull Robot robot, @Nonnull JFileChooser target) {
    super(JFileChooserFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JFileChooserFixture}.
   *
   * @param robot performs simulation of user events on a {@code JFileChooser}.
   * @param fileChooserName the name of the {@code JFileChooser} to find using the given {@code RobotFixture}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws ComponentLookupException if a matching {@code JFileChooser} could not be found.
   * @throws ComponentLookupException if more than one matching {@code JFileChooser} is found.
   */
  public JFileChooserFixture(@Nonnull Robot robot, @Nullable String fileChooserName) {
    super(JFileChooserFixture.class, robot, fileChooserName, JFileChooser.class);
  }

  @Override
  protected @Nonnull JFileChooserDriver createDriver(@Nonnull Robot robot) {
    return new JFileChooserDriver(robot);
  }

  /**
   * Simulates a user pressing the "Approve" button in this fixture's {@code JFileChooser}.
   *
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   * @throws IllegalStateException if the "Approve" button is disabled.
   * @throws IllegalStateException if the "Approve" button is not showing on the screen.
   */
  public void approve() {
    driver().clickApproveButton(target());
  }

  /**
   * Finds the "Approve" button in this fixture's {@code JFileChooser}.
   *
   * @return the found "Approve" button.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  public @Nonnull JButtonFixture approveButton() {
    return new JButtonFixture(robot(), driver().approveButton(target()));
  }

  /**
   * Simulates a user pressing the "Cancel" button in this fixture's {@code JFileChooser}.
   *
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   * @throws IllegalStateException if the "Cancel" button is disabled.
   * @throws IllegalStateException if the "Cancel" button is not showing on the screen.
   */
  public void cancel() {
    driver().clickCancelButton(target());
  }

  /**
   * Finds the "Cancel" button in this fixture's {@code JFileChooser}.
   *
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public @Nonnull JButtonFixture cancelButton() {
    return new JButtonFixture(robot(), driver().cancelButton(target()));
  }

  /**
   * Returns a fixture that manages the field where the user can enter the name of the file to select in this fixture's
   * {@code JFileChooser}.
   *
   * @return the created fixture.
   * @throws ComponentLookupException if a matching textToMatch field could not be found.
   */
  public @Nonnull JTextComponentFixture fileNameTextBox() {
    return new JTextComponentFixture(robot(), driver().fileNameTextBox(target()));
  }

  /**
   * Selects the given file in this fixture's {@code JFileChooser}.
   *
   * @param file the file to select.
   * @return this fixture.
   * @throws NullPointerException if the given file is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} is disabled.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} is not showing on the screen.
   * @throws IllegalArgumentException if this fixture's {@code JFileChooser} can select directories only and the file to
   *           select is not a directory.
   * @throws IllegalArgumentException if this fixture's {@code JFileChooser} cannot select directories and the file to
   *           select is a directory.
   */
  public @Nonnull JFileChooserFixture selectFile(@Nonnull File file) {
    driver().selectFile(target(), file);
    return this;
  }

  /**
   * Selects the given files in this fixture's {@code JFileChooser}.
   *
   * @param files the files to select.
   * @return this fixture.
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
  public @Nonnull JFileChooserFixture selectFiles(@Nonnull File... files) {
    driver().selectFiles(target(), files);
    return this;
  }

  /**
   * Sets the current directory of this fixture's {@code JFileChooser} to the given one.
   *
   * @param dir the directory to set as current.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} is disabled.
   * @throws IllegalStateException if this fixture's {@code JFileChooser} is not showing on the screen.
   * @return this fixture.
   */
  public @Nonnull JFileChooserFixture setCurrentDirectory(@Nonnull File dir) {
    driver().setCurrentDirectory(target(), dir);
    return this;
  }
}
