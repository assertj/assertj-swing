/*
 * Created on Apr 3, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.Index.atIndex;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import javax.swing.JTabbedPane;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JTabbedPaneDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneFixture}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneFixture_withMocks_Test {
  private JTabbedPaneFixture fixture;

  @Before
  public void setUp() {
    fixture = new JTabbedPaneFixture(mock(Robot.class), mock(JTabbedPane.class));
    fixture.replaceDriverWith(mock(JTabbedPaneDriver.class));
  }

  @Test
  public void should_return_tab_titles_using_driver() {
    String[] titles = { "One", "Two" };
    JTabbedPaneDriver driver = fixture.driver();
    JTabbedPane target = fixture.target();
    when(driver.tabTitles(target)).thenReturn(titles);
    assertThat(fixture.tabTitles()).isSameAs(titles);
    verify(driver).tabTitles(target);
  }

  @Test
  public void should_call_selectTab_with_index_in_driver_and_return_self() {
    assertThat(fixture.selectTab(6)).isSameAs(fixture);
    verify(fixture.driver()).selectTab(fixture.target(), 6);
  }

  @Test
  public void should_call_selectTab_with_text_in_driver_and_return_self() {
    assertThat(fixture.selectTab("Six")).isSameAs(fixture);
    verify(fixture.driver()).selectTab(fixture.target(), "Six");
  }

  @Test
  public void should_call_selectTab_with_Pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Six");
    assertThat(fixture.selectTab(pattern)).isSameAs(fixture);
    verify(fixture.driver()).selectTab(fixture.target(), pattern);
  }

  @Test
  public void should_call_requireTitle_with_text_in_driver_and_return_self() {
    assertThat(fixture.requireTitle("Hello", atIndex(6))).isSameAs(fixture);
    verify(fixture.driver()).requireTabTitle(fixture.target(), "Hello", atIndex(6));
  }

  @Test
  public void should_call_requireTitle_with_pattern_in_driver_and_return_self() {
    Pattern pattern = Pattern.compile("Hello");
    assertThat(fixture.requireTitle(pattern, atIndex(6))).isSameAs(fixture);
    verify(fixture.driver()).requireTabTitle(fixture.target(), pattern, atIndex(6));
  }

  @Test
  public void should_call_requireTabTitles_in_driver_and_return_self() {
    String[] titles = { "One", "Two" };
    assertThat(fixture.requireTabTitles(titles)).isSameAs(fixture);
    verify(fixture.driver()).requireTabTitles(fixture.target(), titles);
  }
}
