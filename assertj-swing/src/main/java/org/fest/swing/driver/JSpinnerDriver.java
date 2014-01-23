/*
 * Created on Jan 26, 2008
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

import static javax.swing.text.DefaultEditorKit.selectAllAction;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.JSpinnerSetValueTask.setValue;
import static org.fest.swing.driver.JSpinnerValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.Component;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.Robot;
import org.fest.swing.core.TypeMatcher;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.UnexpectedException;

/**
 * <p>
 * Supports functional testing of {@code JSpinner}s.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is intended for internal use only. Please use the classes in the package
 * {@link org.fest.swing.fixture} in your tests.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JSpinnerDriver extends JComponentDriver {
  private static final TypeMatcher EDITOR_MATCHER = new TypeMatcher(JTextComponent.class, true);
  private static final String VALUE_PROPERTY = "value";

  /**
   * Creates a new {@link JSpinnerDriver}.
   *
   * @param robot the robot to use to simulate user input.
   */
  public JSpinnerDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Increments the value of the {@code JSpinner} the given number of times.
   *
   * @param spinner the target {@code JSpinner}.
   * @param times how many times the value of this fixture's {@code JSpinner} should be incremented.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   */
  @RunsInEDT
  public void increment(@Nonnull JSpinner spinner, int times) {
    checkIsPositive(times, "increment the value");
    validateAndIncrementValue(spinner, times);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void validateAndIncrementValue(final @Nonnull JSpinner spinner, final int times) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(spinner);
        incrementValue(spinner, times);
      }
    });
  }

  @RunsInCurrentThread
  private static void incrementValue(@Nonnull JSpinner spinner, int times) {
    for (int i = 0; i < times; i++) {
      Object newValue = spinner.getNextValue();
      if (newValue == null) {
        return;
      }
      spinner.setValue(newValue);
    }
  }

  /**
   * Increments the value of the {@code JSpinner}.
   *
   * @param spinner the target {@code JSpinner}.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   */
  @RunsInEDT
  public void increment(@Nonnull JSpinner spinner) {
    validateAndIncrementValue(spinner);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void validateAndIncrementValue(final @Nonnull JSpinner spinner) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(spinner);
        Object newValue = spinner.getNextValue();
        if (newValue != null) {
          spinner.setValue(newValue);
        }
      }
    });
  }

  /**
   * Decrements the value of the {@code JSpinner} the given number of times.
   *
   * @param spinner the target {@code JSpinner}.
   * @param times how many times the value of this fixture's {@code JSpinner} should be decremented.
   * @throws IllegalArgumentException if {@code times} is less than or equal to zero.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   */
  @RunsInEDT
  public void decrement(@Nonnull JSpinner spinner, int times) {
    checkIsPositive(times, "decrement the value");
    validateAndDecrementValue(spinner, times);
    robot.waitForIdle();
  }

  private void checkIsPositive(int times, @Nonnull String action) {
    if (times > 0) {
      return;
    }
    String msg = String.format("The number of times to %s should be greater than zero, but was <%d>", action, times);
    throw new IllegalArgumentException(msg);
  }

  @RunsInEDT
  private static void validateAndDecrementValue(final @Nonnull JSpinner spinner, final int times) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(spinner);
        decrementValue(spinner, times);
      }
    });
  }

  @RunsInCurrentThread
  private static void decrementValue(@Nonnull JSpinner spinner, int times) {
    for (int i = 0; i < times; i++) {
      Object newValue = spinner.getPreviousValue();
      if (newValue == null) {
        return;
      }
      spinner.setValue(newValue);
    }
  }

  /**
   * Decrements the value of the {@code JSpinner}.
   *
   * @param spinner the target {@code JSpinner}.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   */
  @RunsInEDT
  public void decrement(@Nonnull JSpinner spinner) {
    validateAndDecrementValue(spinner);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void validateAndDecrementValue(final @Nonnull JSpinner spinner) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        checkEnabledAndShowing(spinner);
        Object newValue = spinner.getPreviousValue();
        if (newValue != null) {
          spinner.setValue(newValue);
        }
      }
    });
  }

  /**
   * Returns the text displayed in the given {@code JSpinner}. This method first tries to get the text displayed in the
   * {@code JSpinner}'s editor, assuming it is a {@code JTextComponent}. If the text from the editor cannot be
   * retrieved, it will return the {@code String} representation of the value in the {@code JSpinner}'s model.
   *
   * @param spinner the target {@code JSpinner}.
   * @return the text displayed in the given {@code JSpinner}.
   * @since 1.2
   */
  @RunsInEDT
  public @Nullable String textOf(@Nonnull JSpinner spinner) {
    JTextComponent editor = findEditor(spinner);
    if (editor != null) {
      return JTextComponentTextQuery.textOf(editor);
    }
    Object value = valueOf(spinner);
    return value != null ? value.toString() : null;
  }

  /**
   * Enters and commits the given text in the {@code JSpinner}, assuming its editor has a {@code JTextComponent} under
   * it.
   *
   * @param spinner the target {@code JSpinner}.
   * @param text the text to enter.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   * @throws ActionFailedException if the editor of the {@code JSpinner} is not a {@code JTextComponent} or cannot be
   *           found.
   * @throws UnexpectedException if entering the text in the {@code JSpinner}'s editor fails.
   */
  @RunsInEDT
  public void enterTextAndCommit(@Nonnull JSpinner spinner, String text) {
    enterText(spinner, text);
    commit(spinner);
    robot.waitForIdle();
  }

  @RunsInEDT
  private static void commit(final @Nonnull JSpinner spinner) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws ParseException {
        spinner.commitEdit();
      }
    });
  }

  /**
   * Enters the given text in the {@code JSpinner}, assuming its editor has a {@code JTextComponent} under it. This
   * method does not commit the value to the {@code JSpinner}.
   *
   * @param spinner the target {@code JSpinner}.
   * @param text the text to enter.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   * @throws ActionFailedException if the editor of the {@code JSpinner} is not a {@code JTextComponent} or cannot be
   *           found.
   * @throws UnexpectedException if entering the text in the {@code JSpinner}'s editor fails.
   * @see #enterTextAndCommit(JSpinner, String)
   */
  @RunsInEDT
  public void enterText(@Nonnull JSpinner spinner, @Nonnull String text) {
    checkInEdtEnabledAndShowing(spinner);
    JTextComponent editor = findEditor(spinner);
    robot.waitForIdle();
    checkEditorNotNull(spinner, editor);
    if (editor == null) {
      return;
    }
    robot.focusAndWaitForFocusGain(editor);
    invokeAction(editor, selectAllAction);
    robot.enterText(text);
  }

  @RunsInEDT
  private @Nullable JTextComponent findEditor(@Nonnull JSpinner spinner) {
    ComponentFinder finder = robot.finder();
    List<Component> found = newArrayList(finder.findAll(spinner, EDITOR_MATCHER));
    if (found.size() != 1) {
      return null;
    }
    Component c = found.get(0);
    if (c instanceof JTextComponent) {
      return (JTextComponent) c;
    }
    return null;
  }

  @RunsInEDT
  private static void checkEditorNotNull(final @Nonnull JSpinner spinner, final @Nullable JTextComponent editor) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        if (editor == null) {
          throw actionFailure(concat("Unable to find editor for ", format(spinner)));
        }
      }
    });
  }

  /**
   * Selects the given value in the given {@code JSpinner}.
   *
   * @param spinner the target {@code JSpinner}.
   * @param value the value to select.
   * @throws IllegalStateException if the {@code JSpinner} is disabled.
   * @throws IllegalStateException if the {@code JSpinner} is not showing on the screen.
   * @throws IllegalArgumentException if the given {@code JSpinner} does not support the given value.
   */
  @RunsInEDT
  public void selectValue(@Nonnull JSpinner spinner, @Nonnull Object value) {
    try {
      setValue(spinner, value);
    } catch (IllegalArgumentException e) {
      // message from original exception is useless
      throw new IllegalArgumentException(concat("Value ", quote(value), " is not valid"));
    }
    robot.waitForIdle();
  }

  /**
   * Returns the {@code JTextComponent} used as editor in the given {@code JSpinner}.
   *
   * @param spinner the target {@code JSpinner}.
   * @return the {@code JTextComponent} used as editor in the given {@code JSpinner}.
   * @throws ComponentLookupException if the given {@code JSpinner} does not have a {@code JTextComponent} as editor.
   */
  @RunsInEDT
  public JTextComponent editor(@Nonnull JSpinner spinner) {
    return (JTextComponent) robot.finder().find(spinner, EDITOR_MATCHER);
  }

  /**
   * Verifies that the value of the {@code JSpinner} is equal to the given one.
   *
   * @param spinner the target {@code JSpinner}.
   * @param value the expected value.
   * @throws AssertionError if the value of the {@code JSpinner} is not equal to the given one.
   */
  @RunsInEDT
  public void requireValue(@Nonnull JSpinner spinner, Object value) {
    assertThat(valueOf(spinner)).as(propertyName(spinner, VALUE_PROPERTY)).isEqualTo(value);
  }
}
