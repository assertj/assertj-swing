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

import static org.fest.swing.core.TestRobots.singletonRobotMock;
import static org.fest.swing.test.builder.JTables.table;
import static org.mockito.Mockito.mock;

import javax.swing.JTable;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Base test case that uses a mock implementation of {@link JTableCellWriter}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTableDriver_withMockCellWriter_TestCase extends EDTSafeTestCase {
  JTableDriver driver;
  JTableCellWriter cellWriter;
  static JTable table;

  @BeforeClass
  public static void setUpTable() {
    table = table().withRowCount(1).withColumnCount(6).createNew();
  }

  @Before
  public final void setUp() {
    driver = new JTableDriver(singletonRobotMock());
    cellWriter = mock(JTableCellWriter.class);
    driver.replaceCellWriter(cellWriter);
    onSetUp();
  }

  void onSetUp() {}
}
