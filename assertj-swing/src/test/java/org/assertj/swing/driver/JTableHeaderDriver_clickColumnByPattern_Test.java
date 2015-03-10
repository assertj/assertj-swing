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

import java.util.regex.Pattern;

import org.assertj.swing.exception.LocationUnavailableException;
import org.assertj.swing.test.recorder.ClickRecorder;
import org.assertj.swing.test.recorder.ClickRecorderManager;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link JTableHeaderDriver#clickColumn(javax.swing.table.JTableHeader, java.util.regex.Pattern)}.
 * 
 * @author Yvonne Wang
 */
public class JTableHeaderDriver_clickColumnByPattern_Test extends JTableHeaderDriver_TestCase {
  @Rule
  public ClickRecorderManager clickRecorder = new ClickRecorderManager();

  @Test
  public void should_Fail_If_Matching_Column_Was_Not_Found() {
    thrown.expect(LocationUnavailableException.class, "Unable to find column with name matching pattern 'hello'");
    driver.clickColumn(tableHeader, Pattern.compile("hello"));
  }

  @Test
  public void should_Click_Column() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tableHeader);
    driver.clickColumn(tableHeader, Pattern.compile("0.*"));
    recorder.wasClicked();
    assertThatColumnWasClicked(recorder, 0);
  }
}
