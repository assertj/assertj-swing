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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#findActivePopupMenu()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class BasicRobot_findActivePopupMenu_Test extends BasicRobot_TestCase {
  @RunsInEDT
  final JPopupMenu addPopupMenuToTextFieldWithCascade() {
    return createAndSetPopupMenuWithCascade(window().textField(), "Luke", "Leia");
  }

  @RunsInEDT
  public static JPopupMenu createAndSetPopupMenuWithCascade(final JComponent c, final String itemOne,
                                                            final String itemTwo) {
    return execute(new GuiQuery<JPopupMenu>() {
      @Override
      protected JPopupMenu executeInEDT() {
        JPopupMenu popupMenu = new JPopupMenu();

        JPopupMenu innerMenu = new JPopupMenu("cascade test");
        innerMenu.add(new JMenuItem("cascade line 1"));
        innerMenu.add(new JMenuItem("cascade line 2"));

        popupMenu.add(new JMenuItem(itemOne));
        popupMenu.add(innerMenu);
        popupMenu.add(new JMenuItem(itemTwo));

        c.setComponentPopupMenu(popupMenu);
        return popupMenu;
      }
    });
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup() {
    JPopupMenu popupMenu = addPopupMenuToTextFieldWithCascade();
    robot().showPopupMenu(window().textField());
    JPopupMenu found = robot().findActivePopupMenu();
    assertThat(found).isSameAs(popupMenu);
  }

  @Test
  public void should_Return_Active_PopupMenu() {
    JPopupMenu popupMenu = addPopupMenuToTextField();
    robot().showPopupMenu(window().textField());
    JPopupMenu found = robot().findActivePopupMenu();
    assertThat(found).isSameAs(popupMenu);
  }

  @Test
  public void should_Return_Null_If_Active_PopupMenu_Not_Found() {
    JPopupMenu found = robot().findActivePopupMenu();
    assertThat(found).isNull();
  }
}
