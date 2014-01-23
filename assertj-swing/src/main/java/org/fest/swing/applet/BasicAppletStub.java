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

import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.Window;
import java.net.URL;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Basic (and limited) implementation of {@code AppletStub}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicAppletStub implements AppletStub {
  private final Window viewer;
  private final AppletContext context;
  private final Map<String, String> parameters = newHashMap();

  /**
   * Creates a new {@link BasicAppletStub}.
   * 
   * @param viewer the window to host the {@code Applet}.
   * @param context the {@code Applet} enviroment.
   * @param parameters the parameters included in an "applet" HTML tag.
   * @throws NullPointerException if {@code viewer}, {@code context} or {@code parameters} are {@code null}.
   */
  public BasicAppletStub(@Nonnull Window viewer, @Nonnull AppletContext context,
      @Nonnull Map<String, String> parameters) {
    this(viewer, context);
    this.parameters.putAll(checkNotNull(parameters));
  }

  /**
   * Creates a new {@link BasicAppletStub}.
   * 
   * @param viewer the window to host the {@code Applet}.
   * @param context the {@code Applet} environment.
   * @throws NullPointerException if {@code viewer} or {@code context} are {@code null}.
   */
  public BasicAppletStub(@Nonnull Window viewer, @Nonnull AppletContext context) {
    this.viewer = checkNotNull(viewer);
    this.context = checkNotNull(context);
  }

  /**
   * Not implemented. Returns {@code true}.
   * 
   * @see AppletStub#isActive()
   */
  @Override
  public boolean isActive() {
    return true;
  }

  /**
   * Resizes this stub's viewer when the {@code Applet} wants to be resized.
   * 
   * @param width the new requested width for the {@code Applet}.
   * @param height the new requested height for the {@code Applet}.
   */
  @Override
  public void appletResize(int width, int height) {
    viewer.setSize(width, height);
  }

  /**
   * @return the {@code Applet}'s context.
   */
  @Override
  public @Nonnull AppletContext getAppletContext() {
    return context;
  }

  /**
   * Not implemented. Returns {@link #getDocumentBase()}.
   * 
   * @see AppletStub#getCodeBase()
   */
  @Override
  public URL getCodeBase() {
    return getDocumentBase();
  }

  /**
   * Not implemented. Returns {@code getClass().getResource(".")}.
   * 
   * @see AppletStub#getDocumentBase()
   */
  @Override
  public URL getDocumentBase() {
    return getClass().getResource(".");
  }

  /**
   * Returns the value of the named parameter in the "applet" HTML tag.
   * 
   * @param name a parameter name.
   * @return the value of the named parameter, or {@code null} if not set.
   */
  @Override
  public @Nullable String getParameter(@Nullable String name) {
    return parameters.get(name);
  }
}
