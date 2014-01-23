/*
 * Created on May 6, 2007
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
package org.fest.swing.image;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.swing.image.RandomFileNameCreator.randomFileName;
import static org.fest.util.Files.temporaryFolderPath;
import static org.fest.util.Strings.concat;

import java.awt.Toolkit;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ScreenshotTaker#saveDesktopAsPng(String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker_saveDesktopAsPng_Test {
  private ScreenshotTaker taker;
  private String imagePath;

  @Before
  public void setUp() {
    imagePath = concat(temporaryFolderPath(), randomFileName());
    taker = new ScreenshotTaker();
  }

  @After
  public void tearDown() {
    File file = new File(imagePath);
    if (file.isFile()) {
      file.delete();
    }
  }

  @Test
  public void should_take_screenshot_of_desktop_and_save_it_in_given_path() throws Exception {
    taker.saveDesktopAsPng(imagePath);
    assertThat(read(imagePath)).hasSize(Toolkit.getDefaultToolkit().getScreenSize());
  }
}
