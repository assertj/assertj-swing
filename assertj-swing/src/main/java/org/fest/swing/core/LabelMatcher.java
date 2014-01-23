/*
 * Created on Dec 2, 2008
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

import static org.fest.util.Objects.areEqual;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.quote;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Matches an AWT or Swing {@code Component} by the text of the associated {@code JLabel} and
 * (optionally) by type.
 * 
 * @see JLabel#getLabelFor()
 * @see JLabel#setLabelFor(Component)
 * 
 * @author Alex Ruiz
 */
public class LabelMatcher extends AbstractComponentMatcher {
  private final String label;
  private final Class<? extends Component> type;

  /**
   * Creates a new {@link LabelMatcher}. The AWT or Swing {@code Component} to match does not have to be showing.
   * 
   * @param label the text of the label associated to the {@code Component} we are looking for.
   * @throws NullPointerException if the given label is {@code null}.
   * @throws IllegalArgumentException if the given label is empty.
   */
  public LabelMatcher(@Nullable String label) {
    this(label, false);
  }

  /**
   * Creates a new {@link LabelMatcher}.
   * 
   * @param label the text of the label associated to the AWT or Swing {@code Component} we are looking for.
   * @param requireShowing indicates if the {@code Component} to match should be showing or not.
   * @throws NullPointerException if the given label is {@code null}.
   * @throws IllegalArgumentException if the given label is empty.
   */
  public LabelMatcher(@Nullable String label, boolean requireShowing) {
    this(label, Component.class, requireShowing);
  }

  /**
   * Creates a new {@link LabelMatcher}. The AWT or Swing {@code Component} to match does not have to be showing.
   * 
   * @param label the text of the label associated to the {@code Component} we are looking for.
   * @param type the type of the {@code Component} we are looking for.
   * @throws NullPointerException if the given label is {@code null}.
   * @throws IllegalArgumentException if the given label is empty.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public LabelMatcher(@Nullable String label, @Nonnull Class<? extends Component> type) {
    this(label, type, false);
  }

  /**
   * Creates a new {@link LabelMatcher}.
   * 
   * @param label the text of the label associated to the AWT or Swing {@code Component} we are looking for.
   * @param type the type of the {@code Component} we are looking for.
   * @param requireShowing indicates if the {@code Component} to match should be showing or not.
   * @throws NullPointerException if the given label is {@code null}.
   * @throws IllegalArgumentException if the given label is empty.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public LabelMatcher(@Nullable String label, @Nonnull Class<? extends Component> type, boolean requireShowing) {
    super(requireShowing);
    this.label = checkNotNullOrEmpty(label);
    this.type = checkNotNull(type);
  }

  /**
   * <p>
   * Indicates whether the given AWT or Swing {@code Component} matches the criteria specified in this matcher:
   * <ol>
   * <li>the text of the {@code JLabel} attached to the {@code Component} to look for matches the text specified in
   * this matcher</li>
   * <li>the {@code Component} to look for is of the type specified in this matcher (if specified)</li>
   * <li>visibility of the given {@code Component} matches the value specified in this matcher</li>
   * </ol>
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
    if (!(c instanceof JLabel)) {
      return false;
    }
    JLabel labelForComponent = (JLabel) c;
    if (!areEqual(labelForComponent.getText(), label)) {
      return false;
    }
    Component labeled = labelForComponent.getLabelFor();
    return type.isInstance(labeled) && requireShowingMatches(checkNotNull(labeled));
  }

  @Override
  public String toString() {
    String format = "%s[label=%s, type=%s, requireShowing=%b]";
    return String.format(format, getClass().getName(), quote(label), type.getName(), requireShowing());
  }
}
