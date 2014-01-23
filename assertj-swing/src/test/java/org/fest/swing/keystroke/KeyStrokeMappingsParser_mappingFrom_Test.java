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

import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_COMMA;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.keystroke.KeyStrokeMapping.mapping;
import static org.fest.swing.keystroke.KeyStrokeMappingProvider.NO_MASK;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link KeyStrokeMappingsParser#mappingFrom(String)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class KeyStrokeMappingsParser_mappingFrom_Test {
  private final String lineToParse;
  private final KeyStrokeMapping expectedMapping;

  @Parameters
  public static Collection<Object[]> linesToParse() {
    return newArrayList(new Object[][] {
        { "a, A, NO_MASK", mapping('a', VK_A, NO_MASK) },
        { "A, A, SHIFT_MASK", mapping('A', VK_A, SHIFT_MASK) },
        { "COMMA, COMMA, NO_MASK", mappingForComma() },
        { "COMMA,COMMA,NO_MASK", mappingForComma() },
        { "  COMMA,  COMMA,  NO_MASK", mappingForComma() },
      });
  }

  private static KeyStrokeMapping mappingForComma() {
    return mapping(',', VK_COMMA, NO_MASK);
  }

  public KeyStrokeMappingsParser_mappingFrom_Test(String lineToParse, KeyStrokeMapping expectedMapping) {
    this.lineToParse = lineToParse;
    this.expectedMapping = expectedMapping;
  }

  private KeyStrokeMappingsParser parser;

  @Before
  public void setUp() {
    parser = new KeyStrokeMappingsParser();
  }

  @Test
  public void should_create_mapping_from_line() {
    KeyStrokeMapping parsedMapping = parser.mappingFrom(lineToParse);
    assertThat(parsedMapping).isEqualTo(expectedMapping);
  }
}
