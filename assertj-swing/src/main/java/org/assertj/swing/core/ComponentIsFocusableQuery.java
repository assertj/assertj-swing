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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.assertj.core.util.Preconditions;
import org.assertj.swing.annotation.RunsInEDT;

/**
 * Indicates whether an AWT or Swing {@code Component} is focusable or not.
 * 
 * @see Component#isFocusable()
 * 
 * @author Yvonne Wang
 */
final class ComponentIsFocusableQuery {
  @RunsInEDT
  static boolean isFocusable(final @Nonnull Component component) {
    Boolean result = execute(() -> component.isFocusable());
    return Preconditions.checkNotNull(result);
  }

  private ComponentIsFocusableQuery() {
  }
}
