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

import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.builder.JTables.table;

import javax.swing.JTable;

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#unselectRows(javax.swing.JTable, int...)} using invalid input.
 *
 * @author Christian Rösch
 */
public class JTableDriver_unselectRows_withInvalidInput_Test extends RobotBasedTestCase {
  @Rule
  public ExpectedException thrown = none();

  private static JTable table;
  private JTableDriver driver;

  @BeforeClass
  public static void setUpOnce() {
    table = table().withRowCount(6).withColumnCount(8).createNew();
  }

  @Override
  protected void onSetUp() {
    driver = new JTableDriver(robot);
  }

  @Test
  public void should_Throw_Error_If_Array_Of_Indices_Is_Null() {
    thrown.expect(NullPointerException.class);
    driver.unselectRows(table, null);
  }

  @Test
  public void should_Throw_Error_If_Array_Of_Indices_Is_Empty() {
    thrown.expect(IllegalArgumentException.class);
    driver.unselectRows(table, new int[0]);
  }
}
