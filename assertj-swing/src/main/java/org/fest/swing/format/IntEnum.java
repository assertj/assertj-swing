/*
 * Created on Dec 23, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.format;

import static java.lang.String.valueOf;
import static org.fest.util.Maps.newHashMap;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Workaround to obtain a {@code String} representation of an {@code int} enumeration.
 * 
 * @author Alex Ruiz
 */
final class IntEnum {
  private final Map<Integer, String> map = newHashMap();

  @Nullable String get(int key) {
    if (map.containsKey(key)) {
      return map.get(key);
    }
    return valueOf(key);
  }

  @Nonnull IntEnum put(int key, @Nullable String value) {
    map.put(key, value);
    return this;
  }
}
