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
package org.assertj.swing.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestMdiWindow;
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
  public void should_Return_Null_If_JDesktopIcon_In_JInternalFrame_Is_Null() {
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
  public void should_Return_JDesktopPane_From_JDesktopIcon() {
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
