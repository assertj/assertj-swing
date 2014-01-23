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

import static org.fest.util.Arrays.array;

import java.io.File;

import javax.swing.JFileChooser;

import org.junit.Test;

/**
 * Tests for {@link JFileChooserDriver#selectFiles(JFileChooser, java.io.File[])}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserDriver_selectFiles_withInvalidInput_Test extends JFileChooserDriver_withMocks_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_of_files_is_null() {
    driver.selectFiles(fileChooser, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_array_of_files_is_empty() {
    driver.selectFiles(fileChooser, new File[0]);
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_any_file_is_null() {
    driver.selectFiles(fileChooser, array(new File("fake"), null));
  }
}
