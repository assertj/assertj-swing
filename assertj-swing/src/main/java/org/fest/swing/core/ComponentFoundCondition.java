/*
 * Created on Nov 15, 2007
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

import static java.util.Collections.emptyList;
import static org.fest.util.Strings.concat;
import static org.fest.util.SystemProperties.lineSeparator;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.assertions.BasicDescription;
import org.fest.assertions.Description;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Condition;

/**
 * Condition that is satisfied if an AWT or Swing {@code Component} that matches certain search criteria is found.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ComponentFoundCondition extends Condition {
  private final ComponentFinder finder;
  private final ComponentMatcher matcher;
  private final Container root;

  private Component found;

  private final AtomicReference<ComponentLookupException> notFoundError = new AtomicReference<ComponentLookupException>();

  /**
   * Creates a new {@link ComponentFoundCondition}.
   *
   * @param description the description of this condition.
   * @param finder performs the search.
   * @param matcher specifies the condition that the AWT or Swing {@code Component} we are looking for needs to match.
   */
  public ComponentFoundCondition(@Nonnull String description, @Nonnull ComponentFinder finder,
      @Nonnull ComponentMatcher matcher) {
    this(description, finder, matcher, null);
  }

  /**
   * Creates a new {@link ComponentFoundCondition}.
   *
   * @param description the description of this condition.
   * @param finder performs the search.
   * @param matcher specifies the condition that the AWT or Swing {@code Component} we are looking for needs to match.
   * @param root the root {@code Container} used as the starting point of the search.
   */
  public ComponentFoundCondition(@Nonnull String description, @Nonnull ComponentFinder finder,
      @Nonnull ComponentMatcher matcher, @Nullable Container root) {
    this(new BasicDescription(description), finder, matcher, root);
  }

  /**
   * Creates a new {@link ComponentFoundCondition}.
   *
   * @param description the description of this condition.
   * @param finder performs the search.
   * @param matcher specifies the condition that the AWT or Swing {@code Component} we are looking for needs to match.
   */
  public ComponentFoundCondition(@Nonnull Description description, @Nonnull ComponentFinder finder,
      @Nonnull ComponentMatcher matcher) {
    this(description, finder, matcher, null);
  }

  /**
   * Creates a new {@link ComponentFoundCondition}.
   *
   * @param description the description of this condition.
   * @param finder performs the search.
   * @param matcher specifies the condition that the AWT or Swing {@code Component} we are looking for needs to match.
   * @param root the root {@code Container} used as the starting point of the search.
   */
  public ComponentFoundCondition(@Nonnull Description description, @Nonnull ComponentFinder finder,
      @Nonnull ComponentMatcher matcher, @Nullable Container root) {
    super(description);
    this.finder = finder;
    this.matcher = matcher;
    this.root = root;
  }

  /**
   * Indicates whether {@code true} an AWT or Swing {@code Component}, that matches the search criteria in this
   * condition's {@link ComponentMatcher}, can be found.
   *
   * @return {@code true} if a matching {@code Component} can be found, {@code false} otherwise.
   */
  @Override
  public boolean test() {
    boolean matchFound = false;
    try {
      found = finder.find(root, matcher);
      matchFound = true;
    } catch (ComponentLookupException e) {
      notFoundError.set(e);
    }
    resetMatcher(matchFound);
    if (matchFound) {
      notFoundError.set(null);
    }
    return matchFound;
  }

  private void resetMatcher(boolean matchFound) {
    if (!(matcher instanceof ResettableComponentMatcher)) {
      return;
    }
    ((ResettableComponentMatcher) matcher).reset(matchFound);
  }

  /**
   * @return the AWT {@code Component} hierarchy to be added to this condition's description in case of a lookup failure.
   */
  @Override
  protected @Nonnull String descriptionAddendum() {
    ComponentLookupException error = notFoundError.get();
    if (error == null) {
      return EMPTY_TEXT;
    }
    return concat(lineSeparator(), error.getMessage());
  }

  /**
   * @return the AWT or Swing {@code Component} found.
   */
  public @Nullable Component found() {
    return found;
  }

  /**
   * @return all the AWT or Swing {@code Component}s that satisfied the search criteria specified by this condition's
   *         {@code ComponentMatcher}.
   */
  public Collection<? extends Component> duplicatesFound() {
    ComponentLookupException error = notFoundError.get();
    if (error == null) {
      return emptyList();
    }
    return error.found();
  }
}
