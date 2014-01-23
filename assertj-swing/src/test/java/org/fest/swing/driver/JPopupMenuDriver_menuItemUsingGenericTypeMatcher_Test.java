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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.AbstractButtonTextQuery.textOf;

import javax.annotation.Nonnull;
import javax.swing.JMenuItem;

import org.fest.swing.core.GenericTypeMatcher;
import org.junit.Test;

/**
 * Tests for {@link JPopupMenuDriver#menuItem(javax.swing.JPopupMenu, org.fest.swing.core.GenericTypeMatcher)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JPopupMenuDriver_menuItemUsingGenericTypeMatcher_Test extends JPopupMenuDriver_TestCase {
  @Test
  public void should_find_JMenuItems_with_GenericTypeMatcher() {
    JMenuItem found = driver.menuItem(popupMenu, new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
      @Override
      protected boolean isMatching(@Nonnull JMenuItem menuItem) {
        return "Second".equals(textOf(menuItem));
      }
    });
    assertThat(found).isSameAs(window.menuItem2);
  }
}
