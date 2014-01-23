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
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.query.JFileChooserCurrentDirectoryQuery.currentDirectoryOf;

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#setCurrentDirectory(javax.swing.JFileChooser, java.io.File)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_setCurrentDirectory_Test extends JFileChooserDriver_TestCase {
  @Test
  public void should_set_current_directory() {
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
  public void should_throw_error_if_JFileChooser_is_disabled() {
    disableFileChooser();
    try {
      driver.setCurrentDirectory(fileChooser, userHomeDirectory());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JFileChooser_is_not_showing_on_the_screen() {
    try {
      driver.setCurrentDirectory(fileChooser, userHomeDirectory());
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

  private File userHomeDirectory() {
    String homePath = System.getProperty("user.home");
    File userHome = new File(homePath);
    assertThat(userHome.isDirectory()).isTrue();
    return userHome;
  }
}
