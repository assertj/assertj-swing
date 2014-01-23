/*
 * Created on Nov 1, 2007
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JDialogs.dialog;

import java.awt.Component;

import javax.swing.JDialog;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link WindowFilter#recognize(Component)}.
 * 
 * @author Alex Ruiz
 */
public class WindowFilter_recognize_Test extends WindowFilter_TestCase {
  @Test
  public void should_recognize_Component() {
    Component c = button().createNew();
    addToIgnoredMap(c);
    addToImplicitlyIgnoredMap(c);
    recognize(filter, c);
    assertThatNoComponentsAreIgnored();
    assertThatNoComponentsAreImplicitlyIgnored();
  }

  @Test
  public void should_recognize_children_of_shared_invisible_Frame() {
    JDialog dialog = dialog().createNew();
    addToIgnoredMap(dialog);
    addToImplicitlyIgnoredMap(dialog);
    recognize(filter, dialog.getOwner());
    assertThatNoComponentsAreIgnored();
    assertThatNoComponentsAreImplicitlyIgnored();
  }

  @RunsInEDT
  private static void recognize(final WindowFilter filter, final Component c) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        filter.recognize(c);
      }
    });
  }
}
