/*
 * Created on Nov 12, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static java.awt.event.WindowEvent.WINDOW_OPENED;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link TransientWindowListener#eventDispatched(AWTEvent)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class TransientWindowListener_eventDispatched_withWindowOpenedAndShownEvents_Test extends
TransientWindowListener_eventDispatched_TestCase {
  private final int eventId;

  @Parameters
  public static Collection<Object[]> componentsAndEvents() {
    return newArrayList(new Object[][] {
        { WINDOW_OPENED }, { COMPONENT_SHOWN }
      });
  }

  public TransientWindowListener_eventDispatched_withWindowOpenedAndShownEvents_Test(int eventId) {
    this.eventId = eventId;
  }

  @Test
  public void should_recognize_Window_if_it_is_implicitly_ignored() {
    when(windowFilter.isImplicitlyIgnored(eventSource)).thenReturn(true);
    listener.eventDispatched(event());
    verify(windowFilter).recognize(eventSource);
  }

  @Test
  public void should_ignore_Window_if_parent_is_ignored() {
    when(windowFilter.isImplicitlyIgnored(eventSource)).thenReturn(false);
    when(windowFilter.isIgnored(parent)).thenReturn(true);
    listener.eventDispatched(event());
    verify(windowFilter).ignore(eventSource);
  }

  private AWTEvent event() {
    return eventId == WINDOW_OPENED ? new WindowEvent(eventSource, eventId) : new ComponentEvent(eventSource, eventId);
  }
}
