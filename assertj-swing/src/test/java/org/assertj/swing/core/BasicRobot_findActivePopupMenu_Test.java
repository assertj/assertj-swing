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

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link BasicRobot#findActivePopupMenu()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author DaveBrad
 */
public class BasicRobot_findActivePopupMenu_Test extends BasicRobot_TestCase {

  @RunsInEDT
  final JPopupMenu[] addPopupMenuToTextFieldWithCascade(int cascadePosition) {
    return createAndSetPopupMenuWithCascade(cascadePosition, window().textField(), "Luke", "Leia");
  }

  /**
   * Create a single cascade arrangement as this is a closed circuit of
   * cascade.
   *
   * @param cascadePosition the menu-selection items position in the
   *          inner-popup that is the JMenu which is a cascade
   * @param c the root component for the popup to be applied on
   * @param itemOne menuItem1 (a selectable item)
   * @param itemTwo menuItem2 (a selectable item)\
   *
   * @return the built popup menu with the cascade inserted at the
   *         cascadePosition
   */
  @RunsInEDT
  public static JPopupMenu[] createAndSetPopupMenuWithCascade(
                                                              final int cascadePosition, final JComponent c,
                                                              final String itemOne,
                                                              final String itemTwo) {
    return execute(() -> {
      // build the outer cascade popup
      JMenu outerMenu = new JMenu("cascade test");

      outerMenu.add(new JMenuItem("cascade line 1"));
      outerMenu.add(new JMenuItem("cascade line 2"));

      // build the root popup, with dynamic position
      JPopupMenu popupMenu = new JPopupMenu();

      // place the outer menu at different locations in popup menu order so
      // as to test for unknown boundaries(also because showPopupMenu positions
      // to the middle of the popup and causes an automatic sub-menu launch)
      switch (cascadePosition) {
      case 1:
        popupMenu.add(outerMenu);
        popupMenu.add(new JMenuItem(itemOne));
        popupMenu.add(new JMenuItem(itemTwo));
        break;

      default:
      case 2:
        popupMenu.add(new JMenuItem(itemOne));
        popupMenu.add(outerMenu);
        popupMenu.add(new JMenuItem(itemTwo));
        break;

      case 3:
        popupMenu.add(new JMenuItem(itemOne));
        popupMenu.add(new JMenuItem(itemTwo));
        popupMenu.add(outerMenu);
        break;
      }
      // record the positions of the popup in order of cascade-position
      ArrayList<JPopupMenu> popupArr = new ArrayList<>();

      popupArr.add(popupMenu); // root popup, is inner
      popupArr.add(outerMenu.getPopupMenu()); // cascade 1 popup, is inner

      c.setComponentPopupMenu(popupMenu);
      return popupArr.toArray(new JPopupMenu[popupArr.size()]);
    });
  }

  @RunsInEDT
  final JPopupMenu[] addPopupMenuToTextFieldWithCascadeWithCascade(int cascadeCascadePosition) {
    return createAndSetPopupMenuWithCascadeWithCascade(cascadeCascadePosition, window().textField(),
                                                       "Luke2", "Leia2");
  }

  /**
   * Create a multiple cascade arrangement as a more complex arrangement.
   *
   * @param cascadePosition the menu-selection items position in the
   *          inner-popup that is the JMenu which is a cascade
   * @param c the root component for the popup to be applied on
   * @param itemOne menuItem1 (a selectable item)
   * @param itemTwo menuItem2 (a selectable item)\
   *
   * @return the built popup menu with the cascade inserted at the
   *         cascadeCascadePosition
   */
  @RunsInEDT
  public static JPopupMenu[] createAndSetPopupMenuWithCascadeWithCascade(
                                                                         final int cascadeCascadePosition,
                                                                         final JComponent c, final String itemOne,
                                                                         final String itemTwo) {
    return execute(() -> {

      JMenu outerMenu = new JMenu("cascade2 test");
      outerMenu.add(new JMenuItem("cascade2 1"));
      outerMenu.add(new JMenuItem("cascade2 2"));

      // associate the outer with the inner1 for testing purposes at
      // the provided menu position
      // although this may be done with set menu item to index, the comments
      // in Java source code suggest this is prone to error (OpenJDK 8 u77)
      JMenu inner1Menu = new JMenu("cascade1 test");

      // place the outer menu at different locations in popup menu order so
      // as to test for unknown boundaries(also because showPopupMenu positions
      // to the middle of the popup and causes an automatic sub-menu launch)
      switch (cascadeCascadePosition) {
      case 1:
        inner1Menu.add(outerMenu);
        inner1Menu.add(new JMenuItem("cascade1 1"));
        inner1Menu.add(new JMenuItem("cascade1 2"));
        break;

      default:
      case 2:
        inner1Menu.add(new JMenuItem("cascade1 1"));
        inner1Menu.add(outerMenu);
        inner1Menu.add(new JMenuItem("cascade1 2"));

        break;

      case 3:
        inner1Menu.add(new JMenuItem("cascade1 1"));
        inner1Menu.add(new JMenuItem("cascade1 2"));
        inner1Menu.add(outerMenu);
        break;
      }
      // build the root popup, adding the inner stuff
      JPopupMenu popupMenu = new JPopupMenu();

      popupMenu.add(new JMenuItem(itemOne));
      popupMenu.add(inner1Menu);
      popupMenu.add(new JMenuItem(itemTwo));

      // record the positions of the popup in order of cascade-position
      ArrayList<JPopupMenu> popupArr = new ArrayList<>();

      // root popup, is inner
      popupArr.add(popupMenu);
      // cascade 1 popup, is inner
      popupArr.add(inner1Menu.getPopupMenu());
      // cascade 2 popup, is outer
      popupArr.add(outerMenu.getPopupMenu());

      c.setComponentPopupMenu(popupMenu);
      return popupArr.toArray(new JPopupMenu[popupArr.size()]);
    });
  }

