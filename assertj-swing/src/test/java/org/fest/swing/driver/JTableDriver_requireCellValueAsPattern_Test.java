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
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Tests for {@link JTableDriver#requireCellValue(javax.swing.JTable, org.fest.swing.data.TableCell, Pattern)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_requireCellValueAsPattern_Test extends JTableDriver_TestCase {
  @Test
  public void should_pass_if_cell_value_matches_pattern() {
    driver.requireCellValue(table, row(0).column(0), Pattern.compile("0.*"));
  }

  @Test
  public void should_fail_if_cell_value_does_not_match_pattern() {
    try {
      driver.requireCellValue(table, row(0).column(0), Pattern.compile("0-1"));
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e.getMessage()).contains("property:'value [row=0, column=0]'").contains(
          "actual value:<'0-0'> does not match pattern:<'0-1'>");
    }
  }
}
