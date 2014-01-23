/*
 * Created on Jan 21, 2008
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

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.valueOf;
import static javax.swing.text.DefaultEditorKit.deletePrevCharAction;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JTextComponentEditableQuery.isEditable;
import static org.fest.swing.driver.JTextComponentSelectAllTask.selectAllText;
import static org.fest.swing.driver.JTextComponentSelectTextTask.selectTextInRange;
import static org.fest.swing.driver.JTextComponentSetTextTask.setTextIn;
import static org.fest.swing.driver.PointAndParentForScrollingJTextFieldQuery.pointAndParentForScrolling;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isNullOrEmpty;
import static org.fest.util.Strings.quote;

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.util.Pair;
import org.fest.swing.util.Platform;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of {@code JTextComponent}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 */
@InternalApi
public class JTextComponentDriver extends JComponentDriver implements TextDisplayDriver<JTextComponent> {
  private static final String EDITABLE_PROPERTY = "editable";
  private static final String TEXT_PROPERTY = "text";

  /**
   * Creates a new {@link JTextComponentDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JTextComponentDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Deletes the text of the {@code JTextComponent}.
   *
   * @param textBox the target {@code JTextComponent}.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   */
  @RunsInEDT
  public void deleteText(@Nonnull JTextComponent textBox) {
    selectAll(textBox);
    invokeAction(textBox, deletePrevCharAction);
  }

  /**
   * Types the given text into the {@code JTextComponent}, replacing any existing text already there.
   *
   * @param textBox the target {@code JTextComponent}.
   * @param text the text to enter.
   * @throws NullPointerException if the text to enter is {@code null}.
   * @throws IllegalArgumentException if the text to enter is empty.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   */
  @RunsInEDT
  public void replaceText(@Nonnull JTextComponent textBox, @Nonnull String text) {
    checkNotNullOrEmpty(text);
    selectAll(textBox);
    enterText(textBox, text);
  }

  /**
   * Selects the text in the {@code JTextComponent}.
   *
   * @param textBox the target {@code JTextComponent}.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   */
  @RunsInEDT
  public void selectAll(@Nonnull JTextComponent textBox) {
    checkStateAndScrollToPosition(textBox, 0);
    selectAllText(textBox);
  }

  /**
   * Types the given text into the {@code JTextComponent}.
   *
   * @param textBox the target {@code JTextComponent}.
   * @param text the text to enter.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   */
  @RunsInEDT
  public void enterText(@Nonnull JTextComponent textBox, @Nonnull String text) {
    focusAndWaitForFocusGain(textBox);
    robot.enterText(text);
  }

  /**
   * <p>
   * Sets the given text into the {@code JTextComponent}. Unlike {@link #enterText(JTextComponent, String)}, this method
   * bypasses the event system and allows immediate updating on the underlying document model.
   * </p>
   * <p>
   * Primarily desired for speeding up tests when precise user event fidelity isn't necessary.
   * </p>
   *
   * @param textBox the target {@code JTextComponent}.
   * @param text the text to enter.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   */
  @RunsInEDT
  public void setText(@Nonnull JTextComponent textBox, @Nullable String text) {
    focusAndWaitForFocusGain(textBox);
    setTextIn(textBox, text);
    robot.waitForIdle();
  }

  /**
   * Select the given text range.
   *
   * @param textBox the target {@code JTextComponent}.
   * @param text the text to select.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   * @throws IllegalArgumentException if the {@code JTextComponent} does not contain the given text to select.
   * @throws ActionFailedException if selecting the text fails.
   */
  @RunsInEDT
  public void selectText(@Nonnull JTextComponent textBox, @Nonnull String text) {
    int indexFound = indexOfText(textBox, text);
    if (indexFound == -1) {
      throw new IllegalArgumentException(String.format("The text %s was not found", quote(text)));
    }
    selectText(textBox, indexFound, indexFound + text.length());
  }

