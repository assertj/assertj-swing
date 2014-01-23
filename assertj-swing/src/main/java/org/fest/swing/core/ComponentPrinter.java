/*
 * Created on Mar 4, 2008
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

import java.awt.Component;
import java.awt.Container;
import java.io.PrintStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.format.Formatting;

/**
 * Sends the {@code String} representation of AWT and Swing {@code Component}s to a {@code java.io.PrintStream}, to
 * facilitate debugging.
 *
 * @author Alex Ruiz
 */
public interface ComponentPrinter {
  /**
   * Prints all the AWT and Swing {@code Component}s in the hierarchy.
   *
   * @param out the output stream where to print the {@code Component}s to.
   * @throws NullPointerException if the output stream is {@code null}.
   * @see Formatting#format(Component)
   */
  void printComponents(@Nonnull PrintStream out);

  /**
   * Prints all the AWT and Swing {@code Component}s in the hierarchy under the given root.
   *
   * @param out the output stream where to print the {@code Component}s to.
   * @param root the root used as the starting point of the search.
   * @throws NullPointerException if the output stream is {@code null}.
   * @see Formatting#format(Component)
   */
  void printComponents(@Nonnull PrintStream out, @Nullable Container root);

  /**
   * Prints only the AWT and Swing {@code Component}s of the given type in the hierarchy.
   *
   * @param out the output stream where to print the {@code Component}s to.
   * @param type the type of {@code Component}s to print.
   * @throws NullPointerException if the output stream is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @see Formatting#format(Component)
   */
  void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends Component> type);

  /**
   * Prints all the AWT and Swing {@code Component}s of the given type in the hierarchy under the given root.
   *
   * @param out the output stream where to print the {@code Component}s to.
   * @param type the type of {@code Component}s to print.
   * @param root the root used as the starting point of the search.
   * @throws NullPointerException if the output stream is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @see Formatting#format(Component)
   */
  void printComponents(@Nonnull PrintStream out, @Nonnull Class<? extends Component> type, @Nullable Container root);

  /**
   * Prints only the AWT and Swing {@code Component}s that match the given search criteria in the hierarchy.
   *
   * @param out the output stream where to print the {@code Component}s to.
   * @param matcher specifies the search criteria to use filter the {@code Component}s to print.
   * @throws NullPointerException if the output stream is {@code null}.
   * @throws NullPointerException if {@code matcher} is {@code null}.
   * @see Formatting#format(Component)
   */
  void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher);

  /**
   * Prints all the AWT and Swing {@code Component}s that match the given search criteria under the given root.
   *
   * @param out the output stream where to print the {@code Component}s to.
   * @param matcher specifies the search criteria to use filter the {@code Component}s to print.
   * @param root the root used as the starting point of the search.
   * @throws NullPointerException if the output stream is {@code null}.
   * @throws NullPointerException if {@code matcher} is {@code null}.
   * @see Formatting#format(Component)
   */
  void printComponents(@Nonnull PrintStream out, @Nonnull ComponentMatcher matcher, @Nullable Container root);
}