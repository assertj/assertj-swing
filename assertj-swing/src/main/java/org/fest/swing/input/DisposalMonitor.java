/*
 * Created on Apr 3, 2008
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
package org.fest.swing.input;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Monitor for disposed {@code Window}s.
 * 
 * @author Alex Ruiz
 */
class DisposalMonitor extends ComponentAdapter {
  final Map<Window, Boolean> disposedWindows;

  DisposalMonitor(@Nonnull Map<Window, Boolean> disposedWindows) {
    this.disposedWindows = disposedWindows;
  }

  @Override
  public void componentShown(ComponentEvent e) {
    Component c = e.getComponent();
    c.removeComponentListener(this); // we are already in EDT
    disposedWindows.remove(c);
  }
}