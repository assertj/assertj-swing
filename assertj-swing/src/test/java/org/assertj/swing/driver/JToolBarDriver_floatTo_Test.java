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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.ComponentLocationQuery.locationOf;

import java.awt.Point;
import java.awt.Window;

import javax.swing.JToolBar;

import org.junit.Test;

/**
 * Tests for {@link JToolBarDriver#floatTo(JToolBar, int, int)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarDriver_floatTo_Test extends JToolBarDriver_TestCase {
  @Test
  public void should_Float_JToolBar() {
    showWindow();
    Window oldAncestor = toolBarAncestor();
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    assertThatToolBarIsFloating(oldAncestor);
  }

  private void assertThatToolBarIsFloating(Window oldAncestor) {
    Window newAncestor = toolBarAncestor();
    assertThat(newAncestor).isNotSameAs(oldAncestor);
    Point newAncestorLocation = locationOf(newAncestor);
    Point oldAncestorLocation = locationOf(oldAncestor);
    assertThat(newAncestorLocation.x).isGreaterThan(oldAncestorLocation.x);
    assertThat(newAncestorLocation.y).isGreaterThan(oldAncestorLocation.y);
  }
}
