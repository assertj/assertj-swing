/*
 * Created on Mar 16, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.swing.query.ComponentVisibleQuery.isVisible;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link JTableHeaderDriver#showPopupMenu(javax.swing.table.JTableHeader, int)}.
 *
 * @author Yvonne Wang
 */
public class JTableHeaderDriver_showPopupMenuByIndex_Test extends JTableHeaderDriver_showPopupMenu_TestCase {
  @Test
  public void should_show_popup_menu() {
    showWindow();
    driver.click(tableHeader);
    ClickRecorder recorder = attachTo(tableHeader);
    driver.showPopupMenu(tableHeader, 1);
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    assertThatColumnWasClicked(recorder, 1);
    assertThat(isVisible(popupMenu)).isTrue();
  }
}
