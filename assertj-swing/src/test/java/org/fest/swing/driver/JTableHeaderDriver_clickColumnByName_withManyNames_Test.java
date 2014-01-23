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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.recorder.ClickRecorder.attachTo;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.fest.swing.test.recorder.ClickRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTableHeaderDriver#clickColumn(javax.swing.table.JTableHeader, String)}.
 *
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTableHeaderDriver_clickColumnByName_withManyNames_Test extends JTableHeaderDriver_TestCase {
  private final String name;
  private final int index;

  @Parameters
  public static Collection<Object[]> indices() {
    return newArrayList(columnIndices());
  }

  public JTableHeaderDriver_clickColumnByName_withManyNames_Test(int index) {
    name = columnNameFromIndex(index);
    this.index = index;
  }

  @Test
  public void should_click_column() {
    showWindow();
    ClickRecorder recorder = attachTo(tableHeader);
    driver.clickColumn(tableHeader, name);
    assertThat(recorder).wasClicked();
    assertThatColumnWasClicked(recorder, index);
  }
}
