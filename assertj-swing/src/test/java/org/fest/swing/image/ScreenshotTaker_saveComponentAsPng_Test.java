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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.image.RandomFileNameCreator.randomFileName;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.util.Files.temporaryFolderPath;
import static org.fest.util.Strings.concat;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ScreenshotTaker#saveComponentAsPng(java.awt.Component, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker_saveComponentAsPng_Test extends SequentialEDTSafeTestCase {
  private String imagePath;
  private MyWindow window;
  private ScreenshotTaker taker;

  @Override
  protected void onSetUp() {
    imagePath = concat(temporaryFolderPath(), randomFileName());
    window = MyWindow.createNew();
    taker = new ScreenshotTaker();
    window.display();
  }

  @Override
  protected void onTearDown() {
    try {
      File f = new File(imagePath);
      if (f.isFile()) {
        f.delete();
      }
    } finally {
      window.destroy();
    }
  }

  @Test
  public void should_take_screenshot_of_Window_and_save_it_in_given_path() throws Exception {
    taker.saveComponentAsPng(window, imagePath);
    assertThat(read(imagePath)).hasSize(sizeOf(window));
  }

  @Test
  public void should_take_screenshot_of_Component_and_save_it_in_given_path() throws Exception {
    taker.saveComponentAsPng(window.button, imagePath);
    assertThat(read(imagePath)).hasSize(sizeOf(window.button));
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final JTextField textField = new JTextField(20);
    final JButton button = new JButton("Hello");

    private MyWindow() {
      super(ScreenshotTaker_saveComponentAsPng_Test.class);
      add(textField);
      add(button);
    }
  }
}
