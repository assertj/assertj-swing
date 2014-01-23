/*
 * Created on Oct 29, 2007
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
package org.fest.swing.finder;

import java.awt.Component;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JFileChooser;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.JFileChooserFixture;

/**
 * <p>
 * Looks up {@code JFileChooser}s. Lookups are performed till a file chooser is found, or until the given time to
 * perform the lookup is over. The default lookup time is 5 seconds.
 * </p>
 *
 * <p>
 * This example illustrates finding a {@code JFileChooser} by name, using the default lookup time (5 seconds):
 *
 * <pre>
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().using(robot);
 * </pre>
 *
 * </p>
 *
 * <p>
 * Where {@code robot} is an instance of {@link org.fest.swing.core.Robot}.
 * </p>
 *
 * <p>
 * This example shows how to find a {@code JFileChooser} by type using a lookup time of 10 seconds:
 *
 * <pre>
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().withTimeout(10000).using(robot);
 * </pre>
 *
 * We can also specify the time unit:
 *
 * <pre>
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().withTimeout(10, {@link java.util.concurrent.TimeUnit#SECONDS SECONDS}).using(robot);
 * </pre>
 *
 * </p>
 *
 * <p>
 * This examples shows how to find a {@code JFileChooser} using a {@link GenericTypeMatcher}:
 *
 * <pre>
 * GenericTypeMatcher&lt;JFileChooser&gt; matcher = new GenericTypeMatcher&lt;JFileChooser&gt;() {
 *   protected boolean isMatching(JFileChooser fileChooser) {
 *     return fileChooser.getCurrentDirectory().getAbsolutePath().equals(&quot;c:\\temp&quot;);
 *   }
 * };
 * JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser(matcher).using(robot);
 * </pre>
 *
 * </p>
 *
 * @author Alex Ruiz
 */
public class JFileChooserFinder extends ComponentFinderTemplate<JFileChooser> {
  /**
   * Creates a new {@link JFileChooserFinder}. This finder looks up a {@code JFileChooser} by type.
   */
  protected JFileChooserFinder() {
    super(JFileChooser.class);
  }

  /**
   * Creates a new {@link JFileChooserFinder}.
   *
   * @param name the name of the {@code FileChooser} to look for.
   */
  protected JFileChooserFinder(@Nullable String name) {
    super(name, JFileChooser.class);
  }

  /**
   * Creates a new {@link JFileChooserFinder}.
   *
   * @param matcher specifies the search criteria to use when looking up a {@code JFileChooser}.
   */
  protected JFileChooserFinder(@Nonnull GenericTypeMatcher<? extends JFileChooser> matcher) {
    super(matcher);
  }

  /**
   * Creates a new {@link JFileChooserFinder} capable of looking up a {@code JFileChooser}.
   *
   * @return the created finder.
   */
  public static @Nonnull JFileChooserFinder findFileChooser() {
    return new JFileChooserFinder();
  }

  /**
   * Creates a new {@link JFileChooserFinder} capable of looking up a {@code JFileChooser} by name.
   *
   * @param name the name of the file chooser to find.
   * @return the created finder.
   */
  public static @Nonnull JFileChooserFinder findFileChooser(@Nullable String name) {
    return new JFileChooserFinder(name);
  }

  /**
   * Creates a new {@link JFileChooserFinder} capable of looking up a {@code JFileChooser} using the given matcher.
   *
   * @param matcher the given matcher.
   * @return the created finder.
   */
  public static @Nonnull JFileChooserFinder findFileChooser(
      @Nonnull GenericTypeMatcher<? extends JFileChooser> matcher) {
    return new JFileChooserFinder(matcher);
  }

  /**
   * Finds a {@code JFileChooser} by name or type.
   *
   * @param robot contains the underlying finding to delegate the search to.
   * @return a {@code JFileChooserFixture} managing the found {@code JFileChooser}.
   * @throws org.fest.swing.exception.WaitTimedOutError if a {@code JFileChooser} could not be found.
   */
  @Override
  public @Nonnull JFileChooserFixture using(@Nonnull Robot robot) {
    return new JFileChooserFixture(robot, findComponentWith(robot));
  }

  /**
   * Sets the timeout for this finder. The {@code JFileChooser} to find should be found within the given time period.
   *
   * @param timeout the number of milliseconds before stopping the search.
   * @return this finder.
   * @throws IllegalArgumentException if the timeout is a negative number.
   */
  @Override
  public @Nonnull JFileChooserFinder withTimeout(@Nonnegative long timeout) {
    super.withTimeout(timeout);
    return this;
  }

  /**
   * Sets the timeout for this finder. The {@code JFileChooser} to find should be found within the given time period.
   *
   * @param timeout the period of time the search should be performed.
   * @param unit the time unit for {@code timeout}.
   * @return this finder.
   * @throws NullPointerException if the time unit is {@code null}.
   * @throws IllegalArgumentException if the timeout is a negative number.
   */
  @Override
  public @Nonnull JFileChooserFinder withTimeout(@Nonnegative long timeout, @Nonnull TimeUnit unit) {
    super.withTimeout(timeout, unit);
    return this;
  }

  /**
   * Casts the given AWT or Swing {@code Component} to {@code JFileChooser}.
   *
   * @return the given {@code Component}, casted to {@code JFileChooser}.
   */
  @Override
  protected @Nullable JFileChooser cast(@Nullable Component c) {
    return (JFileChooser) c;
  }
}
