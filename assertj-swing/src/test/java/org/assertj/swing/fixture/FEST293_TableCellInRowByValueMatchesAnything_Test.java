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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.swing.data.TableCellInRowByValue.rowWithValue;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-293" target="_blank">FEST-293</a>.
 * 
 * @author Alex Ruiz
 */
public class FEST293_TableCellInRowByValueMatchesAnything_Test extends RobotBasedTestCase {
  private FrameFixture frame;

  @Override
  protected void onSetUp() {
    frame = new FrameFixture(robot, MyWindow.createNew());
    frame.show();
  }

  @Test
  public void should_Return_Matching_Cell() {
    JTableCellFixture cell = frame.table().cell(rowWithValue("billy", "willy", "alone").column(2));
    assertThat(cell.row()).isEqualTo(1);
  }

  @Test(expected = ActionFailedException.class)
  public void should_Throw_Error_If_Matching_Cell_Not_Found() {
    frame.table().cell(rowWithValue("billy", "billy", "billy").column(2));
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return GuiActionRunner.execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(FEST293_TableCellInRowByValueMatchesAnything_Test.class);
      Object[][] data = { { "billy", "willy", "dilly" }, { "billy", "willy", "alone" }, { "billy", "all", "alone" } };
      JTable table = new JTable(data, array("name1", "name2", "name3"));
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setPreferredSize(new Dimension(300, 100));
      add(scrollPane);
    }
  }
}
