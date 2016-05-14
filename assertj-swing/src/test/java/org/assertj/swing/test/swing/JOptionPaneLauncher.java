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
package org.assertj.swing.test.swing;

import static javax.swing.SwingUtilities.invokeLater;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.swing.JOptionPaneHost.host;
import static org.assertj.swing.timing.Pause.pause;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.timing.Condition;

/**
 * Launches a {@code JOptionPane}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class JOptionPaneLauncher {
  /**
   * Launches the given {@code JOptionPane}, using the text "A Message" as the title of the dialog hosting the
   * {@code JOptionPane}.
   * 
   * @param optionPane the {@code JOptionPane} to launch.
   * @return the dialog hosting the {@code JOptionPane}.
   */
  @RunsInEDT
  public static JDialog launch(JOptionPane optionPane) {
    return launch(optionPane, "A Message");
  }

  /**
   * Launches the given {@code JOptionPane}.
   * 
   * @param optionPane the {@code JOptionPane} to launch.
   * @param title the title of the dialog to host the {@code JOptionPane}.
   * @return the dialog hosting the {@code JOptionPane}.
   */
  @RunsInEDT
  public static JDialog launch(final JOptionPane optionPane, final String title) {
    final JDialog dialog = pack(optionPane, title);
    invokeLater(new Runnable() {
      @Override
      public void run() {
        dialog.pack();
        dialog.setVisible(true);
        dialog.dispose();
      }
    });
    pause(new Condition("JOptionPane is showing") {
      @Override
      public boolean test() {
        return dialog.isShowing();
      }
    });
    return dialog;
  }

  /**
   * Packs the given {@code JOptionPane} in a {@link JDialog}.
   * 
   * @param optionPane the {@code JOptionPane} to launch.
   * @param title the title of the dialog to host the {@code JOptionPane}.
   * @return the dialog hosting the {@code JOptionPane}.
   */
  @RunsInEDT
  public static JDialog pack(final JOptionPane optionPane, final String title) {
    final JDialog dialog = execute(() -> host(optionPane, title));
    return dialog;
  }

  private JOptionPaneLauncher() {
  }
}
