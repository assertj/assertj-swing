/*
 * Created on Aug 6, 2007
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

import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * {@link ComponentMatcher} that matches an AWT or Swing {@code Component} by type and some custom search criteria. It
 * uses generics to improve type safety.
 * 
 * @param <T> the type of {@code Component} supported by this matcher.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public abstract class GenericTypeMatcher<T extends Component> extends AbstractComponentMatcher {
  private final Class<T> supportedType;

  /**
   * Creates a new {@link GenericTypeMatcher}. The {@code Component} to match does not have to be showing.
   * 
   * @param supportedType the type supported by this matcher.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public GenericTypeMatcher(@Nonnull Class<T> supportedType) {
    this(supportedType, false);
  }

  /**
   * Creates a new {@link GenericTypeMatcher}.
   * 
   * @param supportedType the type supported by this matcher.
   * @param requireShowing indicates if the {@code Component} to match should be showing or not.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public GenericTypeMatcher(@Nonnull Class<T> supportedType, boolean requireShowing) {
    super(requireShowing);
    this.supportedType = checkNotNull(supportedType);
  }

  /**
   * <p>
   * Verifies that the given AWT or Swing {@code Component}:
   * <ol>
   * <li>Is an instance of the generic type specified in this {@link ComponentMatcher}</li>
   * <li>Matches some search criteria</li>
   * </ol>
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param c the {@code Component} to verify.
   * @return {@code true} if the given {@code Component} is an instance of the generic type of this matcher and matches
   *         some search criteria. Otherwise, {@code false}.
   */
  @Override
  @RunsInCurrentThread
  public final boolean matches(@Nullable Component c) {
    if (c == null) {
      return false;
    }
    if (!supportedType.isInstance(c)) {
      return false;
    }
    try {
      return (requireShowingMatches(c)) && isMatching(supportedType.cast(c));
    } catch (ClassCastException ignored) {
      return false;
    }
  }

  /**
   * @return the supported type of this matcher.
   */
  public final @Nonnull Class<T> supportedType() {
    return supportedType;
  }

  /**
   * <p>
   * Verifies that the given AWT or Swing {@code Component} matches some search criteria.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param component the {@code Component} to verify.
   * @return {@code true} if the given {@code Component} matches the defined search criteria; otherwise, {@code false} .
   */
  @RunsInCurrentThread
  protected abstract boolean isMatching(@Nonnull T component);
}
