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

import static org.fest.swing.keystroke.KeyStrokeMappingProviderNames.generateNamesFrom;
import static org.fest.util.Preconditions.checkNotNull;

import java.util.Locale;

import javax.annotation.Nonnull;

import org.fest.swing.util.OSFamily;
import org.fest.util.VisibleForTesting;

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
