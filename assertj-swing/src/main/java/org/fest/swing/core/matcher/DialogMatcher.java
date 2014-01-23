/*
 * Created on Jul 16, 2008
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
package org.fest.swing.core.matcher;

import java.awt.Dialog;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Matches AWT or Swing {@code Dialog}s by name, title or visibility on the screen.
 *
 * @author Alex Ruiz
 */
public final class DialogMatcher extends NamedComponentMatcherTemplate<Dialog> {
  private Object title;

  /**
   * <p>
   * Creates a new {@link DialogMatcher} that matches an AWT or Swing {@code Dialog} by name.
   * </p>
   *
   * <p>
   * The following code listing shows how to match a {@code Dialog} by name and title:
   * <pre>
   * DialogMatcher m = {@link #withName(String) withName}("saveFile").{@link #andTitle(String) andTitle}("Save File");
   * </pre>
   * </p>
   *
   * <p>
   * The following code listing shows how to match a {@code Dialog}, that should be showing on the screen, by name and
   * title:
   * <pre>
   * DialogMatcher m = {@link #withName(String) withName}("saveFile").{@link #andTitle(String) andTitle}("Save File").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   *
   * @param name the id to match.
   * @return the created matcher.
   */
  public static DialogMatcher withName(@Nullable String name) {
    return new DialogMatcher(name, anyValue());
  }

  /**
   * <p>
   * Creates a new {@link DialogMatcher} that matches an AWT or Swing {@code Dialog} by title.
   * </p>
   *
   * <p>
   * The following code listing shows how to match a {@code Dialog} title:
   * <pre>
   * DialogMatcher m = {@link #withTitle(String) withTitle}("Save File");
   * </pre>
   * </p>
   *
   * <p>
   * The following code listing shows how to match a {@code Dialog}, that should be showing on the screen,
   * by title:
   * <pre>
   * DialogMatcher m = {@link #withTitle(String) withTitle}("Save File").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   *
   * @param title the title to match. It can be a regular expression.
   * @return the created matcher.
   */
  public static @Nonnull DialogMatcher withTitle(@Nullable String title) {
    return new DialogMatcher(anyValue(), title);
  }

  /**
   * <p>
   * Creates a new {@link DialogMatcher} that matches an AWT or Swing {@code Dialog} by title.
   * </p>
   *
   * <p>
   * The following code listing shows how to match a {@code Dialog} title, using a regular expression pattern:
   * <pre>
   * DialogMatcher m = {@link #withTitle(Pattern) withTitle}(Pattern.compile("Sav.*"));
   * </pre>
   * </p>
   *
   * <p>
   * The following code listing shows how to match a {@code Dialog}, that should be showing on the screen, by
   * title, using a regular expression pattern:
   * <pre>
   * DialogMatcher m = {@link #withTitle(Pattern) withTitle}(Pattern.compile("Sav.*")).{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   *
   * @param pattern the regular expression pattern to match.
   * @return the created matcher.
   * @since 1.2
   */
  public static @Nonnull DialogMatcher withTitle(@Nonnull Pattern pattern) {
    return new DialogMatcher(anyValue(), pattern);
  }

  /**
   * Creates a new {@link DialogMatcher} that matches any {@code Dialog}.
   *
   * @return the created matcher.
   */
  public static @Nonnull DialogMatcher any() {
    return new DialogMatcher(anyValue(), anyValue());
  }

  private DialogMatcher(@Nullable Object name, @Nullable Object title) {
    super(Dialog.class, name);
    this.title = title;
  }

  /**
   * Specifies the title to match. If this matcher was created using {@link #withTitle(String)} or
   * {@link #withTitle(Pattern)}, this method will simply update the title to match.
   *
   * @param newTitle the new title to match. It can be a regular expression.
   * @return this matcher.
   */
  public @Nonnull DialogMatcher andTitle(@Nullable String newTitle) {
    title = newTitle;
    return this;
  }

  /**
   * Specifies the title to match. If this matcher was created using {@link #withTitle(String)}, or
   * {@link #withTitle(Pattern)} this method will simply update the title to match.
   *
   * @param titlePattern the regular expression pattern to match.
   * @return this matcher.
   * @since 1.2
   */
  public DialogMatcher andTitle(@Nonnull Pattern titlePattern) {
    title = titlePattern;
    return this;
  }

  /**
   * Indicates that the AWT or Swing {@code Dialog} to match should be showing on the screen.
   *
   * @return this matcher.
   */
  public @Nonnull DialogMatcher andShowing() {
    requireShowing(true);
    return this;
  }

  /**
   * <p>
   * Indicates whether the name and title of the given AWT or Swing {@code Dialog} match the ones in this matcher.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param dialog the {@code Dialog} to match.
   * @return {@code true} if the {@code Dialog} matches the search criteria in this matcher.
   */
  @RunsInCurrentThread
  @Override
  protected boolean isMatching(@Nonnull Dialog dialog) {
    return isNameMatching(dialog.getName()) && arePropertyValuesMatching(title, dialog.getTitle());
  }

  @Override
  public String toString() {
    String format = "%s[name=%s, title=%s, requireShowing=%b]";
    return String.format(format, getClass().getName(), quotedName(), quoted(title), requireShowing());
  }
}
