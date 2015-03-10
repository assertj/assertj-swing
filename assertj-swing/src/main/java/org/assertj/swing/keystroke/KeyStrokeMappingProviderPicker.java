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

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.keystroke.KeyStrokeMappingProviderNames.generateNamesFrom;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.assertj.swing.util.OSFamily;
import org.assertj.core.util.VisibleForTesting;

/**
 * Chooses a {@link KeyStrokeMappingProvider} based on OS family and locale.
 * 
 * @author Alex Ruiz
 */
class KeyStrokeMappingProviderPicker {
  private final KeyStrokeMappingProviderFactory factory;

  KeyStrokeMappingProviderPicker() {
    this(new KeyStrokeMappingProviderFactory());
  }

  @VisibleForTesting
  KeyStrokeMappingProviderPicker(@Nonnull KeyStrokeMappingProviderFactory factory) {
    this.factory = factory;
  }

  KeyStrokeMappingProvider providerFor(@Nonnull OSFamily osFamily, @Nonnull Locale locale) {
    for (String name : generateNamesFrom(osFamily, locale)) {
      String typeName = checkNotNull(name);
      KeyStrokeMappingProvider provider = factory.createProvider(typeName);
      if (provider != null) {
        return provider;
      }
    }
    return new KeyStrokeMappingProvider_en();
  }
}
