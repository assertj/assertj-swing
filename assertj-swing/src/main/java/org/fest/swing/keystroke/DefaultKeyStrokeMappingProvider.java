/*
 * Created on Mar 26, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.keystroke;

import static org.fest.swing.keystroke.KeyStrokeMappings.defaultMappings;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Default mapping of characters and {@code javax.swing.KeyStroke}s.
 *
 * @author Alex Ruiz
 */
public class DefaultKeyStrokeMappingProvider implements KeyStrokeMappingProvider {
  /**
   * Returns the default mapping of characters and {@code KeyStroke}s. This provider will only return the mappings for
   * following keys:
   * <ul>
   * <li>Backspace</li>
   * <li>Delete</li>
   * <li>Enter</li>
   * <li>Escape</li>
   * <li>Tab</li>
   * </ul>
   *
   * @return the default mapping of characters and {@code KeyStroke}s
   */
  @Override
  public @Nonnull Collection<KeyStrokeMapping> keyStrokeMappings() {
    return LazyLoadingSingleton.instance;
  }

  private static class LazyLoadingSingleton {
    static List<KeyStrokeMapping> instance = newArrayList(defaultMappings());
  }
}
