/*
 * Created on Mar 28, 2010
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

import static java.util.Locale.US;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.swing.util.OSFamily.WINDOWS;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMappingProviderNames#iterator()}.
 * 
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProviderNames_iterator_Test {
  private static KeyStrokeMappingProviderNames names;
  private Iterator<String> iterator;

  @BeforeClass
  public static void setUpOnce() {
    names = KeyStrokeMappingProviderNames.generateNamesFrom(WINDOWS, US);
  }

  @Before
  public void setUp() {
    iterator = names.iterator();
  }

  @Test
  public void should_return_iterate_through_all_names() {
    assertThat(iterator).containsOnly("org.fest.swing.keystroke.KeyStrokeMappingProvider_win_en_US",
        "org.fest.swing.keystroke.KeyStrokeMappingProvider_win_en",
        "org.fest.swing.keystroke.KeyStrokeMappingProvider_en");
  }

  @Test
  public void should_throw_error_if_iterator_does_not_have_more_elements() {
    iterator.next(); // full name
    iterator.next(); // without country
    iterator.next(); // language only
    try {
      iterator.next();
      failWhenExpectingException();
    } catch (NoSuchElementException e) {
      assertThat(e.getMessage()).isEqualTo("There are no more names to generate");
    }
  }

  @Test
  public void should_throw_error_if_remove_is_called_on_iterator() {
    try {
      iterator.remove();
      failWhenExpectingException();
    } catch (UnsupportedOperationException e) {
      assertThat(e.getMessage()).isEqualTo("This iterator does not support 'remove'");
    }
  }
}
