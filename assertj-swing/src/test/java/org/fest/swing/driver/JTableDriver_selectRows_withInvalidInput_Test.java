/*
 * Created on Dec 22, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.swing.test.builder.JTables.table;
import static org.fest.test.ExpectedException.none;

import javax.swing.JTable;

import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.test.ExpectedException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#selectRows(javax.swing.JTable, int...)} using invalid input.
 *
 * @author Alex Ruiz
 */
public class JTableDriver_selectRows_withInvalidInput_Test extends RobotBasedTestCase {
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
  public void should_throw_error_if_array_of_indices_is_null() {
    thrown.expect(NullPointerException.class);
    driver.selectRows(table, null);
  }

  @Test
  public void should_throw_error_if_array_of_indices_is_empty() {
    thrown.expect(IllegalArgumentException.class);
    driver.selectRows(table, new int[0]);
  }
}
