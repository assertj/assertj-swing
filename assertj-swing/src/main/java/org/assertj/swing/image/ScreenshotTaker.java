/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.image;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.core.FocusOwnerFinder.focusOwner;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.image.ImageFileExtensions.PNG;
import static org.assertj.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.assertj.swing.query.ComponentSizeQuery.sizeOf;

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

import org.assertj.core.util.Preconditions;
import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.internal.annotation.IORuntimeException;
import org.assertj.swing.util.RobotFactory;

/**
 * Takes screenshots of the desktop and AWT or Swing {@code Component}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ScreenshotTaker implements ScreenshotTakerIF {
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
      robot = robotFactory.newRobotInLeftScreen();
    } catch (AWTException e) {
      throw new ImageException("Unable to create AWT Robot", e);
    }
  }

  @Override
  public void saveDesktopAsPng(String imageFilePath) {
    saveImage(takeDesktopScreenshot(), imageFilePath);
  }

  @Override
  public BufferedImage takeDesktopScreenshot() {
    Rectangle r = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    return takeScreenshot(r);
  }

  @Override
  public void saveComponentAsPng(@Nonnull Component c, @Nonnull String imageFilePath) {
    saveImage(takeScreenshotOf(c), imageFilePath);
  }

  @Override
  @Nonnull public BufferedImage takeScreenshotOf(@Nonnull Component c) {
    Point locationOnScreen = locationOnScreen(c);
    Dimension size = sizeOf(c);
    Rectangle r = new Rectangle(locationOnScreen.x, locationOnScreen.y, size.width, size.height);
    return takeScreenshot(r);
  }

  @Nonnull private BufferedImage takeScreenshot(Rectangle r) {
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
    return execute(() -> {
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
    });
  }

  // TODO(Alex): Verify that this method really needs to be executed in the EDT.
  @Nonnull private static BufferedImage takeScreenshot(final @Nonnull Robot robot, final @Nonnull Rectangle r) {
    BufferedImage result = execute(() -> robot.createScreenCapture(r));
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
    execute(() -> {
      Caret caret = textComponent.getCaret();
      if (caret != null) {
        caret.setVisible(true);
      }
    });
  }

  @Override
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
