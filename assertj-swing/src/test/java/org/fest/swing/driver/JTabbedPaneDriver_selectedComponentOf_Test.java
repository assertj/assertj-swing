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

import java.awt.Component;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTabbedPaneDriver#selectedComponentOf(javax.swing.JTabbedPane)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTabbedPaneDriver_selectedComponentOf_Test extends JTabbedPaneDriver_TestCase {
  private final int index;
  private final String componentName;

  @Parameters
  public static Collection<Object[]> indices() {
    return newArrayList(new Object[][] {
        { 0, "panel1" },
        { 1, "panel2" }
      });
  }

  public JTabbedPaneDriver_selectedComponentOf_Test(int index, String componentName) {
    this.index = index;
    this.componentName = componentName;
  }

  @Test
  public void shouldReturnSelectedComponent() {
    showWindow();
    driver.selectTab(tabbedPane, index);
    Component selected = driver.selectedComponentOf(tabbedPane);
    assertThat(selected.getName()).isEqualTo(componentName);
  }
}
