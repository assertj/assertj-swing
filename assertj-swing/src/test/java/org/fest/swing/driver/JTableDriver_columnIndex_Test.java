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
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTableDriver#columnIndex(javax.swing.JTable, Object)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTableDriver_columnIndex_Test extends JTableDriver_TestCase {
  private final String columnId;

  @Parameters
  public static Collection<Object[]> ids() {
    return newArrayList(columnIds());
  }

  public JTableDriver_columnIndex_Test(String columnId) {
    this.columnId = columnId;
  }

  @Test
  public void should_return_column_index() {
    assertThat(driver.columnIndex(table, columnId)).isEqualTo(columnIndexFrom(columnId));
  }
}
