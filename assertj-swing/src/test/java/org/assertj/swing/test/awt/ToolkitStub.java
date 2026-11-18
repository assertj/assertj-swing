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
package org.assertj.swing.test.awt;

import static org.assertj.swing.util.Maps.newHashMap;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.AWTEventListener;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Alex Ruiz
 */
public abstract class ToolkitStub extends Toolkit {
  private Map<AWTEventListener, Long> eventListeners;
  private EventQueue eventQueue;

  static ToolkitStub createNew() {
    return createNew(new EventQueue());
  }

  static ToolkitStub createNew(EventQueue eventQueue) {
    ToolkitStub stub = mock(ToolkitStub.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));
    stub.eventQueue(eventQueue);
    stub.eventListeners = newHashMap();
    return stub;
  }

  public ToolkitStub() {
  }

  public void eventQueue(EventQueue newEventQueue) {
    eventQueue = newEventQueue;
  }

  @Override
  public void addAWTEventListener(AWTEventListener listener, long eventMask) {
    eventListeners().put(listener, eventMask);
  }

  @Override
  public void removeAWTEventListener(AWTEventListener listener) {
    eventListeners().remove(listener);
  }

  public <T extends AWTEventListener> List<T> eventListenersUnderEventMask(long eventMask, Class<T> type) {
    List<T> listeners = new ArrayList<T>();
    for (AWTEventListener listener : eventListeners().keySet()) {
      if (!type.isInstance(listener)) {
        continue;
      }
      long keyEvent = eventListeners().get(listener);
      if (keyEvent != eventMask) {
        continue;
      }
      listeners.add(type.cast(listener));
    }
    return listeners;
  }

  public boolean contains(AWTEventListener listener, long eventMask) {
    if (!eventListeners.containsKey(listener)) {
      return false;
    }
    long storedMask = eventListeners.get(listener);
    return storedMask == eventMask;
  }

  @Override
  protected EventQueue getSystemEventQueueImpl() {
    return eventQueue;
  }

  private Map<AWTEventListener, Long> eventListeners() {
    return eventListeners;
  }

  @Override
  public Insets getScreenInsets(GraphicsConfiguration gc) throws HeadlessException {
    return new Insets(0, 0, 0, 0);
  }

  @Override
  public void beep() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int checkImage(Image image, int width, int height, ImageObserver observer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image createImage(String filename) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image createImage(URL url) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image createImage(ImageProducer producer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image createImage(byte[] imagedata, int imageoffset, int imagelength) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ColorModel getColorModel() throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  @Deprecated
  public String[] getFontList() {
    throw new UnsupportedOperationException();
  }

  @Override
  @Deprecated
  public FontMetrics getFontMetrics(Font font) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image getImage(String filename) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image getImage(URL url) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getScreenResolution() throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Dimension getScreenSize() throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Clipboard getSystemClipboard() throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isModalExclusionTypeSupported(ModalExclusionType modalExclusionType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isModalityTypeSupported(ModalityType modalityType) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Map<TextAttribute, ?> mapInputMethodHighlight(InputMethodHighlight highlight) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean prepareImage(Image image, int width, int height, ImageObserver observer) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void sync() {
    throw new UnsupportedOperationException();
  }
}
