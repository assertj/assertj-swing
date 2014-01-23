/*
 * Created on Jul 17, 2008
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

import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JButton;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Matches {@code JButton}s by name, text or visibility on the screen.
 * 
 * @author Alex Ruiz
 */
public final class JButtonMatcher extends NamedComponentMatcherTemplate<JButton> {
  private Object text;

  /**
   * <p>
   * Creates a new {@link JButtonMatcher} that matches a {@code JButton} by name:
   * </p>
   * <p>
   * The following code listing shows how to match a {@code JButton} by name and text:
   * 
   * <pre>
   * JButtonMatcher m = {@link #withName(String) withName}("ok").{@link #andText(String) andText}("OK");
   * </pre>
   * 
   * </p>
   * <p>
   * The following code listing shows how to match a {@code JButton}, that should be showing on the screen, by name and
   * text:
   * 
   * <pre>
   * JButtonMatcher m = {@link #withName(String) withName}("ok").{@link #andText(String) andText}("OK").{@link #andShowing() andShowing}();
   * </pre>
   * 
   * </p>
   * 
   * @param name the name to match.
   * @return the created matcher.
   */
  public static @Nonnull JButtonMatcher withName(@Nullable String name) {
    return new JButtonMatcher(name, anyValue());
  }

  /**
   * <p>
   * Creates a new {@link JButtonMatcher} that matches a {@code JButton} by text.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JButton} by text:
   * <pre>
   * JButtonMatcher m = {@link #withText(String) withText}("OK");
   * </pre>
   * 
   * </p>
   * <p>
   * The following code listing shows how to match a {@code JButton}, that should be showing on the screen, by text:
   * <pre>
   * JButtonMatcher m = {@link #withText(String) withText}("OK").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param text the text to match. It can be a regular expression.
   * @return the created matcher.
   */
  public static @Nonnull JButtonMatcher withText(@Nullable String text) {
    return new JButtonMatcher(anyValue(), text);
  }

  /**
   * <p>
   * Creates a new {@link JButtonMatcher} that matches a {@code JButton} by text.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JButton} by text, using a regular expression pattern:
   * <pre>
   * JButtonMatcher m = {@link #withText(Pattern) withText}(Pattern.compile("O.*"));
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JButton}, that should be showing on the screen, by text,
   * using a regular expression pattern:
   * <pre>
   * JButtonMatcher m = {@link #withText(Pattern) withText}(Pattern.compile("O.*")).{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param pattern the regular expression pattern to match.
   * @return the created matcher.
   * @since 1.2
   */
  public static @Nonnull JButtonMatcher withText(@Nonnull Pattern pattern) {
    return new JButtonMatcher(anyValue(), pattern);
  }

  /**
   * Creates a new {@link JButtonMatcher} that matches any {@code JButton}.
   * 
   * @return the created matcher.
   */
  public static JButtonMatcher any() {
    return new JButtonMatcher(anyValue(), anyValue());
  }

  private JButtonMatcher(@Nullable Object name, @Nullable Object text) {
    super(JButton.class, name);
    this.text = text;
  }

  /**
   * Specifies the text to match. If this matcher was created using {@link #withText(String)} or
   * {@link #withText(Pattern)}, this method will simply update the text to match.
   * 
   * @param newText the new text to match. It can be a regular expression.
   * @return this matcher.
   */
  public @Nonnull JButtonMatcher andText(@Nullable String newText) {
    text = newText;
    return this;
  }

  /**
   * Specifies the text to match. If this matcher was created using {@link #withText(String)} or
   * {@link #withText(Pattern)}, this method will simply update the text to match.
   * 
   * @param textPattern the regular expression pattern to match.
   * @return this matcher.
   * @since 1.2
   */
  public JButtonMatcher andText(@Nonnull Pattern textPattern) {
    text = textPattern;
    return this;
  }

  /**
   * Indicates that the {@code JButton} to match should be showing on the screen.
   * 
   * @return this matcher.
   */
  public @Nonnull JButtonMatcher andShowing() {
    requireShowing(true);
    return this;
  }

  /**
   * <p>
   * Indicates whether the name and text of the given {@code JButton} match the ones in this matcher.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param button the {@code JButton} to match.
   * @return {@code true} if the {@code JButton} matches the search criteria in this matcher.
   */
  @RunsInCurrentThread
  @Override
  protected boolean isMatching(@Nonnull JButton button) {
    return isNameMatching(button.getName()) && arePropertyValuesMatching(text, button.getText());
  }

  @Override
  public String toString() {
    String format = "%s[name=%s, text=%s, requireShowing=%b]";
    return String.format(format, getClass().getName(), quotedName(), quoted(text), requireShowing());
  }
}
