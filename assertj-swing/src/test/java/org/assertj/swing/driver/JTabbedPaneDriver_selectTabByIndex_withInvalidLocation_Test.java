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

import static org.assertj.swing.driver.JTabbedPaneSelectTabTask.setSelectedTab;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneDriver#selectTab(javax.swing.JTabbedPane, int)}.
 * 
 * @author Alex Ruiz
 */
public class JTabbedPaneDriver_selectTabByIndex_withInvalidLocation_Test extends JTabbedPaneDriver_TestCase {
  @Test
  public void should_Select_Tab_Directly_If_Tab_Location_Not_Found() {
    showWindow();
    selectFirstTab();
    JTabbedPaneLocation location = mock(JTabbedPaneLocation.class);
    int index = 1;
    driver = new JTabbedPaneDriver(robot, location);
    when(location.pointAt(tabbedPane, index)).thenThrow(new LocationUnavailableException("Thrown on purpose"));
    driver.selectTab(tabbedPane, index);
    assertThatSelectedTabIndexIs(index);
    verify(location).checkIndexInBounds(tabbedPane, index); // everything goes fine.
  }

  private void selectFirstTab() {
    setSelectedTab(tabbedPane, 0);
    robot.waitForIdle();
  }
}
