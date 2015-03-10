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
package org.assertj.swing.image;

import static org.assertj.core.util.Files.temporaryFolderPath;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.assertions.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.image.RandomFileNameCreator.randomFileName;
import static org.assertj.swing.query.ComponentSizeQuery.sizeOf;
import static org.assertj.swing.util.ImageReader.readImageFrom;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
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
  public void should_Take_Screenshot_Of_Window_And_Save_It_In_Given_Path() throws Exception {
    taker.saveComponentAsPng(window, imagePath);
    assertThat(readImageFrom(imagePath)).hasSize(sizeOf(window));
  }

  @Test
  public void should_Take_Screenshot_Of_Component_And_Save_It_In_Given_Path() throws Exception {
    taker.saveComponentAsPng(window.button, imagePath);
    assertThat(readImageFrom(imagePath)).hasSize(sizeOf(window.button));
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
