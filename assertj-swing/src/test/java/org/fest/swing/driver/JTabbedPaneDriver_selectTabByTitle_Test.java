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

import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JTabbedPaneDriver#selectTab(javax.swing.JTabbedPane, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTabbedPaneDriver_selectTabByTitle_Test extends JTabbedPaneDriver_TestCase {
  @Test
  public void should_select_matching_tab() {
    showWindow();
    driver.selectTab(tabbedPane, "Tw.*");
    assertThatSelectedTabIndexIs(1);
  }

  @Test
  public void should_throw_error_if_JTabbedPane_is_disabled() {
    disableTabbedPane();
    try {
      driver.selectTab(tabbedPane, "Two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JTabbedPane_is_not_showing_on_the_screen() {
    try {
      driver.selectTab(tabbedPane, "Two");
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }
}
