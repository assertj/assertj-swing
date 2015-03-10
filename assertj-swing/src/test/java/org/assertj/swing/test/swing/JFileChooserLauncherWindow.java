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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.Timer;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * {@code JFrame} that launches a {@code JFileChooser} using a configurable delay.
 * 
 * @author Alex Ruiz
 */
public class JFileChooserLauncherWindow extends TestWindow {
  @RunsInEDT
  public static JFileChooserLauncherWindow createNew(final Class<?> testClass) {
    return execute(new GuiQuery<JFileChooserLauncherWindow>() {
      @Override
      protected JFileChooserLauncherWindow executeInEDT() {
        return new JFileChooserLauncherWindow(testClass);
      }
    });
  }

  private int launchDelay;

  final JFileChooser fileChooser = new JFileChooser();

  private JFileChooserLauncherWindow(Class<?> testClass) {
    super(testClass);
    setUp();
  }

  private void setUp() {
    add(button());
    fileChooser.setName("fileChooser");
    fileChooser.setDialogTitle("Launched JFileChooser");
  }

  @RunsInCurrentThread
  private JButton button() {
    JButton button = new JButton("Browse");
    button.setName("browse");
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        launchJFileChooser();
      }
    });
    return button;
  }

  @RunsInCurrentThread
  private void launchJFileChooser() {
    if (launchDelay == 0) {
      showFileChooser();
      return;
    }
    start(new Timer(launchDelay, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showFileChooser();
      }
    }));
  }

  @RunsInCurrentThread
  private void start(Timer timer) {
    timer.setRepeats(false);
    timer.start();
  }

  @RunsInCurrentThread
  private void showFileChooser() {
    fileChooser.showOpenDialog(JFileChooserLauncherWindow.this);
  }

  public void launchDelay(int newLaunchDelay) {
    this.launchDelay = newLaunchDelay;
  }

  public JFileChooser fileChooser() {
    return fileChooser;
  }
}