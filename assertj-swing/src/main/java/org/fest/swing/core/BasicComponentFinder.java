/*
 * Created on May 14, 2007
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.format.Formatting.format;
import static org.fest.swing.hierarchy.NewHierarchy.ignoreExistingComponents;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;
import static org.fest.util.SystemProperties.lineSeparator;

import java.awt.Component;
import java.awt.Container;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JLabel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.hierarchy.ComponentHierarchy;
import org.fest.swing.hierarchy.ExistingHierarchy;
import org.fest.swing.hierarchy.SingleComponentHierarchy;

/**
 * Default implementation of {@link ComponentFinder}.
 *
 * @author Alex Ruiz
 *
 * @see ComponentFinder
 */
public final class BasicComponentFinder implements ComponentFinder {
  private final ComponentHierarchy hierarchy;
  private final ComponentPrinter printer;
  private final Settings settings;

  private final FinderDelegate finderDelegate = new FinderDelegate();

  private boolean includeHierarchyInComponentLookupException;

  /**
   * Creates a new {@link BasicComponentFinder} with a new AWT hierarchy. AWT and Swing {@code Component}s created
   * before the created {@link BasicComponentFinder} cannot be accessed by the created {@link BasicComponentFinder}.
   *
   * @return the created finder.
   */
  public static @Nonnull ComponentFinder finderWithNewAwtHierarchy() {
    return new BasicComponentFinder(ignoreExistingComponents());
  }

  /**
   * Creates a new {@link BasicComponentFinder} that has access to all the AWT and Swing {@code Component}s in the AWT
   * hierarchy.
   *
   * @return the created finder.
   */
  public static @Nonnull ComponentFinder finderWithCurrentAwtHierarchy() {
    return new BasicComponentFinder(new ExistingHierarchy());
  }

  /**
   * Creates a new {@link BasicComponentFinder}. The created finder does not use any {@link Settings}.
   *
   * @param hierarchy the component hierarchy to use.
   */
  protected BasicComponentFinder(@Nonnull ComponentHierarchy hierarchy) {
    this(hierarchy, null);
  }

