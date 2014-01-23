/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestMdiWindow;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDesktopPaneQuery#desktopPaneOf(JInternalFrame)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDesktopPaneQuery_desktopPaneOf_Test extends SequentialEDTSafeTestCase {
  private TestMdiWindow window;
  private JInternalFrame internalFrame;

  @Override
  protected final void onSetUp() {
    window = TestMdiWindow.createAndShowNewWindow(getClass());
    internalFrame = window.internalFrame();
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  @Test
  public void should_return_null_if_JDesktopIcon_in_JInternalFrame_is_null() {
    JDesktopPane desktopPane = setNullIconAndReturnDesktopPane(internalFrame);
    assertThat(desktopPane).isNull();
  }

  @RunsInEDT
  private static JDesktopPane setNullIconAndReturnDesktopPane(final JInternalFrame internalFrame) {
    JDesktopPane desktopPane = execute(new GuiQuery<JDesktopPane>() {
      @Override
      protected JDesktopPane executeInEDT() {
        internalFrame.setDesktopIcon(null);
        return JInternalFrameDesktopPaneQuery.desktopPaneOf(internalFrame);
      }
    });
    return desktopPane;
  }

  @Test
  public void should_return_JDesktopPane_from_JDesktopIcon() {
    JDesktopPane desktopPane = desktopPaneOf(internalFrame);
    assertThat(desktopPane).isSameAs(window.desktop());
  }

  @RunsInEDT
  private static JDesktopPane desktopPaneOf(final JInternalFrame internalFrame) {
    return execute(new GuiQuery<JDesktopPane>() {
      @Override
      protected JDesktopPane executeInEDT() {
        return JInternalFrameDesktopPaneQuery.desktopPaneOf(internalFrame);
      }
    });
  }
}
