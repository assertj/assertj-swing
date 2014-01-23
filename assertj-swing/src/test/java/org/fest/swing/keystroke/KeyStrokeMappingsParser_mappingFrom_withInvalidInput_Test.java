/*
 * Created on Mar 13, 2010
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

import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.exception.ParsingException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMappingsParser#mappingFrom(String)}.
 * 
 * @author Alex Ruiz
 */
public class KeyStrokeMappingsParser_mappingFrom_withInvalidInput_Test {
  private KeyStrokeMappingsParser parser;

  @Before
  public void setUp() {
    parser = new KeyStrokeMappingsParser();
  }

  @Test
  public void should_throw_error_if_line_does_not_conform_with_pattern() {
    try {
      parser.mappingFrom("Hello World!");
    } catch (ParsingException e) {
      assertThat(e.getMessage()).isEqualTo(
          "Line 'Hello World!' does not conform with pattern '{char}, {keycode}, {modifiers}'");
    }
  }

  @Test
  public void should_throw_error_if_char_cannot_be_obtained() {
    try {
      parser.mappingFrom(", COMMA, NO_MASK");
    } catch (ParsingException e) {
      assertThat(e.getMessage()).isEqualTo("The text '' should have a single character");
    }
  }

  @Test
  public void should_throw_error_if_key_code_cannot_be_obtained() {
    try {
      parser.mappingFrom("A, SOME_KEY, NO_MASK");
    } catch (ParsingException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to retrieve key code from text 'SOME_KEY'");
    }
  }

  @Test
  public void should_throw_error_if_modifiers_cannot_be_obtained() {
    try {
      parser.mappingFrom("A, A, SHIFT");
    } catch (ParsingException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to retrieve modifiers from text 'SHIFT'");
    }
  }
}
