/*
 * Created on Mar 22, 2008
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
package org.fest.swing.test.awt;

import static org.fest.util.Maps.newHashMap;
import static org.mockito.Mockito.spy;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.PrintJob;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.event.AWTEventListener;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.peer.ButtonPeer;
import java.awt.peer.CanvasPeer;
import java.awt.peer.CheckboxMenuItemPeer;
import java.awt.peer.CheckboxPeer;
import java.awt.peer.ChoicePeer;
import java.awt.peer.DesktopPeer;
import java.awt.peer.DialogPeer;
import java.awt.peer.FileDialogPeer;
import java.awt.peer.FontPeer;
import java.awt.peer.FramePeer;
import java.awt.peer.LabelPeer;
import java.awt.peer.ListPeer;
import java.awt.peer.MenuBarPeer;
import java.awt.peer.MenuItemPeer;
import java.awt.peer.MenuPeer;
import java.awt.peer.PanelPeer;
import java.awt.peer.PopupMenuPeer;
import java.awt.peer.ScrollPanePeer;
import java.awt.peer.ScrollbarPeer;
import java.awt.peer.TextAreaPeer;
import java.awt.peer.TextFieldPeer;
import java.awt.peer.WindowPeer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Alex Ruiz
 */
public class ToolkitStub extends Toolkit {
  private Map<AWTEventListener, Long> eventListeners;
  private EventQueue eventQueue;

  static ToolkitStub createNew() {
    return createNew(new EventQueue());
  }

  static ToolkitStub createNew(EventQueue eventQueue) {
    ToolkitStub stub = spy(new ToolkitStub());
    stub.eventQueue(eventQueue);
    stub.eventListeners = newHashMap();
    return stub;
  }

  public ToolkitStub() {}

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
  protected ButtonPeer createButton(Button target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected CanvasPeer createCanvas(Canvas target) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected CheckboxPeer createCheckbox(Checkbox target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected ChoicePeer createChoice(Choice target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected DesktopPeer createDesktopPeer(Desktop target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected DialogPeer createDialog(Dialog target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) throws InvalidDnDOperationException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected FileDialogPeer createFileDialog(FileDialog target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected FramePeer createFrame(Frame target) throws HeadlessException {
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
  protected LabelPeer createLabel(Label target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected ListPeer createList(java.awt.List target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected MenuPeer createMenu(Menu target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected MenuBarPeer createMenuBar(MenuBar target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected MenuItemPeer createMenuItem(MenuItem target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected PanelPeer createPanel(Panel target) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected PopupMenuPeer createPopupMenu(PopupMenu target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected ScrollPanePeer createScrollPane(ScrollPane target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected ScrollbarPeer createScrollbar(Scrollbar target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected TextAreaPeer createTextArea(TextArea target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected TextFieldPeer createTextField(TextField target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected WindowPeer createWindow(Window target) throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ColorModel getColorModel() throws HeadlessException {
    throw new UnsupportedOperationException();
  }

  @Override @Deprecated
  public String[] getFontList() {
    throw new UnsupportedOperationException();
  }

  @Override @Deprecated
  public FontMetrics getFontMetrics(Font font) {
    throw new UnsupportedOperationException();
  }

  @Override @Deprecated
  protected FontPeer getFontPeer(String name, int style) {
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
