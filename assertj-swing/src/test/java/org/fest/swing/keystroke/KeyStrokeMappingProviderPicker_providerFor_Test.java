/*
 * Created on May 16, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.keystroke;

import static java.util.Locale.US;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.keystroke.KeyStrokeMappingProviderNames.generateNamesFrom;
import static org.fest.swing.util.OSFamily.WINDOWS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.fest.swing.util.OSFamily;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMappingProviderPicker#providerFor(OSFamily, Locale)}.
 *
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProviderPicker_providerFor_Test {
  private KeyStrokeMappingProviderFactory factory;
  private KeyStrokeMappingProviderPicker picker;

  @Before
  public void setUp() {
    factory = mock(KeyStrokeMappingProviderFactory.class);
    picker = new KeyStrokeMappingProviderPicker(factory);
  }

  @Test
  public void should_try_to_instantiate_provider_from_system_settings() {
    KeyStrokeMappingProviderNames names = generateNamesFrom(WINDOWS, US);
    String firstName = names.iterator().next();
    KeyStrokeMappingProvider provider = mock(KeyStrokeMappingProvider.class);
    when(factory.createProvider(firstName)).thenReturn(provider);
    assertThat(picker.providerFor(WINDOWS, US)).isSameAs(provider);
  }

  @Test
  public void should_return_default_provider_if_provider_from_system_settings_was_not_found() {
    for (String name : generateNamesFrom(WINDOWS, US)) {
      when(factory.createProvider(name)).thenReturn(null);
    }
    assertThat(picker.providerFor(WINDOWS, US)).isInstanceOf(KeyStrokeMappingProvider_en.class);
  }
}
