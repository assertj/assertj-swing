/*
 * Created on Jul 24, 2009
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
package org.fest.swing.awt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.Component;
import java.awt.Point;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link AWT#centerOf(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class AWT_centerOfComponent_Test extends EDTSafeTestCase {
  @Test
  public void should_return_center_position() {
    Component c = textField().withColumns(20).createNew();
    setComponentSize(c, 80, 60);
    Point center = AWT.centerOf(c);
    assertThat(center.x).isEqualTo(40);
    assertThat(center.y).isEqualTo(30);
  }

  @RunsInEDT
  private static void setComponentSize(final Component c, final int width, final int height) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        c.setSize(width, height);
      }
    });
  }
}
