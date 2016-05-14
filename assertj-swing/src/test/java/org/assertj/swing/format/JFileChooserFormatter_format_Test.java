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
package org.assertj.swing.format;

import static javax.swing.JFileChooser.OPEN_DIALOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.query.JFileChooserCurrentDirectoryQuery.currentDirectoryOf;

import javax.swing.JFileChooser;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserFormatter#format(java.awt.Component)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JFileChooserFormatter_format_Test extends SequentialEDTSafeTestCase {
  private JFileChooser fileChooser;
  private JFileChooserFormatter formatter;

  @Override
  protected final void onSetUp() {
    MyWindow window = MyWindow.createNew();
    fileChooser = window.fileChooser;
    formatter = new JFileChooserFormatter();
  }

  @Test
  public void should_Format_JFileChooser() {
    String formatted = formatter.format(fileChooser);
    assertThat(formatted).contains("javax.swing.JFileChooser").contains("name='fileChooser'")
        .contains("dialogTitle='A file chooser'").contains("dialogType=OPEN_DIALOG")
        .contains(concat("currentDirectory=", currentDirectoryOf(fileChooser))).contains("enabled=true")
        .contains("visible=true").contains("showing=false");
  }

  private static class MyWindow extends TestWindow {
    @RunsInEDT
    static MyWindow createNew() {
      return execute(() -> new MyWindow());
    }

    final JFileChooser fileChooser = new JFileChooser();

    private MyWindow() {
      super(JFileChooserFormatter_format_Test.class);
      fileChooser.setDialogTitle("A file chooser");
      fileChooser.setName("fileChooser");
      fileChooser.setDialogType(OPEN_DIALOG);
      addComponents(fileChooser);
    }
  }
}
