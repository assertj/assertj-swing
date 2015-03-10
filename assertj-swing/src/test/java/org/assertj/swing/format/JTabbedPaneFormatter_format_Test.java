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
package org.assertj.swing.format;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JTabbedPanes.tabbedPane;

import javax.swing.JTabbedPane;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneFormatter#format(java.awt.Component)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTabbedPaneFormatter_format_Test extends EDTSafeTestCase {
  private JTabbedPaneFormatter formatter;

  @Before
  public void setUp() {
    formatter = new JTabbedPaneFormatter();
  }

  @Test
  public void should_Format_JTabbedPane_With_Tabs_And_Selection() {
    JTabbedPane tabbedPane = tabbedPane().withName("tabbedPane").withTabs("One", "Two").withSelection(1).createNew();
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains("javax.swing.JTabbedPane").contains("name='tabbedPane'")
        .contains("selectedTabIndex=1").contains("selectedTabTitle='Two'").contains("tabCount=2")
        .contains("tabTitles=[\"One\", \"Two\"]").contains("enabled=true").contains("visible=true")
        .contains("showing=false");
  }

  @Test
  public void should_Format_JTabbedPane_With_Tabs_And_Without_Selection() {
    JTabbedPane tabbedPane = tabbedPane().withName("tabbedPane").withTabs("One", "Two").createNew();
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains("javax.swing.JTabbedPane").contains("name='tabbedPane'")
        .contains("selectedTabIndex=-1").contains("selectedTabTitle=<No selection>").contains("tabCount=2")
        .contains("tabTitles=[\"One\", \"Two\"]").contains("enabled=true").contains("visible=true")
        .contains("showing=false");
  }

  @Test
  public void should_Format_JTabbedPane_Without_Tabs() {
    JTabbedPane tabbedPane = tabbedPane().withName("tabbedPane").createNew();
    String formatted = formatter.format(tabbedPane);
    assertThat(formatted).contains("javax.swing.JTabbedPane").contains("name='tabbedPane'")
        .contains("selectedTabIndex=-1").contains("selectedTabTitle=<No selection>").contains("tabCount=0")
        .contains("tabTitles=[]").contains("enabled=true").contains("visible=true").contains("showing=false");
  }
}
