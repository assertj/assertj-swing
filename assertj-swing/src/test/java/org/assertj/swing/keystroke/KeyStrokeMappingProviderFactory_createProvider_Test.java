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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link KeyStrokeMappingProviderFactory#createProvider(String)}.
 * 
 * @author Alex Ruiz
 */
public class KeyStrokeMappingProviderFactory_createProvider_Test {
  private static KeyStrokeMappingProviderFactory factory;

  @BeforeClass
  public static void setUpOnce() {
    factory = new KeyStrokeMappingProviderFactory();
  }

  @Test
  public void should_Create_Provider_From_Given_Class_Name() {
    Class<?> providerType = KeyStrokeMappingProvider_en.class;
    String className = providerType.getName();
    KeyStrokeMappingProvider provider = factory.createProvider(className);
    assertThat(provider).isInstanceOf(providerType);
  }

  @Test
  public void should_Return_Null_If_Class_Is_Not_KeyStrokeMappingProvider() {
    String className = String.class.getName();
    KeyStrokeMappingProvider provider = factory.createProvider(className);
    assertThat(provider).isNull();
  }

  @Test
  public void should_Return_Null_If_Provider_Cannot_Be_Instantiated() {
    String className = MyKeyStrokeMappingProvider.class.getName();
    KeyStrokeMappingProvider provider = factory.createProvider(className);
    assertThat(provider).isNull();
  }

  static class MyKeyStrokeMappingProvider implements KeyStrokeMappingProvider {
    private MyKeyStrokeMappingProvider() {
    }

    @Override
    public Collection<KeyStrokeMapping> keyStrokeMappings() {
      return null;
    }
  }
}