  /**
   * Creates a new {@link BasicComponentFinder}.
   *
   * @param hierarchy the component hierarchy to use.
   * @param settings the configuration settings to use. It can be {@code null}.
   */
  protected BasicComponentFinder(@Nonnull ComponentHierarchy hierarchy, @Nullable Settings settings) {
    this.hierarchy = hierarchy;
    this.settings = settings;
    printer = new BasicComponentPrinter(hierarchy);
    includeHierarchyIfComponentNotFound(true);
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull ComponentPrinter printer() {
    return printer;
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull <T extends Component> T findByType(@Nonnull Class<T> type) {
    return findByType(type, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByType(@Nonnull Class<T> type, boolean showing) {
    return type.cast(find(new TypeMatcher(type, showing)));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByType(@Nonnull Container root, @Nonnull Class<T> type) {
    return findByType(root, type, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByType(@Nonnull Container root, @Nonnull Class<T> type, boolean showing) {
    return type.cast(find(root, new TypeMatcher(type, showing)));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByName(@Nullable String name, @Nonnull Class<T> type) {
    return findByName(name, type, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByName(@Nullable String name, @Nonnull Class<T> type, boolean showing) {
    Component found = find(new NameMatcher(name, type, showing));
    return type.cast(found);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByName(@Nullable String name) {
    return findByName(name, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByName(@Nullable String name, boolean showing) {
    return find(new NameMatcher(name, showing));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByLabel(@Nullable String label, @Nonnull Class<T> type) {
    return findByLabel(label, type, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByLabel(@Nullable String label, @Nonnull Class<T> type, boolean showing) {
    Component found = find(new LabelMatcher(label, type, showing));
    return labelFor(found, type);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByLabel(@Nullable String label) {
    return findByLabel(label, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByLabel(@Nullable String label, boolean showing) {
    Component found = find(new LabelMatcher(label, showing));
    return labelFor(found, Component.class);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T find(@Nonnull GenericTypeMatcher<T> m) {
    Component found = find((ComponentMatcher) m);
    return m.supportedType().cast(found);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component find(@Nonnull ComponentMatcher m) {
    return find(hierarchy, m);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByName(
      @Nonnull Container root, @Nullable String name, @Nonnull Class<T> type) {
    return findByName(root, name, type, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByName(
      @Nonnull Container root, @Nullable String name, @Nonnull Class<T> type, boolean showing) {
    Component found = find(root, new NameMatcher(name, type, showing));
    return type.cast(found);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByName(@Nonnull Container root, @Nullable String name) {
    return findByName(root, name, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByName(@Nonnull Container root, @Nullable String name, boolean showing) {
    return find(root, new NameMatcher(name, showing));
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByLabel(
      @Nonnull Container root, @Nullable String label, @Nonnull Class<T> type) {
    return findByLabel(root, label, type, requireShowing());
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T findByLabel(
      @Nonnull Container root, @Nullable String label, @Nonnull Class<T> type, boolean showing) {
    Component found = find(root, new LabelMatcher(label, type, showing));
    return labelFor(found, type);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByLabel(@Nonnull Container root, @Nullable String label) {
    return findByLabel(root, label, requireShowing());
  }

  private boolean requireShowing() {
    return requireShowingFromSettingsOr(false);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component findByLabel(@Nonnull Container root, @Nullable String label, boolean showing) {
    Component found = find(root, new LabelMatcher(label, showing));
    return labelFor(found, Component.class);
  }

  private @Nonnull <T> T labelFor(@Nonnull Component label, @Nonnull Class<T> type) {
    assertThat(label).isInstanceOf(JLabel.class);
    Component target = ((JLabel) label).getLabelFor();
    assertThat(target).isInstanceOf(type);
    return type.cast(target);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull <T extends Component> T find(@Nonnull Container root, @Nonnull GenericTypeMatcher<T> m) {
    Component found = find(root, (ComponentMatcher) m);
    return m.supportedType().cast(found);
  }

  /** {@inheritDoc} */
  @RunsInEDT
  @Override
  public @Nonnull Component find(@Nullable Container root, @Nonnull ComponentMatcher m) {
    return find(hierarchy(root), m);
  }

  @RunsInEDT
  private @Nonnull Component find(@Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
    Collection<Component> found = finderDelegate.find(h, m);
    if (found.isEmpty()) {
      throw componentNotFound(h, m);
    }
    if (found.size() > 1) {
      throw multipleComponentsFound(found, m);
    }
    return checkNotNull(found.iterator().next());
  }

  @RunsInEDT
  private @Nonnull ComponentLookupException componentNotFound(
      @Nonnull ComponentHierarchy h, @Nonnull ComponentMatcher m) {
    String message = concat("Unable to find component using matcher ", m, ".");
    if (includeHierarchyIfComponentNotFound()) {
      message = concat(message, lineSeparator(), lineSeparator(), "Component hierarchy:", lineSeparator(),
          formattedHierarchy(root(h)));
    }
    throw new ComponentLookupException(message);
  }

  private static @Nullable Container root(@Nullable ComponentHierarchy h) {
    if (h instanceof SingleComponentHierarchy) {
      return ((SingleComponentHierarchy) h).root();
    }
    return null;
  }

  @RunsInEDT
  private @Nonnull String formattedHierarchy(@Nullable Container root) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(out, true);
    printer.printComponents(printStream, root);
    printStream.flush();
    return new String(out.toByteArray());
  }

  @RunsInEDT
  private static @Nonnull ComponentLookupException multipleComponentsFound(
      @Nonnull Collection<Component> found, @Nonnull ComponentMatcher m) {
    StringBuilder message = new StringBuilder();
    String format = "Found more than one component using matcher %s. %n%nFound:";
    message.append(String.format(format, m.toString()));
    appendComponents(message, found);
    if (!found.isEmpty()) {
      message.append(lineSeparator());
    }
    throw new ComponentLookupException(message.toString(), found);
  }

  @RunsInEDT
  private static void appendComponents(final @Nonnull StringBuilder message,
      final @Nonnull Collection<Component> found) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        for (Component c : found) {
          message.append(String.format("%n%s", format(c)));
        }
      }
    });
  }

  /** {@inheritDoc} */
  @Override
  public boolean includeHierarchyIfComponentNotFound() {
    return includeHierarchyInComponentLookupException;
  }

  /** {@inheritDoc} */
  @Override
  public void includeHierarchyIfComponentNotFound(boolean newValue) {
    includeHierarchyInComponentLookupException = newValue;
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull Collection<Component> findAll(@Nonnull ComponentMatcher m) {
    return finderDelegate.find(hierarchy, m);
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull Collection<Component> findAll(@Nonnull Container root, @Nonnull ComponentMatcher m) {
    return finderDelegate.find(hierarchy(root), m);
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull <T extends Component> Collection<T> findAll(@Nonnull GenericTypeMatcher<T> m) {
    return finderDelegate.find(hierarchy, m);
  }

  /** {@inheritDoc} */
  @Override
  public @Nonnull <T extends Component> Collection<T> findAll(
      @Nonnull Container root, @Nonnull GenericTypeMatcher<T> m) {
    ComponentHierarchy h = hierarchy(root);
    return finderDelegate.find(h, m);
  }

  /**
   * Returns the value of the flag "requireShowing" in the {@link ComponentLookupScope} this finder's {@link Settings}.
   * If the settings object is {@code null}, this method will return the provided default value.
   *
   * @param defaultValue the value to return if this matcher does not have any configuration settings.
   * @return the value of the flag "requireShowing" in this finder's settings, or the provided default value if this
   *         finder does not have configuration settings.
   */
  protected final boolean requireShowingFromSettingsOr(boolean defaultValue) {
    if (settings == null) {
      return defaultValue;
    }
    return settings.componentLookupScope().requireShowing();
  }

  private @Nonnull ComponentHierarchy hierarchy(@Nullable Container root) {
    if (root == null) {
      return hierarchy;
    }
    return new SingleComponentHierarchy(root, hierarchy);
  }
}
