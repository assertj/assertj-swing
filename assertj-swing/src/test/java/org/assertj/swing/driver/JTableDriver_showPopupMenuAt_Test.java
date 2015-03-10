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
import static org.assertj.swing.core.MouseButton.RIGHT_BUTTON;
import static org.assertj.swing.data.TableCell.row;
import static org.assertj.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;

import javax.swing.JPopupMenu;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#showPopupMenuAt(javax.swing.JTable, org.assertj.swing.data.TableCell)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_showPopupMenuAt_Test extends JTableDriver_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  private JPopupMenu popupMenu;

  @Override
  void extraSetUp() {
    popupMenu = createAndSetPopupMenu(table, "Leia");
  }

  @Test
  public void should_Show_Popup_Menu() {
    showWindow();
    driver.click(table);
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(table);
    TableCell cell = row(0).column(1);
    JPopupMenu found = driver.showPopupMenuAt(table, cell);
    assertThat(found).isSameAs(popupMenu);
    recorder.clicked(RIGHT_BUTTON).timesClicked(1);
    assertThatCellWasClicked(cell, recorder.pointClicked());
  }

  @Test
  public void should_Throw_Error_If_JTable_Is_Disabled() {
    disableTable();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.showPopupMenuAt(table, row(0).column(0));
  }

  @Test
  public void should_Throw_Error_If_JTable_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.showPopupMenuAt(table, row(0).column(0));
  }
}
