/*
 * Created on Aug 16, 2009
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

import static org.fest.swing.test.task.ComponentSetPopupMenuTask.createAndSetPopupMenu;

import javax.swing.JPopupMenu;

/**
 * Base test case for methods in {@link JListDriver} that invoke a {@code JPopupMenu} in a {@code JList}.
 * 
 * @author Alex Ruiz
 */
public abstract class JListDriver_showPopupMenu_TestCase extends JListDriver_TestCase {
  JPopupMenu popupMenu;

  @Override
  void extraSetUp() {
    popupMenu = createAndSetPopupMenu(list, "Frodo");
    robot.waitForIdle();
  }
}
