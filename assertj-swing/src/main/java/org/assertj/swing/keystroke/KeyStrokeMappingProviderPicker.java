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

import org.assertj.core.util.Strings;
import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.util.OSFamily;


/**
 * Chooses a {@link KeyStrokeMappingProvider} based on OS family and locale.
 * @author Alex Ruiz
 */
class KeyStrokeMappingProviderPicker {
  
  /** JVM parameter to use with -D in order to override default strategy for choosing the provider */
  public static final String KEY_STROKE_MAPPING_PROVIDER = "KeyStrokeMappingProvider";
  
  private final KeyStrokeMappingProviderFactory factory;

  KeyStrokeMappingProviderPicker() {
    this(new KeyStrokeMappingProviderFactory());
  }

  @VisibleForTesting
  KeyStrokeMappingProviderPicker(@Nonnull KeyStrokeMappingProviderFactory factory) {
    this.factory = factory;
  }

  KeyStrokeMappingProvider providerFor(@Nonnull OSFamily osFamily, @Nonnull Locale locale) {
    
    String forcedProvider = System.getProperty(KEY_STROKE_MAPPING_PROVIDER);
    if (!Strings.isNullOrEmpty(forcedProvider)) {
      KeyStrokeMappingProvider provider = factory.createProvider(forcedProvider);
      if (provider != null) {
    	return provider;
      } else {
        // Unable to load forced provider => will use default strategy
      }
    }
    
    // default strategy to auto select provider
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
