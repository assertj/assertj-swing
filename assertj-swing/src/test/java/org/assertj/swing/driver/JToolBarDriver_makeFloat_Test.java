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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;

import java.awt.Window;

import javax.swing.JToolBar;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JToolBarDriver#makeFloat(JToolBar)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarDriver_makeFloat_Test extends JToolBarDriver_TestCase {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_Throw_Error_If_JToolBar_Is_Not_Floatable() {
    makeNotFloatable();
    thrown.expect(IllegalStateException.class, "is not floatable");
    driver.makeFloat(toolBar);
  }

  @RunsInEDT
  private void makeNotFloatable() {
    setFloatable(toolBar, false);
    robot.waitForIdle();
    showWindow();
  }

  @RunsInEDT
  private static void setFloatable(final JToolBar toolBar, final boolean flotable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        toolBar.setFloatable(flotable);
      }
    });
  }

  @RunsInEDT
  public void should_Float_JToolbar() {
    Window oldAncestor = toolBarAncestor();
    driver.makeFloat(toolBar);
    Window newAncestor = toolBarAncestor();
    assertThat(newAncestor).isNotSameAs(oldAncestor);
  }
}
