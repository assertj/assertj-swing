/*
 * Created on May 22, 2009
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
package org.fest.swing.input;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLabels.label;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link NativeDndIdentifier#isNativeDragAndDrop(java.awt.AWTEvent)}.
 * 
 * @author Alex Ruiz
 */
public class NativeDnDIdentifier_isNativeDragAndDrop_Test extends EDTSafeTestCase {
  private NativeDndIdentifier dnd;

  @Before
  public void setUp() {
    FailOnThreadViolationRepaintManager.install();
    dnd = new NativeDndIdentifier();
  }

  @Test
  public void should_return_true_if_event_is_mouse_event_and_its_class_name_is_SunDropTargetEvent() {
    assertThat(dnd.isNativeDragAndDrop(new SunDropTargetEvent())).isTrue();
  }

  @Test
  public void should_return_false_if_event_is_not_mouse_event() {
    KeyEvent e = new KeyEvent(label().createNew(), 0, 0, 0, 0, 'a');
    assertThat(dnd.isNativeDragAndDrop(e)).isFalse();
  }

  @Test
  public void should_return_false_if_event_class_name_is_not_SunDropTargetEvent() {
    MouseEvent e = new MouseEvent(label().createNew(), 0, 0, 0, 0, 0, 0, false);
    assertThat(dnd.isNativeDragAndDrop(e)).isFalse();
  }

  static class SunDropTargetEvent extends MouseEvent {
    SunDropTargetEvent() {
      super(label().createNew(), 0, 0, 0, 0, 0, 0, false);
    }
  }
}
