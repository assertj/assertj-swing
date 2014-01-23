/*
 * Created on Mar 14, 2010
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

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_TAB;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.keystroke.KeyStrokeMapping.mapping;
import static org.fest.swing.keystroke.KeyStrokeMappingProvider.NO_MASK;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.util.Platform.isWindows;
import static org.fest.util.Closeables.closeQuietly;
import static org.fest.util.Files.newTemporaryFile;
import static org.fest.util.Flushables.flushQuietly;
import static org.fest.util.SystemProperties.lineSeparator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.fest.swing.exception.ParsingException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMappingsParser#parse(String)}.
 *
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProvider_parse_Test {
  private KeyStrokeMappingsParser parser;

  @Before
  public void setUp() {
    parser = new KeyStrokeMappingsParser();
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_file_name_is_null() {
    String file = null;
    parser.parse(file);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_file_name_is_empty() {
    parser.parse("");
  }

  @Test
  public void should_parse_file_in_classpath() {
    KeyStrokeMappingProvider mappingProvider = parser.parse("keyboard-mapping.txt");
    assertThatContainsDefaultMappings(mappingProvider);
    Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
    assertThat(mappings).contains(mapping('a', VK_A, NO_MASK), mapping('A', VK_A, SHIFT_MASK),
        mapping(',', VK_COMMA, NO_MASK));
  }

  @Test
  public void should_throw_error_if_file_not_found() {
    try {
      parser.parse("abc.txt");
      failWhenExpectingException();
    } catch (ParsingException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to open file abc.txt");
    }
  }

  @Test(expected = AssertionError.class)
  public void should_throw_error_if_file_is_null() {
    File file = null;
    parser.parse(file);
  }

  @Test(expected = AssertionError.class)
  public void should_throw_error_if_file_does_not_exist() {
    parser.parse(new File("abc.xyz"));
  }

  @Test
  public void should_parse_file() throws Exception {
    File f = null;
    try {
      f = createMappingFile("a, A, NO_MASK");
      KeyStrokeMappingProvider mappingProvider = parser.parse(f);
      assertThatContainsDefaultMappings(mappingProvider);
      Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
      assertThat(mappings).contains(mapping('a', VK_A, NO_MASK));
    } finally {
      if (f != null) {
        f.delete();
      }
    }
  }

  private static File createMappingFile(String... mappings) throws IOException {
    File f = newTemporaryFile();
    Writer output = new BufferedWriter(new FileWriter(f));
    try {
      for (String mapping : mappings) {
        output.write(mapping);
        output.write(lineSeparator());
      }
    } finally {
      flushQuietly(output);
      closeQuietly(output);
    }
    return f;
  }

  private static void assertThatContainsDefaultMappings(KeyStrokeMappingProvider mappingProvider) {
    Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
    assertThat(mappings).contains(mapping('\b', VK_BACK_SPACE, NO_MASK), mapping('', VK_DELETE, NO_MASK),
        mapping('\n', VK_ENTER, NO_MASK), mapping('', VK_ESCAPE, NO_MASK), mapping('\t', VK_TAB, NO_MASK));
    if (isWindows()) {
      assertThat(mappings).contains(mapping('\r', VK_ENTER, NO_MASK));
    }
  }
}