  @RunsInEDT
  private static int indexOfText(final @Nonnull JTextComponent textBox, final @Nonnull String text) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected Integer executeInEDT() {
        checkEnabledAndShowing(textBox);
        String actualText = textBox.getText();
        if (isNullOrEmpty(actualText)) {
          return -1;
        }
        return actualText.indexOf(text);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Select the given text range.
   *
   * @param textBox the target {@code JTextComponent}.
   * @param start the starting index of the selection.
   * @param end the ending index of the selection.
   * @throws IllegalStateException if the {@code JTextComponent} is disabled.
   * @throws IllegalStateException if the {@code JTextComponent} is not showing on the screen.
   * @throws ActionFailedException if selecting the text in the given range fails.
   */
  @RunsInEDT
  public void selectText(@Nonnull JTextComponent textBox, int start, int end) {
    robot.moveMouse(textBox, checkStateAndScrollToPosition(textBox, start));
    robot.moveMouse(textBox, scrollToPosition(textBox, end));
    performAndValidateTextSelection(textBox, start, end);
  }

  @RunsInEDT
  private static @Nonnull Point checkStateAndScrollToPosition(final @Nonnull JTextComponent textBox, final int index) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        checkEnabledAndShowing(textBox);
        return scrollToVisible(textBox, index);
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  private static @Nonnull Point scrollToPosition(final @Nonnull JTextComponent textBox, final int index) {
    Point result = execute(new GuiQuery<Point>() {
      @Override
      protected Point executeInEDT() {
        return scrollToVisible(textBox, index);
      }
    });
    return checkNotNull(result);
  }

  /**
   * Move the pointer to the location of the given index. Takes care of auto-scrolling through text.
   *
   * @param textBox the target {@code JTextComponent}.
   * @param index the given location.
   * @return the position of the pointer after being moved.
   * @throws ActionFailedException if it was not possible to scroll to the location of the given index.
   */
  @RunsInCurrentThread
  private static @Nonnull Point scrollToVisible(@Nonnull JTextComponent textBox, int index) {
    Rectangle indexLocation = locationOf(textBox, index);
    if (isRectangleVisible(textBox, indexLocation)) {
      return centerOf(indexLocation);
    }
    scrollToVisible(textBox, indexLocation);
    indexLocation = locationOf(textBox, index);
    if (isRectangleVisible(textBox, indexLocation)) {
      return centerOf(indexLocation);
    }
    String format = "Unable to make visible the location of the index <%d> by scrolling to the point <%s> on %s";
    String msg = String.format(format, index, formatOriginOf(indexLocation), format(textBox));
    throw actionFailure(msg);
  }

  @RunsInCurrentThread
  private static @Nonnull Rectangle locationOf(@Nonnull JTextComponent textBox, int index) {
    Rectangle r = null;
    try {
      r = textBox.modelToView(index);
    } catch (BadLocationException e) {
      throw cannotGetLocation(textBox, index);
    }
    if (r != null) {
      if (Platform.isMacintosh() && r.y == -1) {
        r.y = 0;
      }
      return r;
    }
    throw cannotGetLocation(textBox, index);
  }

  private static ActionFailedException cannotGetLocation(@Nonnull JTextComponent textBox, int index) {
    String msg = String.format("Unable to get location for index <%d> in %s", index, format(textBox));
    throw actionFailure(msg);
  }

  @RunsInCurrentThread
  private static boolean isRectangleVisible(@Nonnull JTextComponent textBox, @Nonnull Rectangle r) {
    Rectangle visible = textBox.getVisibleRect();
    return visible.contains(r.x, r.y);
  }

  private static String formatOriginOf(Rectangle r) {
    return concat(valueOf(r.x), ",", valueOf(r.y));
  }

  @RunsInCurrentThread
  private static void scrollToVisible(@Nonnull JTextComponent textBox, @Nonnull Rectangle r) {
    textBox.scrollRectToVisible(r);
    if (isVisible(textBox, r)) {
      return;
    }
    scrollToVisibleIfIsTextField(textBox, r);
  }

  @RunsInCurrentThread
  private static void scrollToVisibleIfIsTextField(@Nonnull JTextComponent textBox, @Nonnull Rectangle r) {
    if (!(textBox instanceof JTextField)) {
      return;
    }
    Pair<Point, Container> pointAndParent = pointAndParentForScrolling((JTextField) textBox);
    Container parent = pointAndParent.second;
    if (parent == null || parent instanceof CellRendererPane || !(parent instanceof JComponent)) {
      return;
    }
    ((JComponent) parent).scrollRectToVisible(addPointToRectangle(checkNotNull(pointAndParent.first), r));
  }

  private static @Nonnull Rectangle addPointToRectangle(@Nonnull Point p, @Nonnull Rectangle r) {
    Rectangle destination = new Rectangle(r);
    destination.x += p.x;
    destination.y += p.y;
    return destination;
  }

  private static @Nonnull Point centerOf(@Nonnull Rectangle r) {
    return new Point(r.x + r.width / 2, r.y + r.height / 2);
  }

  @RunsInEDT
  private static void performAndValidateTextSelection(final @Nonnull JTextComponent textBox, final int start,
      final int end) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        selectTextInRange(textBox, start, end);
        verifyTextWasSelected(textBox, start, end);
      }
    });
  }

  @RunsInCurrentThread
  private static void verifyTextWasSelected(@Nonnull JTextComponent textBox, int start, int end) {
    int actualStart = textBox.getSelectionStart();
    int actualEnd = textBox.getSelectionEnd();
    if (actualStart == min(start, end) && actualEnd == max(start, end)) {
      return;
    }
    String format = "Unable to select text uses indices <%d> and <%d>, current selection starts at <%d> and ends at <%d>";
    String msg = String.format(format, start, end, actualStart, actualEnd);
    throw actionFailure(msg);
  }

  /**
   * Asserts that the text in the given {@code JTextComponent} is equal to the specified value.
   *
   * @param textBox the given {@code JTextComponent}.
   * @param expected the text to match. It can be a regular expression pattern.
   * @throws AssertionError if the text of the {@code JTextComponent} is not equal to the given one.
   */
  @RunsInEDT
  @Override
  public void requireText(@Nonnull JTextComponent textBox, @Nullable String expected) {
    verifyThat(textOf(textBox)).as(textProperty(textBox)).isEqualOrMatches(expected);
  }

  /**
   * Asserts that the text in the given {@code JTextComponent} matches the given regular expression pattern.
   *
   * @param textBox the given {@code JTextComponent}.
   * @param pattern the regular expression pattern to match.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the text of the {@code JTextComponent} is not equal to the given one.
   * @since 1.2
   */
  @Override
  @RunsInEDT
  public void requireText(@Nonnull JTextComponent textBox, @Nonnull Pattern pattern) {
    verifyThat(textOf(textBox)).as(textProperty(textBox)).matches(pattern);
  }

  /**
   * Asserts that the given {@code JTextComponent} is empty.
   *
   * @param textBox the given {@code JTextComponent}.
   * @throws AssertionError if the {@code JTextComponent} is not empty.
   */
  @RunsInEDT
  public void requireEmpty(@Nonnull JTextComponent textBox) {
    assertThat(textOf(textBox)).as(textProperty(textBox)).isEmpty();
  }

  @RunsInEDT
  private static @Nonnull Description textProperty(@Nonnull JTextComponent textBox) {
    return propertyName(textBox, TEXT_PROPERTY);
  }

  /**
   * Asserts that the given {@code JTextComponent} is editable.
   *
   * @param textBox the given {@code JTextComponent}.
   * @throws AssertionError if the {@code JTextComponent} is not editable.
   */
  @RunsInEDT
  public void requireEditable(@Nonnull JTextComponent textBox) {
    assertEditable(textBox, true);
  }

  /**
   * Asserts that the given {@code JTextComponent} is not editable.
   *
   * @param textBox the given {@code JTextComponent}.
   * @throws AssertionError if the {@code JTextComponent} is editable.
   */
  @RunsInEDT
  public void requireNotEditable(@Nonnull JTextComponent textBox) {
    assertEditable(textBox, false);
  }

  @RunsInEDT
  private void assertEditable(@Nonnull JTextComponent textBox, boolean editable) {
    assertThat(isEditable(textBox)).as(editableProperty(textBox)).isEqualTo(editable);
  }

  @RunsInEDT
  private static @Nonnull  Description editableProperty(@Nonnull JTextComponent textBox) {
    return propertyName(textBox, EDITABLE_PROPERTY);
  }

  /**
   * Returns the text of the given {@code JTextComponent}.
   *
   * @param textBox the given {@code JTextComponent}.
   * @return the text of the given {@code JTextComponent}.
   * @since 1.2
   */
  @RunsInEDT
  @Override
  public @Nullable String textOf(@Nonnull JTextComponent textBox) {
    return JTextComponentTextQuery.textOf(textBox);
  }
}
