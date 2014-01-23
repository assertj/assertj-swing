/*
 * Created on Oct 23, 2008
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

import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Strings.concat;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Verifies correct state of a {@code Component}.
 * 
 * @author Alex Ruiz
 */
public final class ComponentPreconditions {
  /**
   * <p>
   * Verifies that the {@code Component} is enabled and showing.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the target component.
   * @throws IllegalStateException if the {@code Component} is disabled.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInCurrentThread
  public static void checkEnabledAndShowing(@Nonnull Component c) {
    checkEnabled(c);
    checkShowing(c);
  }

  /**
   * <p>
   * Verifies that the {@code Component} is enabled.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the target component.
   * @throws IllegalStateException if the {@code Component} is disabled.
   */
  @RunsInCurrentThread
  public static void checkEnabled(@Nonnull Component c) {
    if (!c.isEnabled()) {
      throw new IllegalStateException(concat("Expecting component ", format(c), " to be enabled"));
    }
  }

  /**
   * <p>
   * Verifies that the {@code Component} is showing on the screen.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the target component.
   * @throws IllegalStateException if the {@code Component} is not showing on the screen.
   */
  @RunsInCurrentThread
  public static void checkShowing(@Nonnull Component c) {
    if (!c.isShowing()) {
      String msg = String.format("Expecting component %s to be showing on the screen", format(c));
      throw new IllegalStateException(msg);
    }
  }

  private ComponentPreconditions() {}
}
