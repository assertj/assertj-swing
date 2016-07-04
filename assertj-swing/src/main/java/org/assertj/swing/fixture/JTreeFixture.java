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
package org.assertj.swing.fixture;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import org.assertj.swing.cell.JTreeCellReader;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.core.MouseClickInfo;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.JTreeDriver;

/**
 * <p>
 * Supports functional testing of {@code JTree}s.
 * </p>
 *
 * <p>
 * {@code TreePath}s can be specified using {@code String}s. For example, for the following tree:
 * </p>
 *
 * <pre>
 * root
 *   |
 *   -- node1
 *      |
 *      -- node1.1
 * </pre>
 *
 * <p>
 * we can identify the node "node1.1" as follows:
 * </p>
 *
 * <pre>
 *   root/node1/node1.1
 * </pre>
 *
 * <p>
 * The default path separator is "/". It can be changed by calling {@link #replaceSeparator(String)}.
 * </p>
 *
 * <p>
 * The conversion between the values given in tests and the values being displayed by a {@code JTree} renderer is
 * performed by a {@link JTreeCellReader}. This fixture uses a {@link org.assertj.swing.driver.BasicJTreeCellReader} by
 * default.
 * </p>
 *
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Fabien Barbero
 * @author Christian Rösch
 */
public class JTreeFixture extends AbstractJPopupMenuInvokerFixture<JTreeFixture, JTree, JTreeDriver> implements
    EditableComponentFixture<JTreeFixture> {
  /**
   * Creates a new {@link JTreeFixture}.
   *
   * @param robot performs simulation of user events on the given {@code JTree}.
   * @param target the {@code JTree} to be managed by this fixture.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public JTreeFixture(@Nonnull Robot robot, @Nonnull JTree target) {
    super(JTreeFixture.class, robot, target);
  }

  /**
   * Creates a new {@link JTreeFixture}.
   *
   * @param robot performs simulation of user events on a {@code JTree}.
   * @param treeName the name of the {@code JTree} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws org.assertj.swing.exception.ComponentLookupException if a matching {@code JTree} could not be found.
   * @throws org.assertj.swing.exception.ComponentLookupException if more than one matching {@code JTree} is found.
   */
  public JTreeFixture(@Nonnull Robot robot, @Nullable String treeName) {
    super(JTreeFixture.class, robot, treeName, JTree.class);
  }

  @Override
  protected @Nonnull JTreeDriver createDriver(@Nonnull Robot robot) {
    return new JTreeDriver(robot);
  }

  /**
   * Clicks the given row.
   *
   * @param row the given row.
   * @return this fixture.
   * @throws IllegalStateException if the {@code JTree} is disabled.
   * @throws IllegalStateException if the {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture clickRow(int row) {
    driver().clickRow(target(), row);
    return this;
  }

  /**
   * Clicks the given row.
   *
   * @param row the given row.
   * @param button the mouse button to use.
   * @return this fixture.
   * @throws NullPointerException if the button is {@code null}.
   * @throws IllegalStateException if the {@code JTree} is disabled.
   * @throws IllegalStateException if the {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture clickRow(int row, @Nonnull MouseButton button) {
    driver().clickRow(target(), row, button);
    return this;
  }

  /**
   * Clicks the given row.
   *
   * @param row the given row.
   * @param mouseClickInfo specifies the mouse button to use and how many times to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if the {@code JTree} is disabled.
   * @throws IllegalStateException if the {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture clickRow(int row, @Nonnull MouseClickInfo mouseClickInfo) {
    driver().clickRow(target(), row, mouseClickInfo);
    return this;
  }

  /**
   * Clicks the given path, expanding parent nodes if necessary.
   *
   * @param path the given path.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture clickPath(@Nonnull String path) {
    driver().clickPath(target(), path);
    return this;
  }

  /**
   * Clicks the given path, expanding parent nodes if necessary.
   *
   * @param path the given path.
   * @param button the mouse button to use.
   * @return this fixture.
   * @throws NullPointerException if the button is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture clickPath(@Nonnull String path, @Nonnull MouseButton button) {
    driver().clickPath(target(), path, button);
    return this;
  }

  /**
   * Clicks the given path, expanding parent nodes if necessary.
   *
   * @param path the given path.
   * @param mouseClickInfo specifies the mouse button to use and how many times to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture clickPath(@Nonnull String path, @Nonnull MouseClickInfo mouseClickInfo) {
    driver().clickPath(target(), path, mouseClickInfo);
    return this;
  }

  /**
   * Double-clicks the given row.
   *
   * @param row the given row.
   * @return this fixture.
   * @throws IllegalStateException if the {@code JTree} is disabled.
   * @throws IllegalStateException if the {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture doubleClickRow(int row) {
    driver().doubleClickRow(target(), row);
    return this;
  }

  /**
   * Double-clicks the given path.
   *
   * @param path the given path.
   * @return this fixture.
   * @throws IllegalStateException if the {@code JTree} is disabled.
   * @throws IllegalStateException if the {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture doubleClickPath(@Nonnull String path) {
    driver().doubleClickPath(target(), path);
    return this;
  }

  /**
   * Right-clicks the given row.
   *
   * @param row the given row.
   * @return this fixture.
   * @throws IllegalStateException if the {@code JTree} is disabled.
   * @throws IllegalStateException if the {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture rightClickRow(int row) {
    driver().rightClickRow(target(), row);
    return this;
  }

  /**
   * Right-clicks the given path, expanding parent nodes if necessary.
   *
   * @param path the given path.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture rightClickPath(@Nonnull String path) {
    driver().rightClickPath(target(), path);
    return this;
  }

  /**
   * Simulates a user dragging a row from this fixture's {@code JTree}.
   *
   * @param row the index of the row to drag.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture drag(int row) {
    driver().drag(target(), row);
    return this;
  }

  /**
   * Simulates a user dropping an item into this fixture's {@code JTree}.
   *
   * @param row the row to drop the item to.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if there is no drag action in effect.
   */
  public @Nonnull JTreeFixture drop(int row) {
    driver().drop(target(), row);
    return this;
  }

  /**
   * Simulates a user dropping an item into this fixture's {@code JTree}.
   *
   * @param row the row to drop the item below the item to drop.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if there is no drag action in effect.
   * @see #drop(int)
   */
  public @Nonnull JTreeFixture dropAbove(int row) {
    return drop(row - 1);
  }

  /**
   * Simulates a user dropping an item into this fixture's {@code JTree}.
   *
   * @param row the row to drop the item above the item to drop.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if there is no drag action in effect.
   * @see #drop(int)
   */
  public @Nonnull JTreeFixture dropBelow(int row) {
    return drop(row + 1);
  }

  /**
   * Simulates a user dragging an item from this fixture's {@code JTree}.
   *
   * @param path the path corresponding to the item to drag.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture drag(@Nonnull String path) {
    driver().drag(target(), path);
    return this;
  }

  /**
   * Simulates a user dropping an item into this fixture's {@code JTree}.
   *
   * @param path the path corresponding to the item relative to the drop point.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if there is no drag action in effect.
   */
  public @Nonnull JTreeFixture drop(@Nonnull String path) {
    driver().drop(target(), path);
    return this;
  }

  /**
   * Simulates a user selecting the tree node at the given row.
   *
   * @param row the index of the row to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture selectRow(int row) {
    driver().selectRow(target(), row);
    return this;
  }

  /**
   * Simulates a user selecting the tree nodes at the given rows.
   *
   * @param rows the indices of the rows to select.
   * @return this fixture.
   * @throws NullPointerException if the array of rows is {@code null}.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for any of the given rows cannot be
   *           found.
   */
  public @Nonnull JTreeFixture selectRows(@Nonnull int... rows) {
    driver().selectRows(target(), rows);
    return this;
  }

  /**
   * Selects the given path, expanding parent nodes if necessary. Unlike {@link #clickPath(String)}, this method will
   * not click the path if it is already selected.
   *
   * @param path the path to select.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture selectPath(@Nonnull String path) {
    driver().selectPath(target(), path);
    return this;
  }

  /**
   * Select the given paths, expanding parent nodes if necessary.
   *
   * @param paths the paths to select.
   * @return this fixture.
   * @throws NullPointerException if the array of rows is {@code null}.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the any of the given paths cannot be found.
   */
  public @Nonnull JTreeFixture selectPaths(@Nonnull String... paths) {
    driver().selectPaths(target(), paths);
    return this;
  }

  /**
   * Simulates a user unselecting the tree node at the given row.
   *
   * @param row the index of the row to unselect.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JTreeFixture unselectRow(int row) {
    driver().unselectRow(target(), row);
    return this;
  }

  /**
   * Simulates a user unselecting the tree nodes at the given rows.
   *
   * @param rows the indices of the rows to unselect.
   * @return this fixture.
   * @throws NullPointerException if the array of rows is {@code null}.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for any of the given rows cannot be
   *           found.
   */
  public @Nonnull JTreeFixture unselectRows(@Nonnull int... rows) {
    driver().unselectRows(target(), rows);
    return this;
  }

  /**
   * Unselects the given path, expanding parent nodes if necessary. Unlike {@link #clickPath(String)}, this method will
   * not click the path if it is not selected.
   *
   * @param path the path to unselect.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreeFixture unselectPath(@Nonnull String path) {
    driver().unselectPath(target(), path);
    return this;
  }

  /**
   * Unselect the given paths, expanding parent nodes if necessary.
   *
   * @param paths the paths to unselect.
   * @return this fixture.
   * @throws NullPointerException if the array of rows is {@code null}.
   * @throws IllegalArgumentException if the array of rows is empty.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the any of the given paths cannot be found.
   */
  public @Nonnull JTreeFixture unselectPaths(@Nonnull String... paths) {
    driver().unselectPaths(target(), paths);
    return this;
  }

  /**
   * Simulates a user toggling the open/closed state of the tree node at the given row.
   *
   * @param row the index of the row to toggle.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if this method fails to toggle the row.
   */
  public @Nonnull JTreeFixture toggleRow(int row) {
    driver().toggleRow(target(), row);
    return this;
  }

  /**
   * Simulates a user expanding the tree node at the given row.
   *
   * @param row the index of the row to expand.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if this method fails to expand the row.
   */
  public @Nonnull JTreeFixture expandRow(int row) {
    driver().expandRow(target(), row);
    return this;
  }

  /**
   * Simulates a user collapsing the tree node at the given row.
   *
   * @param row the index of the row to collapse.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if this method fails to collapse the row.
   */
  public @Nonnull JTreeFixture collapseRow(int row) {
    driver().collapseRow(target(), row);
    return this;
  }

  /**
   * Simulates a user expanding the tree node at the given path.
   *
   * @param path the path of the row to expand.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if this method fails to expand the path.
   */
  public @Nonnull JTreeFixture expandPath(@Nonnull String path) {
    driver().expandPath(target(), path);
    return this;
  }

  /**
   * Simulates a user collapsing the tree node at the given path.
   *
   * @param path the path of the row to collapse.
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   * @throws org.assertj.swing.exception.ActionFailedException if this method fails to collapse the path.
   */
  public @Nonnull JTreeFixture collapsePath(@Nonnull String path) {
    driver().collapsePath(target(), path);
    return this;
  }

  /**
   * Shows a pop-up menu at the position of the node in the given row.
   *
   * @param row the index of the row invoking the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(int row) {
    JPopupMenu popupMenu = driver().showPopupMenu(target(), row);
    return new JPopupMenuFixture(robot(), popupMenu);
  }

  /**
   * Shows a pop-up menu at the position of the last node in the given path. The last node in the given path will be
   * made visible (by expanding the parent node(s)) if it is not visible.
   *
   * @param path the path of the node invoking the pop-up menu.
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JTree} is disabled.
   * @throws IllegalStateException if this fixture's {@code JTree} is not showing on the screen.
   * @throws org.assertj.swing.exception.ComponentLookupException if a pop-up menu cannot be found.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JPopupMenuFixture showPopupMenuAt(@Nonnull String path) {
    JPopupMenu popupMenu = driver().showPopupMenu(target(), path);
    return new JPopupMenuFixture(robot(), popupMenu);
  }

  /**
   * Asserts that this fixture's {@code JTree} is editable.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JTree} is not editable.
   */
  @Override
  public @Nonnull JTreeFixture requireEditable() {
    driver().requireEditable(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JTree} is not editable.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JTree} is editable.
   */
  @Override
  public @Nonnull JTreeFixture requireNotEditable() {
    driver().requireNotEditable(target());
    return this;
  }

  /**
   * Asserts that this fixture's {@code JTree}'s selected rows are equal to the given one.
   *
   * @param rows the indices of the rows, expected to be selected.
   * @throws NullPointerException if the array of row indices is {@code null}.
   * @throws AssertionError if this fixture's {@code JTree} selection is not equal to the given rows.
   * @return this fixture.
   */
  public @Nonnull JTreeFixture requireSelection(@Nonnull int... rows) {
    driver().requireSelection(target(), rows);
    return this;
  }

  /**
   * Asserts that this fixture's {@code JTree} selection is equal to the given paths.
   *
   * @param paths the given paths, expected to be selected.
   * @return this fixture.
   * @throws NullPointerException if the array of paths is {@code null}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if any of the given path cannot be found.
   * @throws AssertionError if this fixture's {@code JTree} selection is not equal to the given paths.
   */
  public @Nonnull JTreeFixture requireSelection(@Nonnull String... paths) {
    driver().requireSelection(target(), paths);
    return this;
  }

  /**
   * Asserts that this fixture's {@code JTree}'s does not have any selection.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code JTree} has a selection.
   */
  public @Nonnull JTreeFixture requireNoSelection() {
    driver().requireNoSelection(target());
    return this;
  }

  /**
   * @return the separator to use when converting {@code TreePath}s to {@code String}s.
   */
  public String separator() {
    return driver().separator();
  }

  /**
   * Updates the separator to use when converting {@code TreePath}s to {@code String}s. The default value is "/".
   *
   * @param separator the new separator.
   * @return this fixture.
   * @throws NullPointerException if the given separator is {@code null}.
   */
  public @Nonnull JTreeFixture replaceSeparator(@Nonnull String separator) {
    driver().replaceSeparator(separator);
    return this;
  }

  /**
   * Updates the implementation of {@link JTreeCellReader} to use when comparing internal values of a {@code JTree} and
   * the values expected in a test. The default implementation to use is
   * {@link org.assertj.swing.driver.BasicJTreeCellReader}.
   *
   * @param cellReader the new {@code JTreeCellValueReader} to use.
   * @throws NullPointerException if {@code cellReader} is {@code null}.
   * @return this fixture.
   */
  public @Nonnull JTreeFixture replaceCellReader(@Nonnull JTreeCellReader cellReader) {
    driver().replaceCellReader(cellReader);
    return this;
  }

  /**
   * Returns a fixture that manages the node specified by the given row.
   *
   * @param row the given row.
   * @return a fixture that manages the node specified by the given row.
   * @throws IndexOutOfBoundsException if the given index is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   */
  public @Nonnull JTreeRowFixture node(int row) {
    driver().checkRowInBounds(target(), row);
    return new JTreeRowFixture(this, row);
  }

  /**
   * Returns a fixture that manages the node specified by the given path.
   *
   * @param path the given path.
   * @return a fixture that manages the node specified by the given path.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nonnull JTreePathFixture node(@Nonnull String path) {
    driver().checkPathExists(target(), path);
    return new JTreePathFixture(this, path);
  }

  /**
   * Returns the {@code String} representation of the given row.
   *
   * @param row the given row.
   * @return the {@code String} representation of the node at the given path.
   * @throws IndexOutOfBoundsException if the given row is less than zero or equal than or greater than the number of
   *           visible rows in the {@code JTree}.
   * @throws org.assertj.swing.exception.LocationUnavailableException if a tree path for the given row cannot be found.
   */
  public @Nullable String valueAt(int row) {
    return driver().nodeValue(target(), row);
  }

  /**
   * Returns the {@code String} representation of the node at the given path.
   *
   * @param path the given path.
   * @return the {@code String} representation of the node at the given path.
   * @throws org.assertj.swing.exception.LocationUnavailableException if the given path cannot be found.
   */
  public @Nullable String valueAt(@Nonnull String path) {
    return driver().nodeValue(target(), path);
  }
}