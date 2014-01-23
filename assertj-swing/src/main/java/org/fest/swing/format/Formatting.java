/*
 * Created on Sep 16, 2007
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
package org.fest.swing.format;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Maps.newConcurrentHashMap;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.isNullOrEmpty;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.text.JTextComponent;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.util.VisibleForTesting;

/**
 * Utility methods related to formatting.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Formatting {
  private static final String MAXIMUM = "maximum";
  private static final String MINIMUM = "minimum";
  private static final String NULL_COMPONENT_MESSAGE = "Null Component";

  private static final String ENABLED = "enabled";
  private static final String NAME = "name";
  private static final String SHOWING = "showing";
  private static final String TEXT = "text";
  private static final String TITLE = "title";
  private static final String VALUE = "value";
  private static final String VISIBLE = "visible";

  private static final ConcurrentMap<Class<?>, ComponentFormatter> FORMATTERS = newConcurrentHashMap();

  private static Logger logger = Logger.getLogger(Formatting.class.getCanonicalName());

  static {
    register(instrospect(AbstractButton.class, NAME, TEXT, "selected", ENABLED, VISIBLE, SHOWING));
    register(instrospect(Dialog.class, NAME, TITLE, ENABLED, "modal", VISIBLE, SHOWING));
    register(instrospect(Frame.class, NAME, TITLE, ENABLED, VISIBLE, SHOWING));
    register(new JComboBoxFormatter());
    register(instrospect(JButton.class, NAME, TEXT, ENABLED, VISIBLE, SHOWING));
    register(new JFileChooserFormatter());
    register(instrospect(JLabel.class, NAME, TEXT, ENABLED, VISIBLE, SHOWING));
    register(empty(JLayeredPane.class));
    register(new JListFormatter());
    register(empty(JMenuBar.class));
    register(new JOptionPaneFormatter());
    register(nameOnly(JPanel.class));
    register(instrospect(JPopupMenu.class, NAME, "label", ENABLED, VISIBLE, SHOWING));
    register(instrospect(JProgressBar.class, NAME, VALUE, MINIMUM, MAXIMUM, "string", "stringPainted", ENABLED,
        VISIBLE, SHOWING));
    register(empty(JRootPane.class));
    register(instrospect(JScrollBar.class, NAME, VALUE, "blockIncrement", MINIMUM, MAXIMUM, ENABLED, VISIBLE, SHOWING));
    register(instrospect(JScrollPane.class, NAME, ENABLED, VISIBLE, SHOWING));
    register(instrospect(JSlider.class, NAME, VALUE, MINIMUM, MAXIMUM, ENABLED, VISIBLE, SHOWING));
    register(instrospect(JSpinner.class, NAME, VALUE, ENABLED, VISIBLE, SHOWING));
    register(new JTabbedPaneFormatter());
    register(new JTableFormatter());
    register(nameOnly(JToolBar.class));
    register(instrospect(JPasswordField.class, NAME, ENABLED, VISIBLE, SHOWING));
    register(instrospect(JTextComponent.class, NAME, TEXT, ENABLED, VISIBLE, SHOWING));
    register(new JTreeFormatter());
  }

  private static @Nonnull ComponentFormatter instrospect(@Nonnull Class<? extends Component> targetType,
      @Nonnull String... propertyNames) {
    return new IntrospectionComponentFormatter(targetType, propertyNames);
  }

  private static @Nonnull ComponentFormatter empty(@Nonnull Class<? extends Component> targetType) {
    return new IntrospectionComponentFormatter(targetType);
  }

  private static @Nonnull ComponentFormatter nameOnly(@Nonnull Class<? extends Component> targetType) {
    return new IntrospectionComponentFormatter(targetType, NAME);
  }

  /**
   * Registers the given {@link ComponentFormatter}, replacing any other one previously registered for the same
   * supported component type.
   *
   * @param formatter the formatter to register.
   */
  public static void register(@Nonnull ComponentFormatter formatter) {
    Class<?> key = formatter.targetType();
    ComponentFormatter previous = FORMATTERS.put(key, formatter);
    if (previous != null) {
      String format = "Replaced formatter %s with %s for type %s";
      logger.info(String.format(format, previous.toString(), formatter.toString(), key.getName()));
    }
  }

  @VisibleForTesting
  static ComponentFormatter formatter(@Nonnull Class<?> type) {
    return FORMATTERS.get(type);
  }

  /**
   * Returns a {@code String} representation of the given AWT or Swing {@code Component}. This method is invoked in the
   * event dispatch thread (EDT.)
   *
   * @param c the given {@code Component}.
   * @return a {@code String} representation of the given {@code Component}.
   */
  @RunsInEDT
  public static @Nonnull String inEdtFormat(final @Nonnull Component c) {
    String result = execute(new GuiQuery<String>() {
      @Override
      protected @Nullable String executeInEDT() {
        return format(c);
      }
    });
    return checkNotNull(result);
  }

  /**
   * <p>
   * Returns a {@code String} representation of the given AWT or Swing {@code Component}.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @return a {@code String} representation of the given {@code Component}.
   */
  @RunsInCurrentThread
  public static @Nonnull String format(@Nullable Component c) {
    if (c == null) {
      return NULL_COMPONENT_MESSAGE;
    }
    ComponentFormatter formatter = formatterFor(c.getClass());
    if (formatter != null) {
      return formatter.format(c);
    }
    String name = c.getName();
    if (isNullOrEmpty(name)) {
      return c.toString();
    }
    return String.format("%s[name=%s]", c.getClass().getName(), name);
  }

  private static @Nullable ComponentFormatter formatterFor(@Nonnull Class<?> type) {
    ComponentFormatter formatter = FORMATTERS.get(type);
    if (formatter != null) {
      return formatter;
    }
    Class<?> superType = type.getSuperclass();
    if (superType != null) {
      return formatterFor(superType);
    }
    return null;
  }

  private Formatting() {}
}