  @RunsInEDT
  public static JPopupMenu createAndSetPopupMenuWithCascade(final JComponent c, final String itemOne,
                                                            final String itemTwo) {
    return execute(() -> {
      JPopupMenu popupMenu = new JPopupMenu();

      JPopupMenu outerMenu = new JPopupMenu("cascade test");
      outerMenu.add(new JMenuItem("cascade line 1"));
      outerMenu.add(new JMenuItem("cascade line 2"));

      popupMenu.add(new JMenuItem(itemOne));
      popupMenu.add(outerMenu);
      popupMenu.add(new JMenuItem(itemTwo));

      c.setComponentPopupMenu(popupMenu);
      return outerMenu;
    });
  }

  /** Perform the single cascade test for the popup in the N position. */
  private void should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_N(int selectMenu)
      throws InterruptedException {
    int selectMenuIdx = selectMenu - 1;

    // build the popup to be tested against
    JPopupMenu[] popupMenuArray = addPopupMenuToTextFieldWithCascade(selectMenu);

    // show-popup-menu will launch the popup and select the menu item that
    // is at the middle of the list
    // HOWEVER, the middle selection is done after a pause
    robot().showPopupMenu(window().textField());
    JPopupMenu found = robot().findActivePopupMenu();

    assertThat(found).isSameAs(popupMenuArray[0]);

    // crude but effect way to get the cascade
    Component o = found.getComponent(selectMenuIdx);
    robot().focus(o);

    pauseYieldForFocusChange();

    // after the HOWEVER will expect the cascade to be presented
    found = robot().findActivePopupMenu();
    assertThat(found).isSameAs(popupMenuArray[1]);
  }

  /** Perform the cascade cascade test for the popup in the N position. */
  private void should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_N(int selectMenu)
      throws InterruptedException {
    int selectMenuIdx = selectMenu - 1;

    // build the popup to be tested against
    JPopupMenu[] popupMenuArray = addPopupMenuToTextFieldWithCascadeWithCascade(selectMenu);

    // show-popup-menu will launch the popup and select the menu item that
    // is at the middle of the list
    // HOWEVER, the middle selection is done after a pause
    robot().showPopupMenu(window().textField());

    JPopupMenu found = robot().findActivePopupMenu();
    assertThat(found).isSameAs(popupMenuArray[0]);

    // crude but effect way to get the cascade
    Component o = found.getComponent(1);
    robot().focus(o);

    // need a pause to allow the focus request to happen and new popup to be shown
    pauseYieldForFocusChange();

    // after the HOWEVER will expect the cascade to be presented
    found = robot().findActivePopupMenu();

    // crude but effect way to get the cascade
    o = found.getComponent(selectMenuIdx);
    robot().focus(o);

    pauseYieldForFocusChange();

    found = robot().findActivePopupMenu();

    assertThat(found).isSameAs(popupMenuArray[2]);
  }

  /** Crude pause to allow the robot focus change event to propagate to a component. */
  private void pauseYieldForFocusChange() throws InterruptedException {
    // need a pause to allow the focus request to happen and new popup to be shown
    // (focus and wait does not seem to happen, suspect the thread is not
    // yielding to allow the focus change to happen)
    // 300-500 works consistently, < 300 is if for test pass condition { 500
    // is better choice for slower machines that may be farmed into testing versus development)
    Thread.sleep(500);
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_3() throws InterruptedException {
    should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_N(3);
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_2() throws InterruptedException {
    should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_N(2);
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_1() throws InterruptedException {
    should_Return_Outer_PopupMenu_When_Having_A_CascadingPopup_Select_N(1);
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_3() throws InterruptedException {
    should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_N(3);
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_2() throws InterruptedException {
    should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_N(2);
  }

  @Test
  public void should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_1() throws InterruptedException {
    should_Return_Outer_PopupMenu_When_Having_A_CascadingCascadePopup_Select_N(1);
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