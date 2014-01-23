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

import java.awt.Point;
import java.awt.Window;

import javax.swing.JToolBar;

import org.junit.Test;

/**
 * Tests for {@link JToolBarDriver#unfloat(JToolBar)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarDriver_unfloat_Test extends JToolBarDriver_TestCase {
  @Test
  public void should_unfloat_JToolbar() {
    showWindow();
    Window oldAncestor = toolBarAncestor();
    Point where = whereToFloatTo();
    driver.floatTo(toolBar, where.x, where.y);
    driver.unfloat(toolBar);
    assertThat(toolBarAncestor()).isSameAs(oldAncestor);
  }
}
