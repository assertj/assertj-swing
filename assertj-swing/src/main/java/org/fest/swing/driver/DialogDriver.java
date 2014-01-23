/*
 * Created on Feb 29, 2008
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
import static org.fest.swing.driver.DialogModalQuery.isModal;

import java.awt.Dialog;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.util.InternalApi;

/**
 * <p>
 * Supports functional testing of AWT or Swing {@code Dialog}s.
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
public class DialogDriver extends WindowDriver {
  /**
   * Creates a new {@link DialogDriver}.
   * 
   * @param robot the robot to use to simulate user input.
   */
  public DialogDriver(@Nonnull Robot robot) {
    super(robot);
  }

  /**
   * Asserts that the given AWT or Swing {@code Dialog} is modal.
   * 
   * @param dialog the given {@code Dialog}.
   * @throws AssertionError if the given {@code Dialog} is not modal.
   */
  @RunsInEDT
  public void requireModal(@Nonnull Dialog dialog) {
    assertThat(isModal(dialog)).as(propertyName(dialog, "modal")).isTrue();
  }
}
