/*
 * Created on Jul 30, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.util;

import static java.awt.event.ComponentEvent.COMPONENT_HIDDEN;
import static java.awt.event.ComponentEvent.COMPONENT_SHOWN;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLabels.label;

import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;

import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link AWTEvents#wasWindowShown(java.awt.AWTEvent)}.
 * 
 * @author Alex Ruiz
 */
public class AWTEvents_windowShown_Test extends SequentialEDTSafeTestCase {
  private TestWindow source;

  @Override
  protected void onSetUp() {
    source = TestWindow.createNewWindow(getClass());
  }

  @Override
  protected void onTearDown() {
    source.destroy();
  }

  @Test
  public void should_return_true_if_Window_shown() {
    AWTEvent event = new ComponentEvent(source, COMPONENT_SHOWN);
    assertThat(AWTEvents.wasWindowShown(event)).isTrue();
  }

  @Test
  public void should_return_false_if_Component_shown_is_not_Window() {
    AWTEvent event = new ComponentEvent(label().createNew(), COMPONENT_SHOWN);
    assertThat(AWTEvents.wasWindowShown(event)).isFalse();
  }

  @Test
  public void should_return_false_if_Window_not_shown() {
    AWTEvent event = new ComponentEvent(source, COMPONENT_HIDDEN);
    assertThat(AWTEvents.wasWindowShown(event)).isFalse();
  }
}
