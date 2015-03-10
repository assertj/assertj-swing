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
package org.assertj.swing.test.recorder;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Maps.newHashMap;
import static org.assertj.core.util.Preconditions.checkNotNull;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * An event listener that records mouse events on a specific component as {@link AWTEventListener} in the
 * {@link Toolkit}.
 * 
 * @author Christian Rösch
 */
public class ToolkitClickRecorder extends AbstractClickRecorder {

  private static Map<ToolkitClickRecorder, ClickListener> recorderListeners = newHashMap();

  ToolkitClickRecorder() {
    // hide the constructor from the outside
  }

  static @Nonnull ToolkitClickRecorder attachTo(@Nonnull Component target) {
    ToolkitClickRecorder recorder = new ToolkitClickRecorder();
    ClickListener listener = new ClickListener(recorder);
    recorderListeners.put(recorder, listener);

    Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);
    attach(listener, target);
    return recorder;
  }

  static void remove(@Nonnull ToolkitClickRecorder recorder) {
    ClickListener listener = recorderListeners.remove(recorder);
    if (listener != null) {
      Toolkit.getDefaultToolkit().removeAWTEventListener(listener);
    }
  }

  private static void attach(@Nonnull final ClickListener listener, @Nonnull final Component target) {
    listener.addToolkitComponent(target);
    if (!(target instanceof Container)) {
      return;
    }
    for (Component c : ((Container) target).getComponents()) {
      attach(listener, checkNotNull(c));
    }
  }

  private static class ClickListener implements AWTEventListener {
    private final ToolkitClickRecorder owner;

    private final List<Component> toolkitComponents = newArrayList();

    ClickListener(@Nonnull ToolkitClickRecorder owner) {
      this.owner = owner;
    }

    public void addToolkitComponent(@Nonnull Component target) {
      toolkitComponents.add(target);
    }

    @Override
    public void eventDispatched(@Nonnull AWTEvent evt) {
      if (toolkitComponents.contains(evt.getSource())) {
        MouseEvent event = (MouseEvent) evt;
        switch (event.getID()) {
        case MouseEvent.MOUSE_PRESSED:
        case MouseEvent.MOUSE_RELEASED:
          owner.record(event);
          break;
        }
      }
    }
  }
}