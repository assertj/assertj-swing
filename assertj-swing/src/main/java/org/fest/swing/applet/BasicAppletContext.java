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

import static java.util.Collections.enumeration;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AudioClip;
import java.awt.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Basic (and limited) implementation of {@code AppletContext}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicAppletContext implements AppletContext {
  private static final Enumeration<Applet> NO_APPLETS = new EmptyAppletEnumeration();

  private static final class EmptyAppletEnumeration implements Enumeration<Applet> {
    EmptyAppletEnumeration() {}

    @Override
    public boolean hasMoreElements() {
      return false;
    }

    @Override
    public @Nullable Applet nextElement() {
      return null;
    }
  }

  private final Map<String, InputStream> streamMap = newHashMap();
  private final StatusDisplay statusDisplay;

  /**
   * Creates a new {@link BasicAppletContext}.
   * 
   * @param statusDisplay where the application can inform users of its current state.
   * @throws NullPointerException if {@code statusDisplay} is {@code null}.
   */
  public BasicAppletContext(@Nonnull StatusDisplay statusDisplay) {
    this.statusDisplay = checkNotNull(statusDisplay);
  }

  /**
   * If the {@link StatusDisplay} passed to this context is an instance of {@link AppletViewer}, this method will return
   * the {@code Applet} of such {@code AppletViewer}. Otherwise this method returns {@code null}.
   * 
   * @return the {@code Applet} in this context's {@link StatusDisplay} (if any.)
   * @see AppletContext#getApplet(String)
   */
  @Override
  public @Nullable Applet getApplet(@Nullable String name) {
    return appletFrom(statusDisplay);
  }

  /**
   * Returns an enumeration containing the {@code Applet} returned by {@link #getApplet(String)}. If
   * {@link #getApplet(String)} returns {@code null}, this method will return an empty enumeration.
   * 
   * @return an enumeration containing the {@code Applet} in this context's {@code StatusDisplay} (if any.)
   * @see #getApplet(String)
   * @see AppletContext#getApplets()
   */
  @Override
  public @Nonnull Enumeration<Applet> getApplets() {
    Applet applet = appletFrom(statusDisplay);
    return applet == null ? NO_APPLETS : enumeration(newArrayList(applet));
  }

  private static @Nullable Applet appletFrom(@Nullable StatusDisplay statusDisplay) {
    if (!(statusDisplay instanceof AppletViewer)) {
      return null;
    }
    AppletViewer viewer = (AppletViewer) statusDisplay;
    return viewer.getApplet();
  }

  /**
   * Not implemented. Returns {@code null}.
   * 
   * @see AppletContext#getAudioClip(URL)
   */
  @Override
  public @Nullable AudioClip getAudioClip(@Nullable URL url) {
    return null;
  }

  /**
   * Not implemented. Returns {@code null}.
   * 
   * @see AppletContext#getImage(URL)
   */
  @Override
  public @Nullable Image getImage(@Nullable URL url) {
    return null;
  }

  /**
   * Returns the stream to which specified key is associated within this {@code AppletContext}. Returns {@code null} if
   * the {@code AppletContext} contains no stream for this key.
   * 
   * @return the stream to which this {@code AppletContext} maps the key.
   * @param key key whose associated stream is to be returned.
   */
  @Override
  public @Nullable InputStream getStream(@Nullable String key) {
    return streamMap.get(key);
  }

  /**
   * Finds all the keys of the streams in this {@code AppletContext}.
   * 
   * @return an iterator of all the names of the streams in this {@code AppletContext}.
   */
  @Override
  public @Nonnull Iterator<String> getStreamKeys() {
    return streamMap.keySet().iterator();
  }

  /**
   * Associates the specified stream with the specified key in this {@code AppletContext}.
   * 
   * @param key key with which the specified value is to be associated.
   * @param stream stream to be associated with the specified key. If this parameter is {@code null}, the specified key
   *          is removed in this {@code AppletContext}.
   */
  @Override
  public void setStream(@Nullable String key, @Nullable InputStream stream) {
    if (stream == null) {
      streamMap.remove(key);
      return;
    }
    streamMap.put(key, stream);
  }

  /**
   * Not implemented.
   * 
   * @see AppletContext#showDocument(URL)
   */
  @Override
  public void showDocument(@Nullable URL url) {}

  /**
   * Not implemented.
   * 
   * @see AppletContext#showDocument(URL, String)
   */
  @Override
  public void showDocument(@Nullable URL url, @Nullable String target) {}

  /**
   * Requests that the given status be displayed in this context's {@link StatusDisplay}.
   * 
   * @param status a message to display.
   */
  @Override
  public void showStatus(String status) {
    statusDisplay.showStatus(checkNotNull(status));
  }
}
