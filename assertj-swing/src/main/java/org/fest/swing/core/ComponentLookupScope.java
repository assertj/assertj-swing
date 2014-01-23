/*
 * Created on Jan 7, 2008
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

/**
 * Scopes of AWT or Swing {@code Component} lookups.
 * 
 * @author Alex Ruiz
 */
public enum ComponentLookupScope {
  /**
   * For most of the cases, only AWT or Swing {@code Component}s that are being shown on the screen participate in a
   * lookup. Currently, the only exception is {@code JMenuItem}, which participates in a lookup regardless if it is
   * showing or not.
   */
  DEFAULT(true),

  /**
   * All AWT or Swing {@code Component}s participate in a lookup, regardless if they are being shown on the screen or
   * not.
   */
  ALL(false),

  /** Only AWT or Swing {@code Component}s that are being shown on the screen can participate in a lookup. */
  SHOWING_ONLY(true);

  private final boolean requireShowing;

  private ComponentLookupScope(boolean requireShowing) {
    this.requireShowing = requireShowing;
  }

  /**
   * Returns whether showing AWT or Swing {@code Component}s are the only ones participating in lookups.
   * 
   * @return {@code true} if only showing {@code Component}s participate in lookups; {@code false} if any
   *         {@code Component} (showing or not showing) can participate in lookup.
   */
  public boolean requireShowing() {
    return requireShowing;
  }
}
