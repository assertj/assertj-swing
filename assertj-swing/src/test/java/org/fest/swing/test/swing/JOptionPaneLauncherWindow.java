/*
 * Created on May 20, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.test.swing;

import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.Timer;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * {@code JFrame} that launches a {@code JOptionPane} using a configurable delay.
 *
 * @author Alex Ruiz
 */
public class JOptionPaneLauncherWindow extends TestWindow {
  @RunsInEDT
  public static JOptionPaneLauncherWindow createNew(final Class<?> testClass) {
    return execute(new GuiQuery<JOptionPaneLauncherWindow>() {
      @Override
      protected JOptionPaneLauncherWindow executeInEDT() {
        return new JOptionPaneLauncherWindow(testClass);
      }
    });
  }

  private int launchDelay;

  private JOptionPaneLauncherWindow(Class<?> testClass) {
    super(testClass);
    setUp();
  }

  private void setUp() {
    add(button());
  }

  @RunsInCurrentThread
  private JButton button() {
    JButton button = new JButton("Message");
    button.setName("message");
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        launchDialog();
      }
    });
    return button;
  }

  @RunsInCurrentThread
  private void launchDialog() {
    if (launchDelay == 0) {
      showJOptionPane();
      return;
    }
    start(new Timer(launchDelay, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showJOptionPane();
      }
    }));
  }

  @RunsInCurrentThread
  private void start(Timer timer) {
    timer.setRepeats(false);
    timer.start();
  }

  @RunsInCurrentThread
  private void showJOptionPane() {
    showMessageDialog(JOptionPaneLauncherWindow.this, "A message", "Hello", PLAIN_MESSAGE);
  }

  public void launchDelay(int newLaunchDelay) {
    this.launchDelay = newLaunchDelay;
  }
}