/*
 * Created on Mar 12, 2010
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
package org.fest.swing.keystroke;

import static java.lang.Thread.currentThread;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.fest.swing.keystroke.KeyStrokeMapping.mapping;
import static org.fest.swing.keystroke.KeyStrokeMappingProvider.NO_MASK;
import static org.fest.util.Closeables.closeQuietly;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;
import org.fest.swing.exception.ParsingException;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * Creates {@link KeyStrokeMapping}s by parsing a text file.
 * </p>
 *
 * <p>
 * Mappings for the following characters:
 * <ul>
 * <li>Backspace</li>
 * <li>Delete</li>
 * <li>Enter</li>
 * <li>Escape</li>
 * <li>Tab</li>
 * </ul>
 * will be automatically added and should <strong>not</strong> be included to the file to parse.
 * </p>
 *
 * <p>
 * The following is an example of a mapping file:
 * <pre>
 * a, A, NO_MASK
 * A, A, SHIFT_MASK
 * COMMA, COMMA, NO_MASK
 * </pre>
 * Each line represents a character-keystroke mapping where each value is separated by a comma.
 * </p>
 *
 * <p>
 * The first value represents the character to map. For example 'a' or 'A'. Since each field is separated by a comma, to
 * map the ',' character we need to specify the text "COMMA."
 * </p>
 *
 * <p>
 * The second value represents the key code, which should be the name of a key code from {@link KeyEvent} without the
 * prefix "VK_". For example, if the key code is {@link KeyEvent#VK_COMMA} we just need to specify "COMMA".
 * </p>
 *
 * <p>
 * The third value represents any modifiers to use, which should be the name of a modifier from a {@code InputEvent}.
 * For example, if the modifier to use is {@code InputEvent.SHIFT_MASK} we need to specify "SHIFT_MASK". If no modifiers
 * are necessary, we just specify "NO_MASK".
 * </p>
 *
 * @author Olivier DOREMIEUX
 * @author Alex Ruiz
 *
 * @since 1.2
 */
public class KeyStrokeMappingsParser {
  private static final Map<String, Character> SPECIAL_MAPPINGS = newHashMap();

  static {
    SPECIAL_MAPPINGS.put("COMMA", ',');
  }

  /**
   * <p>
   * Creates a {@link KeyStrokeMappingProvider} containing all the character-keystroke mappings specified in the file
   * with the given name.
   * </p>
   *
   * <p>
   * <strong>Note:</strong> This attempts to read the file using {@link ClassLoader#getResourceAsStream(String)}.
   * </p>
   *
   * @param fileName the name of the file to parse.
   * @return the created {@code KeyStrokeMappingProvider}.
   * @throws NullPointerException if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   * @throws ParsingException if any error occurs during parsing.
   * @see #parse(File)
   */
  public @Nonnull KeyStrokeMappingProvider parse(@Nonnull String fileName) {
    checkNotNullOrEmpty(fileName);
    try {
      return parse(fileAsStream(fileName));
    } catch (IOException e) {
      throw new ParsingException(concat("An I/O error ocurred while parsing file ", fileName), e);
    }
  }

  private @Nonnull InputStream fileAsStream(String file) {
    InputStream stream = currentThread().getContextClassLoader().getResourceAsStream(file);
    if (stream == null) {
      throw new ParsingException(String.format("Unable to open file %s", file));
    }
    return stream;
  }

  /**
   * Creates a {@link KeyStrokeMappingProvider} containing all the character-keystroke mappings specified in the given
   * file.
   *
   * @param file the file to parse.
   * @return the created {@code KeyStrokeMappingProvider}.
   * @throws NullPointerException if the given file is {@code null}.
   * @throws AssertionError if the given file does not represent an existing file.
   * @throws ParsingException if any error occurs during parsing.
   */
  public @Nonnull KeyStrokeMappingProvider parse(@Nonnull File file) {
    assertThat(file).isFile();
    try {
      return parse(fileAsStream(file));
    } catch (IOException e) {
      throw new ParsingException(concat("An I/O error ocurred while parsing file ", file), e);
    }
  }

  private @Nonnull InputStream fileAsStream(@Nonnull File file) {
    try {
      return new FileInputStream(file);
    } catch (FileNotFoundException e) {
      String msg = String.format("The file %s was not found", file.getPath());
      throw new ParsingException(msg, e);
    }
  }

  private @Nonnull KeyStrokeMappingProvider parse(@Nonnull InputStream input) throws IOException {
    List<KeyStrokeMapping> mappings = newArrayList();
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    try {
      String line = reader.readLine();
      while (line != null) {
        mappings.add(mappingFrom(line));
        line = reader.readLine();
      }
      return new ParsedKeyStrokeMappingProvider(mappings);
    } finally {
      closeQuietly(reader);
    }
  }

  @VisibleForTesting
  @Nonnull KeyStrokeMapping mappingFrom(@Nonnull String line) {
    String[] parts = line.trim().split(",");
    if (parts.length != 3) {
      String msg = String.format("Line '%s' does not conform with pattern '{char}, {keycode}, {modifiers}'", line);
      throw new ParsingException(msg);
    }
    char character = characterFrom(parts[0].trim());
    int keyCode = keyCodeFrom(parts[1].trim());
    int modifiers = modifiersFrom(parts[2].trim());
    return mapping(character, keyCode, modifiers);
  }

  private static char characterFrom(@Nonnull String s) {
    if (SPECIAL_MAPPINGS.containsKey(s)) {
      return SPECIAL_MAPPINGS.get(s);
    }
    if (s.length() == 1) {
      return s.charAt(0);
    }
    throw new ParsingException(String.format("The text '%s' should have a single character", s));
  }

  private static int keyCodeFrom(@Nonnull String s) {
    try {
      Integer keyCode = field("VK_" + s).ofType(int.class).in(KeyEvent.class).get();
      return checkNotNull(keyCode);
    } catch (ReflectionError e) {
      throw new ParsingException(concat("Unable to retrieve key code from text ", quote(s)), e.getCause());
    }
  }

  private static int modifiersFrom(@Nonnull String s) {
    if ("NO_MASK".equals(s)) {
      return NO_MASK;
    }
    try {
      Integer modifiers = field(s).ofType(int.class).in(InputEvent.class).get();
      return checkNotNull(modifiers);
    } catch (ReflectionError e) {
      throw new ParsingException(concat("Unable to retrieve modifiers from text ", quote(s)), e.getCause());
    }
  }
}
