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

import org.assertj.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JTableHeaderDriver#clickColumn(javax.swing.table.JTableHeader, String)}.
 * 
 * @author Yvonne Wang
 */
public class JTableHeaderDriver_clickColumnByName_Test extends JTableHeaderDriver_TestCase {
  @Test
  public void should_Fail_If_Matching_Column_Was_Not_Found() {
    thrown.expect(LocationUnavailableException.class, "Unable to find column with name matching value 'hello'");
    driver.clickColumn(tableHeader, "hello");
  }

  @Test
  public void should_Throw_Error_If_JTableHeader_Is_Disabled() {
    disableTableHeader();
    thrown.expectIllegalStateIsDisabledComponent();
    driver.clickColumn(tableHeader, "0");
  }

  @Test
  public void should_Throw_Error_If_JTableHeader_Is_Not_Showing_On_The_Screen() {
    thrown.expectIllegalStateIsNotShowingComponent();
    driver.clickColumn(tableHeader, "0");
  }
}
