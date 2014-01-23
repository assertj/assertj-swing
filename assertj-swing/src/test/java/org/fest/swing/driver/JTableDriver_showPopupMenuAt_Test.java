/*
 * Created on Feb 25, 2008
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
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;

import javax.swing.JPopupMenu;

import org.fest.swing.data.TableCell;
import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#showPopupMenuAt(javax.swing.JTable, org.fest.swing.data.TableCell)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_showPopupMenuAt_Test extends JTableDriver_TestCase {
  private JPopupMenu popupMenu;

  @Override
  void extraSetUp() {
    popupMenu = createAndSetPopupMenu(table, "Leia");
  }

  @Test
  public void should_show_popup_menu() {
    showWindow();
    driver.click(table);
    ClickRecorder recorder = attachTo(table);
    TableCell cell = row(0).column(1);
    JPopupMenu found = driver.showPopupMenuAt(table, cell);
    assertThat(found).isSameAs(popupMenu);
    assertThat(recorder).clicked(RIGHT_BUTTON).timesClicked(1);
    assertThatCellWasClicked(cell, recorder.pointClicked());
  }

  @Test
  public void should_throw_error_if_JTable_is_disabled() {
    disableTable();
    try {
      driver.showPopupMenuAt(table, row(0).column(0));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTable_is_not_showing_on_the_screen() {
    try {
      driver.showPopupMenuAt(table, row(0).column(0));
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
