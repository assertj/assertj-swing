/*
 * Created on Jul 10, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.applet;

import static javax.swing.BorderFactory.createBevelBorder;
import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static javax.swing.border.BevelBorder.LOWERED;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * <p>
 * A window that displays an {@code Applet}.
 * </p>
 *
 * <p>
 * Example:
 * <pre>
 * AppletViewer viewer = AppletViewer.newViewer(new MyApplet());
 *
 * // test the applet, viewer can be wrapped with a FrameFixture.
 * FrameFixture viewerFixture = new FrameFixture(viewer);
 *
 * viewer.unloadApplet() // stops and destroys the applet
 * viewerFixture.cleanUp();
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class AppletViewer extends JFrame implements StatusDisplay {
  private static final Dimension DEFAULT_SIZE = new Dimension(100, 100);

  private final JLabel statusLabel = new JLabel();

  private final Applet applet;
  private transient AppletStub stub;
  private boolean loaded;

  /**
   * <p>
   * Creates a new {@link AppletViewer} using a {@link BasicAppletStub} as
   * <p>
   *
   * <p>
   * <b>Note:</b> This method is executed in the event dispatch thread (EDT.)
   * </p>
   *
   * @param applet the {@code Applet} to view.
   * @return the created {@code AppletViewer}.
   * @throws NullPointerException if {@code applet} is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  public static @Nonnull AppletViewer newViewer(@Nonnull Applet applet) {
    AppletViewer viewer = createInEDT(applet);
    viewer.appletStub(new BasicAppletStub(viewer, new BasicAppletContext(viewer)));
    return viewer;
  }

  /**
   * <p>
   * Creates a new {@link AppletViewer}. This factory method creates new instances of {@link BasicAppletStub} and
   * {@link BasicAppletContext}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is executed in the event dispatch thread (EDT.)
   * </p>
   *
   * @param applet the {@code Applet} to view.
   * @param parameters the parameters included in an "applet" HTML tag.
   * @return the created {@code AppletViewer}.
   * @throws NullPointerException if {@code applet} is {@code null}.
   * @throws NullPointerException if {@code parameters} is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  public static @Nonnull AppletViewer newViewer(@Nonnull Applet applet, @Nonnull Map<String, String> parameters) {
    AppletViewer viewer = createInEDT(applet);
    viewer.appletStub(new BasicAppletStub(viewer, new BasicAppletContext(viewer), parameters));
    return viewer;
  }

  /**
   * Creates a new {@link AppletViewer}.
   * <p>
   * <b>Note:</b> This method is executed in the event dispatch thread (EDT.)
   * </p>
   *
   * @param applet the {@code Applet} to view.
   * @param stub the applet's stub.
   * @return the created {@code AppletViewer}.
   * @throws NullPointerException if {@code applet} is {@code null}.
   * @throws NullPointerException if {@code stub} is {@code null}.
   * @since 1.2
   */
  @RunsInEDT
  public static AppletViewer newViewer(@Nonnull Applet applet, @Nonnull AppletStub stub) {
    checkNotNull(applet);
    AppletViewer viewer = createInEDT(applet);
    viewer.appletStub(stub);
    return viewer;
  }

  @RunsInEDT
  private static AppletViewer createInEDT(@Nonnull final Applet applet) {
    return execute(new GuiQuery<AppletViewer>() {
      @Override
      protected AppletViewer executeInEDT() {
        return new AppletViewer(applet);
      }
    });
  }

  private AppletViewer(@Nonnull Applet applet) {
    this.applet = checkNotNull(applet);
    setUpFrame();
    addContent();
  }

  private void setUpFrame() {
    setTitle(concat("Applet Viewer: ", applet.getClass().getName()));
    setSize(DEFAULT_SIZE);
    setLayout(new BorderLayout());
  }

  private void addContent() {
    add(applet, BorderLayout.CENTER);
    statusLabel.setBorder(createCompoundBorder(createBevelBorder(LOWERED), createEmptyBorder(2, 5, 2, 5)));
    statusLabel.setName("status");
    add(statusLabel, BorderLayout.SOUTH);
  }

  private void appletStub(@Nonnull AppletStub newAppletStub) {
    checkNotNull(newAppletStub);
    stub = newAppletStub;
    applet.setStub(stub);
    setUpApplet();
  }

  private void setUpApplet() {
    loadApplet();
    showStatus("Applet loaded");
  }

  /**
   * Initializes and starts the {@code Applet} in this viewer.
   */
  public void reloadApplet() {
    if (loaded) {
      unloadApplet();
    }
    loadApplet();
  }

  private void loadApplet() {
    applet.init();
    applet.start();
    loaded = true;
  }

  /**
   * Stops and destroys the {@code Applet} loaded in this viewer. This method should be called before closing or
   * disposing this viewer.
   */
  public void unloadApplet() {
    applet.stop();
    applet.destroy();
    loaded = false;
  }

  /**
   * Indicates whether the {@code Applet} in this viewer is loaded or not.
   *
   * @return {@code true} if this {@code Applet} is loaded, {@code false} otherwise.
   */
  public boolean appletLoaded() {
    return loaded;
  }

  /**
   * Displays the given status message. This method is executed in the event dispatch thread (EDT.)
   *
   * @param status the status to display.
   */
  @RunsInEDT
  @Override
  public void showStatus(@Nonnull final String status) {
    if (isEventDispatchThread()) {
      setStatus(status);
      return;
    }
    invokeLater(new Runnable() {
      @Override
      public void run() {
        setStatus(status);
      }
    });
  }

  @RunsInCurrentThread
  private void setStatus(@Nonnull String status) {
    statusLabel.setText(status);
  }

  /**
   * @return the {@code Applet} displayed in this viewer.
   */
  public @Nonnull Applet getApplet() {
    return applet;
  }

  /**
   * @return the {@code AppletStub} used in this viewer.
   */
  public @Nonnull AppletStub getAppletStub() {
    return stub;
  }
}
