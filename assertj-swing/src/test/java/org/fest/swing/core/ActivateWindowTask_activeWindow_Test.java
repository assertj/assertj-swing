/*
 * Created on Feb 23, 2008
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
package org.fest.swing.core;

import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.task.FrameShowTask.packAndShow;
import static org.fest.swing.test.task.WindowDestroyTask.hideAndDispose;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Strings.concat;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.timing.Condition;
import org.junit.Test;

/**
 * Tests for {@link ActivateWindowTask#activateWindow(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ActivateWindowTask_activeWindow_Test extends SequentialEDTSafeTestCase {
  private JFrame frameOne;
  private JFrame frameTwo;

  @Override
  protected void onSetUp() {
    Dimension size = new Dimension(300, 200);
    frameOne = frame().withTitle(concat(testName(), " - One")).createNew();
    packAndShow(frameOne, size);
    frameTwo = frame().withTitle(concat(testName(), " - Two")).createNew();
    packAndShow(frameTwo, size);
  }

  private static String testName() {
    return ActivateWindowTask_activeWindow_Test.class.getSimpleName();
  }

  @Override
  protected void onTearDown() {
    hideAndDispose(frameOne);
    hideAndDispose(frameTwo);
  }

  @Test
  public void should_activate_window() {
    pause(new HasFocusCondition(frameTwo));
    ActivateWindowTask.activateWindow(frameOne);
    // verify that frameOne was given focus (i.e. was activated)
    pause(new HasFocusCondition(frameOne));
  }

  private static class HasFocusCondition extends Condition {
    private Component c;

    HasFocusCondition(Component c) {
      super("Component has focus");
      this.c = c;
    }

    @Override
    public boolean test() {
      return c.hasFocus();
    }

    @Override
    protected void done() {
      c = null;
    }
  }
}
