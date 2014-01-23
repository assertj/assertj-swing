/*
 * Created on Feb 28, 2008
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
import static org.fest.swing.driver.AbstractButtonSelectedQuery.isSelected;
import static org.fest.swing.driver.ComponentPreconditions.checkEnabledAndShowing;
import static org.fest.swing.driver.TextAssert.verifyThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.AbstractButton;

import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of Swing {@code AbstractButton}s.
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
@InternalApi
public class AbstractButtonDriver extends JComponentDriver implements TextDisplayDriver<AbstractButton> {
  private static final String SELECTED_PROPERTY = "selected";
  private static final String TEXT_PROPERTY = "text";

  /**
   * Creates a new {@link AbstractButtonDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public AbstractButtonDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Asserts that the text in the given Swing {@code AbstractBuffon} is equal to or matches the specified
   * {@code String}.
   * 
   * @param button the given {@code AbstractBuffon}.
   * @param expected the text to match. It can be a regular expression.
   * @throws AssertionError if the text of the {@code AbstractBuffon} is not equal to or does not match the given one.
   */
  @RunsInEDT
  @Override
  public void requireText(@Nonnull AbstractButton button, @Nullable String expected) {
    verifyThat(textOf(button)).as(propertyName(button, TEXT_PROPERTY)).isEqualOrMatches(expected);
  }

  /**
   * Asserts that the text in the given Swing {@code AbstractBuffon} matches the given regular expression pattern.
   * 
   * @param button the given {@code AbstractBuffon}.
   * @param pattern the regular expression pattern to match.
   * @throws NullPointerException if the given regular expression pattern is {@code null}.
   * @throws AssertionError if the text of the {@code AbstractBuffon} does not match the given regular expression
   *           pattern.
   * @since 1.2
   */
  @Override
  public void requireText(@Nonnull AbstractButton button, @Nonnull Pattern pattern) {
    verifyThat(textOf(button)).as(propertyName(button, TEXT_PROPERTY)).matches(pattern);
  }

  /**
   * Returns the text of the given Swing {@code AbstractBuffon}.
   * 
   * @param button the given {@code AbstractBuffon}.
   * @return the text of the given {@code AbstractBuffon}.
   */
  @RunsInEDT
  @Override
  public @Nonnull String textOf(@Nonnull AbstractButton button) {
    return AbstractButtonTextQuery.textOf(button);
  }

  /**
   * Selects the given Swing {@code AbstractBuffon} only it is not already selected.
   * 
   * @param button the target {@code AbstractBuffon}.
   * @throws IllegalStateException if the {@code AbstractBuffon} is disabled.
   * @throws IllegalStateException if the {@code AbstractBuffon} is not showing on the screen.
   */
  @RunsInEDT
  public void select(@Nonnull AbstractButton button) {
    if (checkSelected(button)) {
      return;
    }
    robot.click(button);
  }

  /**
   * Deselects the given Swing {@code AbstractBuffon} only if it is selected.
   * 
   * @param button the target {@code AbstractBuffon}.
   * @throws IllegalStateException if the {@code AbstractBuffon} is disabled.
   * @throws IllegalStateException if the {@code AbstractBuffon} is not showing on the screen.
   */
  @RunsInEDT
  public void deselect(@Nonnull AbstractButton button) {
    if (!checkSelected(button)) {
      return;
    }
    robot.click(button);
  }

  @RunsInEDT
  private static boolean checkSelected(final @Nonnull AbstractButton button) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected @Nullable Boolean executeInEDT() {
        checkEnabledAndShowing(button);
        return button.isSelected();
      }
    });
    return checkNotNull(result);
  }

  /**
   * Verifies that the Swing {@code AbstractBuffon} is selected.
   * 
   * @param button the given {@code AbstractBuffon}.
   * @throws AssertionError if the button is not selected.
   */
  @RunsInEDT
  public void requireSelected(@Nonnull AbstractButton button) {
    assertThatButtonIsSelected(button, true);
  }

  /**
   * Verifies that the {@code AbstractBuffon} is not selected.
   * 
   * @param button the given {@code AbstractBuffon}.
   * @throws AssertionError if the {@code AbstractBuffon} is selected.
   */
  @RunsInEDT
  public void requireNotSelected(@Nonnull AbstractButton button) {
    assertThatButtonIsSelected(button, false);
  }

  @RunsInEDT
  private void assertThatButtonIsSelected(@Nonnull AbstractButton button, boolean selected) {
    assertThat(isSelected(button)).as(selectedProperty(button)).isEqualTo(selected);
  }

  @RunsInEDT
  private static @Nonnull Description selectedProperty(@Nonnull AbstractButton button) {
    return propertyName(button, SELECTED_PROPERTY);
  }
}
