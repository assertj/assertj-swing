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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Maps.newHashMap;

import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.util.Map;

import javax.swing.JFrame;

import org.fest.swing.test.swing.TestWindow;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link DisposalMonitor#componentShown(ComponentEvent)}.
 * 
 * @author Alex Ruiz
 */
public class DisposalMonitor_componentShown_Test {
  private JFrame frame;
  private Map<Window, Boolean> disposedWindows;
  private DisposalMonitor monitor;

  @Before
  public void setUp() {
    frame = TestWindow.createNewWindow(DisposalMonitor_componentShown_Test.class);
    disposedWindows = newHashMap();
    monitor = new DisposalMonitor(disposedWindows);
    frame.addComponentListener(monitor);
  }

  @Test
  public void should_remove_Component_when_shown() {
    disposedWindows.put(frame, true);
    monitor.componentShown(new ComponentEvent(frame, 0));
    assertThat(disposedWindows).isEmpty();
    assertThat(frame.getComponentListeners()).isEmpty();
  }
}
