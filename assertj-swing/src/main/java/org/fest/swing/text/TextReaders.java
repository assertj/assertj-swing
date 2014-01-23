/*
 * Created on Jul 29, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.text;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Maps.newConcurrentHashMap;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;

import java.awt.Component;
import java.awt.Container;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.util.VisibleForTesting;

/**
 * Registry of {@link TextReader}s.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.3
 */
public class TextReaders {
  private static Logger logger = Logger.getLogger(TextReaders.class.getCanonicalName());

  @VisibleForTesting
  final ConcurrentMap<Class<?>, TextReader<?>> readers = newConcurrentHashMap();

  @VisibleForTesting
  TextReaders() {
    register(new AbstractButtonTextReader());
    register(new JLabelTextReader());
    register(new JListTextReader());
    register(new JTextComponentTextReader());
  }

  /**
   * Adds the given {@code TextReader} to this registry.
   * 
   * @param reader the {@code TextReader} to add.
   * @throws NullPointerException if the given {@code TextReader} is {@code null}.
   * @throws NullPointerException if the supported {@code Component} type in the given {@code TextReader} is
   *           {@code null}.
   */
  public void register(@Nonnull TextReader<?> reader) {
    checkNotNull(reader);
    Class<?> type = checkNotNull(reader.supportedComponent());
    TextReader<?> old = readers.put(type, reader);
    if (old != null) {
      logger.info(concat("Replaced reader for type ", type.getName()));
    }
  }

  /**
   * Indicates whether the given {@code Container} or any of its subcomponents contains the given text.
   * 
   * @param container the given {@code Container}. This method is executed in the event dispatch thread (EDT.)
   * @param text the text to look for.
   * @return {@code true} if the given {@code Container} or any of its subcomponents contains the given text;
   *         {@code false} otherwise.
   * @throws NullPointerException if the given {@code Container} is {@code null}.
   * @throws NullPointerException if the given text is {@code null}.
   */
  @RunsInEDT
  public boolean containsText(final @Nonnull Container container, final @Nonnull String text) {
    checkNotNull(container);
    checkNotNull(text);
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected @Nullable Boolean executeInEDT() {
        if (componentContainsText(container, text)) {
          return true;
        }
        return anyComponentContainsText(container.getComponents(), text);
      }
    });
    return checkNotNull(result);
  }

  private boolean anyComponentContainsText(@Nonnull Component[] components, @Nonnull String text) {
    for (Component c : components) {
      if (c == null) {
        continue;
      }
      if (componentContainsText(c, text)) {
        return true;
      }
      if (c instanceof Container) {
        Component[] children = ((Container) c).getComponents();
        return anyComponentContainsText(children, text);
      }
    }
    return false;
  }

  private boolean componentContainsText(@Nonnull Component c, @Nonnull String text) {
    TextReader<?> reader = readerFor(c);
    if (reader == null) {
      return false;
    }
    return reader.containsText(c, text);
  }

  private @Nullable TextReader<?> readerFor(@Nonnull Component c) {
    Class<?> type = c.getClass();
    while (type != null) {
      TextReader<?> reader = readers.get(type);
      if (reader != null) {
        return reader;
      }
      if (type.equals(Component.class)) {
        break;
      }
      type = type.getSuperclass();
    }
    return null;
  }

  /**
   * @return the singleton instance of this class.
   */
  public static @Nonnull TextReaders instance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {
    static final TextReaders INSTANCE = new TextReaders();
  }
}
