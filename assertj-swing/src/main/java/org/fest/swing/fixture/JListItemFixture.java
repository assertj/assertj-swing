/*
 * Created on Dec 8, 2007
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
package org.fest.swing.fixture;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.cell.JListCellReader;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.util.VisibleForTesting;

/**
 * Supports functional testing of single items in {@code JList}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JListItemFixture implements ItemFixture<JListItemFixture> {
  private final JListFixture list;
  private final int index;

  /**
   * Creates a new {@link JListItemFixture}.
   *
   * @param list manages the {@code JList} containing the list item to be managed by this fixture.
   * @param index index of the list item to be managed by this fixture.
   * @throws NullPointerException if {@code list} is {@code null}.
   */
  public JListItemFixture(@Nonnull JListFixture list, int index) {
    this.list = checkNotNull(list);
    this.index = index;
  }

  /**
   * Simulates a user selecting this fixture's list item.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture select() {
    list.selectItem(index);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's list item.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture click() {
    list.clickItem(index);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's list item.
   *
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseButton} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture click(@Nonnull MouseButton button) {
    list.clickItem(index, button, 1);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's list item.
   *
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture click(@Nonnull MouseClickInfo mouseClickInfo) {
    list.clickItem(index, mouseClickInfo.button(), mouseClickInfo.times());
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's list item.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture doubleClick() {
    list.clickItem(index, LEFT_BUTTON, 2);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's list item.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture rightClick() {
    list.clickItem(index, RIGHT_BUTTON, 1);
    return this;
  }

  /**
   * Shows a pop-up menu using this fixture's list item as the invoker of the pop-up menu.
   *
   * @return a fixture that manages the displayed pop-up menu.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   * @throws ComponentLookupException if a pop-up menu cannot be found.
   */
  @Override
  public final @Nonnull JPopupMenuFixture showPopupMenu() {
    return list.showPopupMenuAt(index);
  }

  /**
   * Returns the {@code String} representation of the value of this fixture's list item, using the
   * {@link JListCellReader} from the {@link JListFixture} that created this {@link JListItemFixture}.
   *
   * @return the {@code String} representation of the value of this fixture's list item.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   * @see JListFixture#replaceCellReader(JListCellReader)
   */
  @Override
  public final @Nullable String value() {
    return list.valueAt(index);
  }

  /**
   * Simulates a user dragging this fixture's list item.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   */
  @Override
  public final @Nonnull JListItemFixture drag() {
    list.drag(index);
    return this;
  }

  /**
   * Simulates a user dropping into this fixture's list item.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code JList} is disabled.
   * @throws IllegalStateException if this fixture's {@code JList} is not showing on the screen.
   * @throws IndexOutOfBoundsException if this item's index is negative or greater than the index of the last item in
   *           the {@code JList}.
   * @throws ActionFailedException if there is no drag action in effect.
   */
  @Override
  public final @Nonnull JListItemFixture drop() {
    list.drop(index);
    return this;
  }

  /**
   * @return the index of this fixture's list item.
   */
  public final int index() {
    return index;
  }

  @VisibleForTesting
  @Nonnull JListFixture listFixture() {
    return list;
  }
}
