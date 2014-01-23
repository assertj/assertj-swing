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
import static java.awt.event.MouseEvent.MOUSE_MOVED;
import static java.awt.event.MouseEvent.MOUSE_PRESSED;
import static java.awt.event.MouseEvent.MOUSE_RELEASED;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JComboBoxes.comboBox;

import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import org.junit.Test;

/**
 * Tests for {@link DragDropInfo#update(java.awt.event.MouseEvent)}.
 * 
 * @author Alex Ruiz
 */
public class DragDropInfo_update_Test extends DragDropInfo_TestCase {

  @Test
  public void should_update_source_and_x_y_coordinates_on_mouse_pressed() {
    MouseEvent event = new MouseEvent(source, MOUSE_PRESSED, when, 0, origin.x, origin.y, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isSameAs(source);
    assertThat(info.origin()).isEqualTo(origin);
  }

  @Test
  public void should_clear_source_on_mouse_released() {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, MOUSE_RELEASED, when, 0, 7, 9, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isNull();
  }

  @Test
  public void should_clear_source_on_mouse_moved() {
    info.source(source);
    info.origin(origin);
    JComboBox c = comboBox().createNew();
    MouseEvent event = new MouseEvent(c, MOUSE_MOVED, when, 0, 7, 9, 1, false, BUTTON1);
    info.update(event);
    assertThat(info.source()).isNull();
  }
}
