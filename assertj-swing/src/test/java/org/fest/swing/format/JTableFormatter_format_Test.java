/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTables.table;

import javax.swing.JTable;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTableFormatter#format(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableFormatter_format_Test extends EDTSafeTestCase {
  private JTable table;
  private JTableFormatter formatter;

  @Before
  public void setUp() {
    table = table().withRowCount(8).withColumnCount(6).withName("table").withSelectionMode(MULTIPLE_INTERVAL_SELECTION)
        .createNew();
    formatter = new JTableFormatter();
  }

  @Test
  public void should_format_JTable() {
    String formatted = formatter.format(table);
    assertThat(formatted).contains("javax.swing.JTable")
                         .contains("name='table'")
                         .contains("rowCount=8")
                         .contains("columnCount=6")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
