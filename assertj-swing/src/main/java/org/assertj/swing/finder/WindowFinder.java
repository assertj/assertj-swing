/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.finder;

import java.awt.Dialog;
import java.awt.Frame;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.swing.core.GenericTypeMatcher;

/**
 * <p>
 * Looks up {@code Frame}s and {@code Dialog}s. Lookups are performed till the window of interest is found, or until the
 * given time to perform the lookup is over. The default lookup time is 5 seconds.
 * </p>
 *
 * <p>
 * {@link WindowFinder} is the &quot;entry point&quot; of a fluent interface to look up frames and dialogs. This example
 * illustrates finding a {@code Frame} by name, using the default lookup time (5 seconds):
 * </p>
 *
 * <pre>
 * FrameFixture frame = WindowFinder.findFrame(&quot;someFrame&quot;).using(robot);
 * </pre>
 *
 * <p>
 * Where {@code robot} is an instance of {@link org.assertj.swing.core.Robot}.
 * </p>
 *
 * <p>
 * This example shows how to find a {@code Dialog} by type using a lookup time of 10 seconds:
 * </p>
 *
 * <pre>
 * DialogFixture dialog = WindowFinder.findDialog(MyDialog.class).withTimeout(10000).using(robot);
 * </pre>
 *
 * <p>
 * We can also specify the time unit:
 * </p>
 *
 * <pre>
 * DialogFixture dialog = WindowFinder.findDialog(MyDialog.class).withTimeout(10, {@link java.util.concurrent.TimeUnit#SECONDS SECONDS}).using(robot);
 * </pre>
 *
 * <p>
 * This example shows how to use a {@link GenericTypeMatcher} to find a {@code Frame} with title "Hello":
 * </p>
 *
 * <pre>
 * GenericTypeMatcher&lt;JFrame&gt; matcher = new GenericTypeMatcher&lt;JFrame&gt;() {
 *   protected boolean isMatching(JFrame frame) {
 *     return &quot;hello&quot;.equals(frame.getTitle());
 *   }
 * };
 * FrameFixture frame = WindowFinder.findFrame(matcher).using(robot);
 * </pre>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class WindowFinder {
  private WindowFinder() {
  }

  /**
   * Creates a new {@link FrameFinder} capable of looking up a {@code Frame} by name.
   *
   * @param frameName the name of the frame to find.
   * @return the created finder.
   */
  @Nonnull public static FrameFinder findFrame(@Nullable String frameName) {
    return new FrameFinder(frameName);
  }

  /**
   * Creates a new {@link FrameFinder} capable of looking up a {@code Frame} by type.
   *
   * @param frameType the type of the frame to find.
   * @return the created finder.
   */
  @Nonnull public static FrameFinder findFrame(@Nonnull Class<? extends Frame> frameType) {
    return new FrameFinder(frameType);
  }

  /**
   * Creates a new {@link FrameFinder} capable of looking up a {@code Frame} using the provided matcher.
   *
   * @param matcher the matcher to use to find a frame.
   * @return the created finder.
   */
  @Nonnull public static FrameFinder findFrame(@Nonnull GenericTypeMatcher<? extends Frame> matcher) {
    return new FrameFinder(matcher);
  }

  /**
   * Creates a new {@link DialogFinder} capable of looking up a {@code Dialog} by name.
   *
   * @param dialogName the name of the dialog to find.
   * @return the created finder.
   */
  @Nonnull public static DialogFinder findDialog(@Nullable String dialogName) {
    return new DialogFinder(dialogName);
  }

  /**
   * Creates a new {@link DialogFinder} capable of looking up a {@code Dialog} by type.
   *
   * @param dialogType the type of the dialog to find.
   * @return the created finder.
   */
  @Nonnull public static DialogFinder findDialog(@Nonnull Class<? extends Dialog> dialogType) {
    return new DialogFinder(dialogType);
  }

  /**
   * Creates a new {@link DialogFinder} capable of looking up a {@code Dialog} using the provided matcher.
   *
   * @param matcher the matcher to use to find a dialog.
   * @return the created finder.
   */
  @Nonnull public static DialogFinder findDialog(@Nonnull GenericTypeMatcher<? extends Dialog> matcher) {
    return new DialogFinder(matcher);
  }
}
