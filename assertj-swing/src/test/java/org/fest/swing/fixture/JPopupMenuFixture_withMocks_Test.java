/*
 * Created on Sep 5, 2007
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
import static org.fest.swing.test.core.NeverMatchingComponentMatcher.neverMatches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JPopupMenuDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JPopupMenuFixture}.
 *
 * @author Yvonne Wang
 */
public class JPopupMenuFixture_withMocks_Test {
  private JPopupMenuDriver driver;
  private JPopupMenu target;

  private JPopupMenuFixture fixture;

  @Before
  public void setUp() {
    fixture = new JPopupMenuFixture(mock(Robot.class), mock(JPopupMenu.class));
    fixture.replaceDriverWith(mock(JPopupMenuDriver.class));
    driver = fixture.driver();
    target = fixture.target();
  }

  @Test
  public void should_find_JMenuItem_with_name_using_driver() {
    JMenuItem menuItem = mock(JMenuItem.class);
    when(driver.menuItem(target, "File")).thenReturn(menuItem);
    JMenuItemFixture menuItemFixture = fixture.menuItem("File");
    assertThat(menuItemFixture.target()).isSameAs(menuItem);
    verify(driver).menuItem(target, "File");
  }

  @Test
  public void should_find_JMenuItem_with_matcher_using_driver() {
    GenericTypeMatcher<JMenuItem> matcher = neverMatches(JMenuItem.class);
    JMenuItem menuItem = mock(JMenuItem.class);
    when(driver.menuItem(target, matcher)).thenReturn(menuItem);
    JMenuItemFixture menuItemFixture = fixture.menuItem(matcher);
    assertThat(menuItemFixture.target()).isSameAs(menuItem);
    verify(driver).menuItem(target, matcher);
  }

  @Test
  public void should_return_menu_labels_using_driver() {
    String[] labels = { "One", "Two" };
    when(driver.menuLabelsOf(target)).thenReturn(labels);
    assertThat(fixture.menuLabels()).isSameAs(labels);
    verify(driver).menuLabelsOf(target);
  }
}
