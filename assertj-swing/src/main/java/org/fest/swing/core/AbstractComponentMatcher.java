/*
 * Created on Oct 19, 2008
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
package org.fest.swing.core;

import java.awt.Component;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Template for implementations of {@link ResettableComponentMatcher}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class AbstractComponentMatcher implements ResettableComponentMatcher {
  private boolean requireShowing;

  /**
   * Creates a new {@link AbstractComponentMatcher}. The created matcher does not require the AWT or Swing
   * {@code Component} to match to be showing.
   */
  public AbstractComponentMatcher() {
    this(false);
  }

  /**
   * Creates a new {@link AbstractComponentMatcher}.
   * 
   * @param requireShowing indicates whether the AWT or Swing {@code Component} to match should be showing or not.
   */
  public AbstractComponentMatcher(boolean requireShowing) {
    requireShowing(requireShowing);
  }

  /**
   * Indicates whether the AWT or Swing {@code Component} to match has to be showing.
   * 
   * @return {@code true} if the {@code Component} to find has to be showing, {@code false} otherwise.
   */
  protected final boolean requireShowing() {
    return requireShowing;
  }

  /**
   * Updates the value of the flag that indicates if the AWT or Swing {@code Component} to match should be showing or
   * not.
   * 
   * @param shouldBeShowing the new value to set.
   */
  protected final void requireShowing(boolean shouldBeShowing) {
    requireShowing = shouldBeShowing;
  }

  /**
   * <p>
   * Indicates if the value of the "showing" property of the given AWT or Swing {@code Component} matches the value
   * specified in this matcher.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the component to verify.
   * @return {@code true} if the value of the "isShowing" property of the given component matches the value specified in
   *         this matcher, {@code false} otherwise.
   */
  @RunsInCurrentThread
  protected final boolean requireShowingMatches(@Nonnull Component c) {
    return !requireShowing || c.isShowing();
  }

  /**
   * Resets the internal state of this matcher.
   * 
   * @param matchFound indicates whether a match has been found before resetting.
   * @since 1.2
   */
  @Override
  public void reset(boolean matchFound) {}
}
