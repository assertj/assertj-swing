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
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Matches {@code JLabel}s by name, text or visibility on the screen.
 * 
 * @author Alex Ruiz
 */
public final class JLabelMatcher extends NamedComponentMatcherTemplate<JLabel> {
  private Object text;

  /**
   * <p>
   * Creates a new {@link JLabelMatcher} that matches a {@code JLabel} by text.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JLabel} by name and text:
   * <pre>
   * JLabelMatcher m = {@link #withName(String) withName}("firstName").{@link #andText(String) andText}("First Name:");
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JLabel}, that should be showing on the screen, by name and
   * text:
   * <pre>
   * JLabelMatcher m = {@link #withName(String) withName}("firstName").{@link #andText(String) andText}("First Name:").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param name the id to match.
   * @return the created matcher.
   */
  public static @Nonnull JLabelMatcher withName(@Nullable String name) {
    return new JLabelMatcher(name, anyValue());
  }

  /**
   * <p>
   * Creates a new {@link JLabelMatcher} that matches a {@code JLabel} by text.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JLabel} by text:
   * <pre>
   * JLabelMatcher m = {@link #withText(String) withText}("First Name:");
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JLabel}, that should be showing on the screen, by text:
   * <pre>
   * JLabelMatcher m = {@link #withText(String) withText}("First Name:").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param text the text to match. It can be a regular expression.
   * @return the created matcher.
   */
  public static @Nonnull JLabelMatcher withText(@Nullable String text) {
    return new JLabelMatcher(anyValue(), text);
  }

  /**
   * <p>
   * Creates a new {@link JLabelMatcher} that matches a {@code JLabel} by text.
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JLabel} by text:
   * <pre>
   * JLabelMatcher m = {@link #withText(Pattern) withText}(Pattern.compile("F.*");
   * </pre>
   * </p>
   * 
   * <p>
   * The following code listing shows how to match a {@code JLabel}, that should be showing on the screen, by text:
   * <pre>
   * JLabelMatcher m = {@link #withText(Pattern) withText}(Pattern.compile("F.*").{@link #andShowing() andShowing}();
   * </pre>
   * </p>
   * 
   * @param textPattern the regular expression pattern to match.
   * @return the created matcher.
   * @since 1.2
   */
  public static @Nonnull JLabelMatcher withText(@Nonnull Pattern textPattern) {
    return new JLabelMatcher(anyValue(), textPattern);
  }

  /**
   * Creates a new {@link JLabelMatcher} that matches any {@code JLabel}.
   * 
   * @return the created matcher.
   */
  public static @Nonnull JLabelMatcher any() {
    return new JLabelMatcher(anyValue(), anyValue());
  }

  private JLabelMatcher(@Nullable Object name, @Nullable Object text) {
    super(JLabel.class, name);
    this.text = text;
  }

  /**
   * Specifies the text to match. If this matcher was created using {@link #withText(String)} or
   * {@link #withText(Pattern)}, this method will simply update the text to match.
   * 
   * @param newText the new text to match. It can be a regular expression.
   * @return this matcher.
   */
  public @Nonnull JLabelMatcher andText(@Nullable String newText) {
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
  public @Nonnull JLabelMatcher andText(@Nonnull Pattern textPattern) {
    text = textPattern;
    return this;
  }

  /**
   * Indicates that the {@code JLabel} to match should be showing on the screen.
   * 
   * @return this matcher.
   */
  public JLabelMatcher andShowing() {
    requireShowing(true);
    return this;
  }

  /**
   * <p>
   * Indicates whether the name and text of the given {@code JLabel} match the ones in this matcher.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param label the {@code JLabel} to match.
   * @return {@code true} if the text in the {@code JLabel} is equal to the text in this matcher, {@code false}
   *         otherwise.
   */
  @RunsInCurrentThread
  @Override
  protected boolean isMatching(@Nonnull JLabel label) {
    return isNameMatching(label.getName()) && arePropertyValuesMatching(text, label.getText());
  }

  @Override
  public String toString() {
    String format = "%s[name=%s, text=%s, requireShowing=%b]";
    return String.format(format, getClass().getName(), quotedName(), quoted(text), requireShowing());
  }
}
