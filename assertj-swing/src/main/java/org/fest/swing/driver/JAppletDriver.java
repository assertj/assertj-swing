/*
 * Created on Mar 30, 2010 Modified on July 05, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static java.util.Collections.enumeration;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JApplet;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JApplet}s.
 * </p>
 * 
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 * 
 * @author Mel Llaguno
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
@InternalApi
public class JAppletDriver extends ComponentDriver implements AppletStub {
  private JApplet applet;

  /**
   * Creates a new {@link JAppletDriver}.
   * 
   * @param robot the robot used to simulate user input.
   */
  public JAppletDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Creates a new {@link JAppletDriver}.
   * 
   * @param robot the robot used to simulate user input.
   * @param newApplet the applet to simulate user input against.
   */
  public JAppletDriver(@Nonnull Robot robot, @Nonnull JApplet newApplet) {
    this(robot);
    applet = newApplet;
  }

  @RunsInEDT
  private static void doResize(final @Nonnull JApplet applet, final int width, final int height) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        applet.resize(width, height);
      }
    });
  }

  @RunsInEDT
  private static @Nullable URL codeBase(final @Nonnull JApplet applet) {
    return execute(new GuiQuery<URL>() {
      @Override
      protected @Nullable URL executeInEDT() {
        return applet.getCodeBase();
      }
    });
  }

  @RunsInEDT
  private static @Nullable URL documentBase(final @Nonnull JApplet applet) {
    return execute(new GuiQuery<URL>() {
      @Override
      protected URL executeInEDT() {
        return applet.getDocumentBase();
      }
    });
  }

  @RunsInEDT
  private static @Nullable String parameter(final @Nonnull JApplet applet, final @Nullable String parameterName) {
    return execute(new GuiQuery<String>() {
      @Override
      protected @Nullable String executeInEDT() {
        return applet.getParameter(parameterName);
      }
    });
  }

  @RunsInEDT
  private static boolean active(final @Nonnull JApplet applet) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected @Nullable Boolean executeInEDT() {
        return applet.isActive();
      }
    });
    return checkNotNull(result);
  }

  /**
   * Requests the default {@code JApplet} to be resized.
   * 
   * @param width the new width.
   * @param height the new height.
   */
  @RunsInEDT
  @Override
  public void appletResize(int width, int height) {
    appletResize(applet, width, height);
  }

  /**
   * Requests the given {@code JApplet} to be resized.
   * 
   * @param applet the given {@code JApplet}.
   * @param width the new width.
   * @param height the new height.
   */
  @RunsInEDT
  public void appletResize(@Nonnull JApplet applet, int width, int height) {
    doResize(applet, width, height);
  }

  /**
   * @return the {@code AppletContext} of the default {@code JApplet}.
   */
  @RunsInEDT
  @Override
  public AppletContext getAppletContext() {
    return getAppletContext(applet);
  }

  /**
   * Returns the {@link AppletContext} of the given {@code JApplet}.
   * 
   * @param applet the given {@code JApplet}.
   * @return the {@code AppletContext} of the given {@code JApplet}.
   */
  @RunsInEDT
  public AppletContext getAppletContext(JApplet applet) {
    return appletContext(applet);
  }

  @RunsInEDT
  private static @Nullable AppletContext appletContext(final @Nonnull JApplet applet) {
    return execute(new GuiQuery<AppletContext>() {
      @Override
      protected @Nullable AppletContext executeInEDT() {
        return applet.getAppletContext();
      }
    });
  }

  /**
   * @return the URL of the directory that contains the default {@code JApplet}.
   */
  @RunsInEDT
  @Override
  public @Nullable URL getCodeBase() {
    return getCodeBase(applet);
  }

  /**
   * Returns the URL of the directory that contains the given {@code JApplet}.
   * 
   * @param applet the given {@code JApplet}.
   * @return the URL of the directory that contains the given {@code JApplet}.
   */
  @RunsInEDT
  public @Nullable URL getCodeBase(@Nonnull JApplet applet) {
    return codeBase(applet);
  }

  /**
   * Returns the URL of the document the default {@code JApplet} is embedded.
   * 
   * @return the URL of the document the given {@code JApplet} is embedded.
   */
  @RunsInEDT
  @Override
  public @Nullable URL getDocumentBase() {
    return getDocumentBase(applet);
  }

  /**
   * Returns the URL of the document the given {@code JApplet} is embedded.
   * 
   * @param applet the given {@code JApplet}.
   * @return the URL of the document the given {@code JApplet} is embedded.
   */
  @RunsInEDT
  public @Nullable URL getDocumentBase(@Nonnull JApplet applet) {
    return documentBase(applet);
  }

  /**
   * Returns the value of the named parameter in the default {@code JApplet} in the HTML tag, or {@code null} if
   * not set.
   * 
   * @param name a parameter name.
   * @return the value of the named parameter in the default {code JApplet} in the HTML tag, or {@code null} if not
   *         set.
   */
  @RunsInEDT
  @Override
  public @Nullable String getParameter(@Nullable String name) {
    return getParameter(applet, name);
  }

  /**
   * Returns the value of the named parameter in the given {@code JApplet} in the HTML tag, or {@code null} if not set.
   * 
   * @param applet the given {@code JApplet}.
   * @param name a parameter name.
   * @return the value of the named parameter in the given {code JApplet} in the HTML tag, or {@code null} if not set.
   */
  @RunsInEDT
  public String getParameter(@Nonnull JApplet applet, @Nullable String name) {
    return parameter(applet, name);
  }

  /**
   * Indicates whether the default {@code JApplet} is active or not.
   * 
   * @return {@code true} if the default {@code JApplet} is active; {@code false} otherwise.
   */
  @RunsInEDT
  @Override
  public boolean isActive() {
    return isActive(applet);
  }

  /**
   * Indicates whether the given {@code JApplet} is active or not.
   * 
   * @param applet the given {@code JApplet}.
   * @return {@code true} if the given {@code JApplet} is active; {@code false} otherwise.
   */
  @RunsInEDT
  public boolean isActive(@Nonnull JApplet applet) {
    return active(applet);
  }

  /**
   * Returns the {@code JApplet} of the given its name in the {@code AppletContext}.
   * 
   * @param name the name of the {@code JApplet}.
   * @return the {@code Applet} with the given name.
   */
  @RunsInEDT
  public Applet getApplet(@Nonnull String name) {
    return applet.getAppletContext().getApplet(name);
  }

  /**
   * @return the collection of {@code Applet}s within the {@code AppletContext}.
   */
  @RunsInEDT
  public @Nonnull Enumeration<Applet> getApplets() {
    Enumeration<Applet> applets = applet.getAppletContext().getApplets();
    return applets != null ? applets : enumeration(Collections.<Applet>emptyList());
  }
}
