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

import static org.assertj.swing.data.TableCell.row;

import org.junit.Test;

/**
 * Tests for {@link JTableDriver#requireEditable(javax.swing.JTable, org.assertj.swing.data.TableCell)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_requireEditable_Test extends JTableDriver_TestCase {
  @Test
  public void should_Pass_If_Cell_Is_Editable() {
    makeFirstCellEditable();
    driver.requireEditable(table, row(0).column(0));
  }

  @Test
  public void should_Fail_If_Cell_Is_Not_Editable() {
    makeFirstCellNotEditable();
    thrown.expectAssertionError("property:'editable [row=0, column=0]'");
    thrown.expectMessageToContain("expected:<[tru]e> but was:<[fals]e>");
    driver.requireEditable(table, row(0).column(0));
  }
}
