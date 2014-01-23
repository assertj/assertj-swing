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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.hierarchy.NewHierarchy.ignoreExistingComponents;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Component;
import java.awt.Container;
import java.io.PrintStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.format.Formatting;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.hierarchy.SingleComponentHierarchy;

/**
 * Default implementation of {@link ComponentPrinter}.
 *
 * @author Alex Ruiz
 *
 * @see ComponentPrinter
 * @see Formatting#format(Component)
 */
public final class BasicComponentPrinter implements ComponentPrinter {
  private static final String INDENTATION = "  ";

  private static final ComponentMatcher ALWAYS_MATCHES = alwaysMatches();

  private static @Nonnull ComponentMatcher alwaysMatches() {
    return new ComponentMatcher() {
      @Override
      public boolean matches(@Nullable Component c) {
        return true;
      }
    };
  }

  private final ComponentHierarchy hierarchy;

  /**
   * Creates a new {@link BasicComponentPrinter} with a new AWT hierarchy. AWT and Swing {@code Component}s created
   * before the created {@link BasicComponentPrinter} cannot be accessed by the created {@link BasicComponentPrinter}.
   *
   * @return the created finder.
   */
  public static @Nonnull ComponentPrinter printerWithNewAwtHierarchy() {
    return new BasicComponentPrinter(ignoreExistingComponents());
  }

  /**
   * Creates a new {@link BasicComponentPrinter} that has access to all the AWT and Swing {@code Component}s in the AWT
   * hierarchy.
   *
   * @return the created printer.
   */
  public static @Nonnull ComponentPrinter printerWithCurrentAwtHierarchy() {
    return new BasicComponentPrinter(new ExistingHierarchy());
  }

  /**
   * Creates a new {@link BasicComponentPrinter}.
   *
   * @param hierarchy the component hierarchy to use.
   */
  protected BasicComponentPrinter(@Nonnull ComponentHierarchy hierarchy) {
    this.hierarchy = hierarchy;
  }

  /**
   * @return the component hierarchy used by this printer.
   */
  protected final @Nonnull ComponentHierarchy hierarchy() {
    return hierarchy;
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void printComponents(@Nonnull PrintStream out) {
    printComponents(out, ALWAYS_MATCHES);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void printComponents(@Nonnull PrintStream out, @Nullable Container root) {
    printComponents(out, ALWAYS_MATCHES, root);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends Component> type) {
    printComponents(out, type, null);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public void printComponents(
      @Nonnull PrintStream out, @Nonnull Class<? extends Component> type, @Nullable Container root) {
    print(hierarchy(root), new TypeMatcher(checkNotNull(type)), checkNotNull(out));
  }

  /** ${@inheritDoc} */
  @Override
  public void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher) {
    printComponents(out, matcher, null);
  }

  /** ${@inheritDoc} */
  @Override
  public void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher, @Nullable Container root) {
    print(hierarchy(root), checkNotNull(matcher), checkNotNull(out));
  }

  private @Nonnull ComponentHierarchy hierarchy(@Nullable Container root) {
    return root != null ? new SingleComponentHierarchy(root, hierarchy) : hierarchy;
  }

  @RunsInEDT
  private static void print(@Nonnull final ComponentHierarchy hierarchy, @Nonnull final ComponentMatcher matcher,
      @Nonnull final PrintStream out) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        for (Component c : hierarchy.roots()) {
          print(checkNotNull(c), hierarchy, matcher, 0, out);
        }
      }
    });
  }

  @RunsInCurrentThread
  private static void print(@Nonnull Component c, @Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher matcher,
      int level, @Nonnull PrintStream out) {
    if (matcher.matches(c)) {
      print(c, level, out);
    }
    for (Component child : h.childrenOf(c)) {
      print(checkNotNull(child), h, matcher, level + 1, out);
    }
  }

  @RunsInCurrentThread
  private static void print(@Nonnull Component c, int level, @Nonnull PrintStream out) {
    for (int i = 0; i < level; i++) {
      out.print(INDENTATION);
    }
    out.println(format(c));
  }
}
