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
package org.assertj.swing.keystroke;

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_TAB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Closeables.closeQuietly;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.assertj.core.util.Flushables.flush;
import static org.assertj.core.util.SystemProperties.LINE_SEPARATOR;
import static org.assertj.swing.keystroke.KeyStrokeMapping.mapping;
import static org.assertj.swing.keystroke.KeyStrokeMappingProvider.NO_MASK;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.util.Platform.isWindows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import org.assertj.swing.exception.ParsingException;
import org.assertj.swing.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMappingsParser#parse(String)}.
 * 
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProvider_parse_Test {
  private KeyStrokeMappingsParser parser;

  @Rule
  public ExpectedException thrown = none();

  @Before
  public void setUp() {
    parser = new KeyStrokeMappingsParser();
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_File_Name_Is_Null() {
    String file = null;
    parser.parse(file);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_File_Name_Is_Empty() {
    parser.parse("");
  }

  @Test
  public void should_Parse_File_In_Classpath() {
    KeyStrokeMappingProvider mappingProvider = parser.parse("keyboard-mapping.txt");
    assertThatContainsDefaultMappings(mappingProvider);
    Collection<KeyStrokeMapping> mappings = mappingProvider.keyStrokeMappings();
    assertThat(mappings).contains(mapping('a', VK_A, NO_MASK), mapping('A', VK_A, SHIFT_MASK),
        mapping(',', VK_COMMA, NO_MASK));
  }

  @Test
  public void should_Throw_Error_If_File_Not_Found() {
    thrown.expect(ParsingException.class, "Unable to open file abc.txt");
    parser.parse("abc.txt");
  }

  @Test(expected = AssertionError.class)
  public void should_Throw_Error_If_File_Is_Null() {
    File file = null;
    parser.parse(file);
  }

  @Test(expected = AssertionError.class)
  public void should_Throw_Error_If_File_Does_Not_Exist() {
    parser.parse(new File("abc.xyz"));
  }

  @Test
  public void should_Parse_File() throws Exception {
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
        output.write(LINE_SEPARATOR);
      }
    } finally {
      flush(output);
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
