/*
 * Created on Mar 16, 2008
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.util.regex.Pattern;

import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.test.recorder.ClickRecorder;
import org.fest.swing.test.recorder.ClickRecorderManager;
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
  public void should_fail_if_matching_column_was_not_found() {
    try {
      driver.clickColumn(tableHeader, Pattern.compile("hello"));
      failWhenExpectingException();
    } catch (LocationUnavailableException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to find column with name matching pattern 'hello'");
    }
  }

  @Test
  public void should_click_column() {
    showWindow();
    ClickRecorder recorder = clickRecorder.attachDirectlyTo(tableHeader);
    driver.clickColumn(tableHeader, Pattern.compile("0.*"));
    recorder.wasClicked();
    assertThatColumnWasClicked(recorder, 0);
  }
}
