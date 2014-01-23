/*
 * Created on Feb 23, 2008
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

import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

import java.awt.Component;

import javax.accessibility.AccessibleAction;
import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Executes the default (first) {@code Action} in an AWT or Swing {@code Component}'s {@code AccessibleAction}.
 * </p>
 * 
 * <p>
 * This task is <b>not</b> guaranteed to be executed in the event dispatch thread (EDT.)
 * </p>
 * 
 * @author Alex Ruiz
 */
class ComponentPerformDefaultAccessibleActionTask {
  private static final int DEFAULT_ACTION_INDEX = 0;

  @RunsInCurrentThread
  static void performDefaultAccessibleAction(final @Nonnull Component c) {
    AccessibleAction action = c.getAccessibleContext().getAccessibleAction();
    if (action == null || action.getAccessibleActionCount() == 0) {
      throw actionFailure(concat("Unable to perform accessible action for ", format(c)));
    }
    action.doAccessibleAction(DEFAULT_ACTION_INDEX);
  }
}
