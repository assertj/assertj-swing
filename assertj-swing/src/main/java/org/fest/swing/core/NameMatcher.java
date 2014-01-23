/*
 * Created on Dec 22, 2007
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
package org.fest.swing.core;

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Matches an AWT or Swing {@code Component} by name and (optionally) by type.
 * 
 * @author Alex Ruiz
 */
public final class NameMatcher extends AbstractComponentMatcher {
  private final String name;
  private final Class<? extends Component> type;

  /**
   * Creates a new {@link NameMatcher}. The AWT or Swing {@code Component} to match does not have to be showing.
   * 
   * @param name the name of the {@code Component} we are looking for.
   * @throws NullPointerException if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public NameMatcher(@Nullable String name) {
    this(name, false);
  }

  /**
   * Creates a new {@link NameMatcher}.
   * 
   * @param name the name of the AWT or Swing {@code Component} we are looking for.
   * @param requireShowing indicates if the {@code Component} to match should be showing or not.
   * @throws NullPointerException if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public NameMatcher(@Nullable String name, boolean requireShowing) {
    this(name, Component.class, requireShowing);
  }

  /**
   * Creates a new {@link NameMatcher}. The AWT or Swing {@code Component} to match does not have to be showing.
   * 
   * @param name the name of the {@code Component} we are looking for.
   * @param type the type of the {@code Component} we are looking for.
   * @throws NullPointerException if the given name is empty.
   * @throws IllegalArgumentException if the given name is empty.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public NameMatcher(@Nullable String name, @Nonnull Class<? extends Component> type) {
    this(name, type, false);
  }

  /**
   * Creates a new {@link NameMatcher}.
   * 
   * @param name the name of the AWT or Swing {@code Component} we are looking for.
   * @param type the type of the {@code Component} we are looking for.
   * @param requireShowing indicates if the {@code Component} to match should be showing or not.
   * @throws NullPointerException if the given name is empty.
   * @throws IllegalArgumentException if the given name is empty.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public NameMatcher(@Nullable String name, @Nonnull Class<? extends Component> type, boolean requireShowing) {
    super(requireShowing);
    this.name = checkNotNullOrEmpty(name);
    this.type = checkNotNull(type);
  }

  /**
   * <p>
   * Indicates whether the name and visibility of the given AWT or Swing {@code Component} matches the value specified
   * in this matcher.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @return {@code true} if the name and visibility of the given {@code Component} matches the values specified in this
   *         matcher, {@code false} otherwise.
   */
  @Override
  @RunsInCurrentThread
  public boolean matches(@Nullable Component c) {
    if (c == null) {
      return false;
    }
    return areEqual(name, c.getName()) && type.isInstance(c) && requireShowingMatches(c);
  }

  @Override
  public String toString() {
    String format = "%s[name='%s', type=%s, requireShowing=%b]";
    return String.format(format, getClass().getName(), name, type.getName(), requireShowing());
  }
}
