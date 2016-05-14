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
import static org.assertj.swing.test.swing.TestWindow.DEFAULT_WINDOW_LOCATION;
import static org.assertj.swing.test.task.DialogShowTask.packAndShow;
import static org.assertj.swing.test.task.DialogShowTask.waitForShowing;
import static org.assertj.swing.test.task.WindowDestroyTask.hideAndDispose;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JDialog;

import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.task.FrameShowTask;

/**
 * Base dialog for all GUI tests.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class TestDialog extends JDialog {
  private static final Dimension DEFAULT_PREFERRED_SIZE = new Dimension(200, 100);

  static final Point DEFAULT_DIALOG_LOCATION = new Point(200, 200);

  /**
   * Creates a new {@link TestDialog} and displays it on the screen with the given frame as its owner. This constructor
   * will set the title of the dialog to be the same as its owner. This method is executed in the event dispatch thread
   * (EDT).
   *
   * @param owner the owner of the dialog to create.
   * @return the created window.
   */
  @RunsInEDT
  public static TestDialog createAndShowNewDialog(final Frame owner) {
    TestDialog dialog = execute(() -> createInCurrentThread(owner));
    dialog.display();
    return dialog;
  }

  /**
   * Creates a new {@link TestDialog} with the given frame as its owner. This constructor will set the title of the
   * dialog to be the same as its owner. This method is executed in the event dispatch thread (EDT).
   *
   * @param owner the owner of the dialog to create.
   * @return the created window.
   */
  @RunsInEDT
  public static TestDialog createNewDialog(final Frame owner) {
    return execute(() -> createInCurrentThread(owner));
  }

  @RunsInCurrentThread
  private static TestDialog createInCurrentThread(Frame owner) {
    return new TestDialog(owner);
  }

  /**
   * Creates a new {@link TestDialog} with the given frame as its owner. This constructor will set the title of the
   * dialog to be the same as its owner.
   *
   * @param owner the owner of the dialog to create.
   */
  @RunsInCurrentThread
  protected TestDialog(Frame owner) {
    super(owner);
    setTitle(owner.getTitle());
    setLayout(new FlowLayout());
  }

  /**
   * Adds the given GUI components to this dialog. This method is <b>not</b> executed in the event dispatch thread
   * (EDT).
   *
   * @param components the components to add.
   */
  @RunsInCurrentThread
  public void addComponents(Component... components) {
    for (Component c : components) {
      add(c);
    }
  }

  /**
   * Displays this dialog on the screen. This method is executed in the event dispatch thread (EDT).
   */
  @RunsInEDT
  public void display() {
    display(this);
  }

  /**
   * Displays the given dialog on the screen. the current thread where it is called.
   *
   * @param dialog the dialog to display on the screen.
   */
  @RunsInEDT
  protected static void display(TestDialog dialog) {
    display(dialog, DEFAULT_PREFERRED_SIZE);
  }

  /**
   * Displays this dialog on the screen using the given dimension as its preferred size. This method is executed in the
   * event dispatch thread (EDT).
   *
   * @param preferredSize the preferred size to set to this dialog before displaying it on the screen.
   */
  @RunsInEDT
  public void display(final Dimension preferredSize) {
    display(TestDialog.this, preferredSize);
  }

  /**
   * Displays the given dialog on the screen using the given dimension as its preferred size. This method is executed in
   * the EDT.
   *
   * @param dialog the dialog to display on the screen.
   * @param preferredSize the preferred size to set to the given dialog before displaying it on the screen.
   */
  @RunsInEDT
  protected static void display(final TestDialog dialog, final Dimension preferredSize) {
    execute(() -> {
      showOwnerIfPossible(dialog.getOwner());
      dialog.setLocation(DEFAULT_DIALOG_LOCATION);
      packAndShow(dialog, preferredSize);
    });
    waitForShowing(dialog);
  }

  @RunsInCurrentThread
  private static void showOwnerIfPossible(Window owner) {
    if (!(owner instanceof Frame)) {
      return;
    }
    Frame dialogOwner = (Frame) owner;
    dialogOwner.setLocation(DEFAULT_WINDOW_LOCATION);
    FrameShowTask.packAndShow(dialogOwner);
  }

  /**
   * Chooses the look and feel.
   */
  @RunsInCurrentThread
  protected void chooseLookAndFeel() {
    // applySubstanceBusinessLookAndFeel();
  }

  /**
   * Hides and disposes this dialog. This method is executed in the event dispatch thread (EDT).
   */
  @RunsInEDT
  public void destroy() {
    execute(() -> destroy(TestDialog.this));
  }

  /**
   * Hides and disposes the given dialog. This method is executed in the current thread where it is called.
   *
   * @param dialog the dialog to destroy.
   */
  @RunsInCurrentThread
  protected static void destroy(TestDialog dialog) {
    hideAndDispose(dialog);
  }
}
