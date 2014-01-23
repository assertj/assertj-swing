/*
 * Created on Jul 26, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.fest.swing.exception.ComponentLookupException;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#showPopupMenu(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_showPopupMenu_Test extends BasicRobot_TestCase {
  @Test
  public void should_show_popupMenu() {
    JPopupMenu popupMenu = addPopupMenuToTextField();
    JTextField textField = window().textField();
    robot().click(textField);
    JPopupMenu found = robot().showPopupMenu(textField);
    assertThat(found).isSameAs(popupMenu);
    assertThat(found.isVisible()).isTrue();
  }

  @Test
  public void should_throw_error_if_popupMenu_not_found() {
    try {
      robot().showPopupMenu(window().textField());
      failWhenExpectingException();
    } catch (ComponentLookupException expected) {
      assertThat(expected.getMessage()).contains("Unable to show popup").contains("on javax.swing.JTextField");
    }
  }
}
