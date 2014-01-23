/*
 * Created on Mar 28, 2008
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

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.awt.event.MouseEvent.MOUSE_DRAGGED;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import static java.awt.event.MouseEvent.MOUSE_EXITED;
import static java.awt.event.MouseEvent.MOUSE_WHEEL;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;
import static org.fest.util.Lists.newArrayList;

import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JComboBox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link DragDropInfo#update(java.awt.event.MouseEvent)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class DragDropInfo_update_withUnrecognizedEvents_Test extends DragDropInfo_TestCase {
  private final int eventMask;

  @Parameters
  public static Collection<Object[]> eventMasks() {
    return newArrayList(new Object[][] {
        { MOUSE_CLICKED }, { MOUSE_DRAGGED }, { MOUSE_ENTERED }, { MOUSE_EXITED }, { MOUSE_WHEEL }
      });
  }

  public DragDropInfo_update_withUnrecognizedEvents_Test(int eventMask) {
    this.eventMask = eventMask;
  }

  @Test
  public void should_not_update_for_unrecognized_events() {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, eventMask, when, 0, 0, 0, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isSameAs(source);
    assertThat(info.origin()).isEqualTo(origin);
  }
}
