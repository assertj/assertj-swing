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

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.awt.Point;
import java.awt.Window;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JToolBarDriver#unfloat(javax.swing.JToolBar, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JToolBarDriver_unfloatWithConstraint_Test extends JToolBarDriver_TestCase {
  private final String constraint;

  @Parameters
  public static Collection<Object[]> unfloatConstraints() {
    return newArrayList(new Object[][] {
        { NORTH }, { EAST }, { SOUTH }, { WEST }
      });
  }

  public JToolBarDriver_unfloatWithConstraint_Test(String constraint) {
    this.constraint = constraint;
  }

  @Test
  public void should_unfloat_JToolbar() {
    showWindow();
    Window originalAncestor = toolBarAncestor();
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    driver.unfloat(toolBar, constraint);
    assertThat(toolBarAncestor()).isSameAs(originalAncestor);
    assertThat(window.componentAt(constraint)).isSameAs(toolBar);
  }
}
