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

import static org.fest.swing.core.FocusOwnerFinder.focusOwner;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.image.ImageFileExtensions.PNG;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.util.RobotFactory;
import org.fest.util.IORuntimeException;
import org.fest.util.Preconditions;
import org.fest.util.VisibleForTesting;

/**
 * Takes screenshots of the desktop and AWT or Swing {@code Component}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker {
  private final Robot robot;
  private final ImageFileWriter writer;

  /**
   * Creates a new {@link ScreenshotTaker}.
   *
   * @throws ImageException if an AWT Robot (the responsible for taking screenshots) cannot be instantiated.
   */
  public ScreenshotTaker() {
    this(new ImageFileWriter(), new RobotFactory());
  }

  @VisibleForTesting
  ScreenshotTaker(@Nonnull ImageFileWriter writer, @Nonnull RobotFactory robotFactory) {
    this.writer = writer;
    try {
      robot = robotFactory.newRobotInPrimaryScreen();
    } catch (AWTException e) {
      throw new ImageException("Unable to create AWT Robot", e);
    }
  }

  /**
   * Takes a screenshot of the desktop and saves it as a PNG file.
   *
   * @param imageFilePath the path of the file to save the screenshot to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   * @throws IllegalArgumentException if the given file path belongs to a non-empty directory.
   * @throws IORuntimeException if an I/O error prevents the image from being saved as a file.
   */
  public void saveDesktopAsPng(String imageFilePath) {
    saveImage(takeDesktopScreenshot(), imageFilePath);
  }

  /**
   * Takes a screenshot of the desktop.
   *
   * @return the screenshot of the desktop.
   * @throws SecurityException if {@code readDisplayPixels} permission is not granted.
   */
  public BufferedImage takeDesktopScreenshot() {
    Rectangle r = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    return takeScreenshot(r);
  }

  /**
   * Takes a screenshot of the given AWT or Swing {@code Component} and saves it as a PNG file.
   *
   * @param c the given {@code Component}.
   * @param imageFilePath the path of the file to save the screenshot to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   * @throws IllegalArgumentException if the given file path belongs to a non-empty directory.
   * @throws IORuntimeException if an I/O error prevents the image from being saved as a file.
   */
  public void saveComponentAsPng(@Nonnull Component c, @Nonnull String imageFilePath) {
    saveImage(takeScreenshotOf(c), imageFilePath);
  }

  /**
   * Takes a screenshot of the given AWT or Swing {@code Component}.
   *
   * @param c the given {@code Component}.
   * @return a screenshot of the given {@code Component}.
   * @throws SecurityException if {@code readDisplayPixels} permission is not granted.
   */
  public @Nonnull BufferedImage takeScreenshotOf(@Nonnull Component c) {
    Point locationOnScreen = locationOnScreen(c);
    Dimension size = sizeOf(c);
    Rectangle r = new Rectangle(locationOnScreen.x, locationOnScreen.y, size.width, size.height);
    return takeScreenshot(r);
  }

  private @Nonnull BufferedImage takeScreenshot(Rectangle r) {
    JTextComponent textComponent = findFocusOwnerAndHideItsCaret();
    robot.waitForIdle();
    try {
      return takeScreenshot(robot, r);
    } finally {
      showCaretIfPossible(textComponent);
    }
  }

  @RunsInEDT
  private static JTextComponent findFocusOwnerAndHideItsCaret() {
    return execute(new GuiQuery<JTextComponent>() {
      @Override
      protected JTextComponent executeInEDT() {
        Component focusOwner = focusOwner();
        if (!(focusOwner instanceof JTextComponent)) {
          return null;
        }
        JTextComponent textComponent = (JTextComponent) focusOwner;
        Caret caret = textComponent.getCaret();
        if (caret == null || !caret.isVisible()) {
          return null;
        }
        caret.setVisible(false);
        return textComponent;
      }
    });
  }

  // TODO(Alex): Verify that this method really needs to be executed in the EDT.
  private static @Nonnull BufferedImage takeScreenshot(final @Nonnull Robot robot, final @Nonnull Rectangle r) {
    BufferedImage result = execute(new GuiQuery<BufferedImage>() {
      @Override
      protected @Nullable BufferedImage executeInEDT() {
        return robot.createScreenCapture(r);
      }
    });
    return checkNotNull(result);
  }

  private void showCaretIfPossible(@Nullable JTextComponent textComponent) {
    if (textComponent == null) {
      return;
    }
    showCaretOf(textComponent);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void showCaretOf(final @Nonnull JTextComponent textComponent) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        Caret caret = textComponent.getCaret();
        if (caret != null) {
          caret.setVisible(true);
        }
      }
    });
  }

  /**
   * Saves the given image as a PNG file.
   *
   * @param image the image to save.
   * @param filePath the path of the file to save the image to.
   * @throws NullPointerException if the given file path is {@code null}.
   * @throws IllegalArgumentException if the given file path is empty.
   * @throws IllegalArgumentException if the given file path does not end with ".png".
   * @throws IllegalArgumentException if the given file path belongs to a non-empty directory.
   * @throws IORuntimeException if an I/O error prevents the image from being saved as a file.
   */
  public void saveImage(@Nonnull BufferedImage image, @Nonnull String filePath) {
    Preconditions.checkNotNullOrEmpty(filePath);
    if (!filePath.endsWith(PNG)) {
      String format = String.format("The file in path '%s' should have extension 'png'", filePath);
      throw new IllegalArgumentException(format);
    }
    try {
      writer.writeAsPng(image, filePath);
    } catch (IOException e) {
      String msg = String.format("Unable to save image as '%s'", filePath);
      throw new IORuntimeException(msg, e);
    }
  }
}
