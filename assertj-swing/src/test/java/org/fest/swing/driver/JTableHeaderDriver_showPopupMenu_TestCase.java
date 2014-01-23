/*
 * Created on Aug 13, 2009
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
package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fest.swing.edt.GuiQuery;

/**
 * Base test case for methods in {@link JTableHeaderDriver} that invoke a {@code JPopupMenu}.
 * 
 * @author Yvonne Wang
 */
public abstract class JTableHeaderDriver_showPopupMenu_TestCase extends JTableHeaderDriver_TestCase {
  JPopupMenu popupMenu;

  @Override
  final void extraSetUp() {
    popupMenu = execute(new GuiQuery<JPopupMenu>() {
      @Override
      protected JPopupMenu executeInEDT() {
        JPopupMenu p = new JPopupMenu();
        p.add(new JMenuItem("Frodo"));
        tableHeader.setComponentPopupMenu(p);
        return p;
      }
    });
    robot.waitForIdle();
  }
}
