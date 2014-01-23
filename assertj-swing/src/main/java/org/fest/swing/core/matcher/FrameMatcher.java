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

import java.awt.Frame;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Matches AWT or Swing {@code Frame}s by name, title or visibility on the screen.
 * 
 * @author Alex Ruiz
 */
public final class FrameMatcher extends NamedComponentMatcherTemplate<Frame> {
  private Object title;

  /**
   * <p>
   * Creates a new {@link FrameMatcher} that matches an AWT or Swing {@code Frame} by name.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code Frame} by name and title:
   * <pre>
   * FrameMatcher m = {@link #withName(String) withName}("myApp").{@link #andTitle(String) andTitle}("My App");
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code Frame}, that should be showing on the screen, by name and
   * title:
   * <pre>
   * FrameMatcher m = {@link #withName(String) withName}("myApp").{@link #andTitle(String) andTitle}("My App").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param name the id to match.
   * @return the created matcher.
   */
  public static FrameMatcher withName(@Nullable String name) {
    return new FrameMatcher(name, anyValue());
  }

  /**
   * <p>
   * Creates a new {@link FrameMatcher} that matches an AWT or Swing {@code Frame} by title.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code Frame} by title:
   * <pre>
   * FrameMatcher m = {@link #withTitle(String) withTitle}("My App");
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code Frame}, that should be showing on the screen, by title:
   * <pre>
   * FrameMatcher m = {@link #withTitle(String) withTitle}("My App").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param title the title to match. It can be a regular expression.
   * @return the created matcher.
   */
  public static FrameMatcher withTitle(@Nonnull String title) {
    return new FrameMatcher(anyValue(), title);
  }

  /**
   * <p>
   * Creates a new {@link FrameMatcher} that matches an AWT or Swing {@code Frame} by title.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code Frame} by title, using a regular expression matcher:
   * <pre>
   * FrameMatcher m = {@link #withTitle(Pattern) withTitle}(Pattern.compile("My.*"));
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code Frame}, that should be showing on the screen, by title:
   * <pre>
   * FrameMatcher m = {@link #withTitle(Pattern) withTitle}(Pattern.compile("My.*")).{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param pattern the title to match.
   * @return the created matcher.
   * @since 1.2
   */
  public static @Nonnull FrameMatcher withTitle(@Nonnull Pattern pattern) {
    return new FrameMatcher(anyValue(), pattern);
  }

  /**
   * Creates a new {@link FrameMatcher} that matches any {@code java.awt.Frame}.
   * 
   * @return the created matcher.
   */
  public static @Nonnull FrameMatcher any() {
    return new FrameMatcher(anyValue(), anyValue());
  }

  private FrameMatcher(@Nullable Object name, @Nullable Object title) {
    super(Frame.class, name);
    this.title = title;
  }

  /**
   * Specifies the title to match. If this matcher was created using {@link #withTitle(String)} or
   * {@link FrameMatcher#withTitle(Pattern)}, this method will simply update the title to match.
   * 
   * @param newTitle the new title to match. It can be a regular expression.
   * @return this matcher.
   */
  public @Nonnull FrameMatcher andTitle(@Nonnull String newTitle) {
    title = newTitle;
    return this;
  }

  /**
   * Specifies the title to match. If this matcher was created using {@link #withTitle(String)} or
   * {@link FrameMatcher#withTitle(Pattern)}, this method will simply update the title to match.
   * 
   * @param titlePattern the regular expression pattern to match.
   * @return this matcher.
   * @since 1.2
   */
  public @Nonnull FrameMatcher andTitle(@Nonnull Pattern titlePattern) {
    title = titlePattern;
    return this;
  }

  /**
   * Indicates that the AWT or Swing {@code Frame} to match should be showing on the screen.
   * 
   * @return this matcher.
   */
  public @Nonnull FrameMatcher andShowing() {
    requireShowing(true);
    return this;
  }

  /**
   * <p>
   * Indicates whether the name and title of the given AWT or Swing {@code Frame} match the ones in this matcher.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param frame the {@code Frame} to match.
   * @return {@code true} if the title in the {@code Frame} is equal to the title in this matcher, {@code false}
   *         otherwise.
   */
  @RunsInCurrentThread
  @Override
  protected boolean isMatching(@Nonnull Frame frame) {
    return isNameMatching(frame.getName()) && arePropertyValuesMatching(title, frame.getTitle());
  }

  @Override
  public String toString() {
    String format = "%s[name=%s, title=%s, requireShowing=%b]";
    return String.format(format, getClass().getName(), quotedName(), quoted(title), requireShowing());
  }
}
