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
import static org.assertj.swing.test.query.JFileChooserCurrentDirectoryQuery.currentDirectoryOf;

import java.io.File;

import javax.swing.JFileChooser;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#setCurrentDirectory(javax.swing.JFileChooser, java.io.File)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_setCurrentDirectory_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_Set_Current_Directory() {
    File userHome = userHomeDirectory();
    showWindow();
    driver.setCurrentDirectory(fileChooser, userHome);
    assertThat(absolutePathOfCurrentDirectory(fileChooser)).isEqualTo(userHome.getAbsolutePath());
  }

  @RunsInEDT
  private static String absolutePathOfCurrentDirectory(final JFileChooser fileChooser) {
    return currentDirectoryOf(fileChooser).getAbsolutePath();
  }

  @Test
  public void should_Throw_Error_If_JFileChooser_Is_Disabled() {
    disableFileChooser();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.setCurrentDirectory(fileChooser, userHomeDirectory());
  }

  @Test
  public void should_Throw_Error_If_JFileChooser_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.setCurrentDirectory(fileChooser, userHomeDirectory());
  }

  private File userHomeDirectory() {
    String homePath = System.getProperty("user.home");
    File userHome = new File(homePath);
    assertThat(userHome.isDirectory()).isTrue();
    return userHome;
  }
}
