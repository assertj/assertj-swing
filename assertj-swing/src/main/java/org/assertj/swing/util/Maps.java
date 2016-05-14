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
package org.assertj.swing.util;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Utility methods related to maps.
 *
 *
 * @author Christian Rösch
 */
public class Maps {
  /**
   * Returns a <em>mutable</em> {@code HashMap} that is empty.
   *
   * @return the created {@code HashMap}.
   */
  public static <K, V> Map<K, V> newHashMap() {
    return new HashMap<>();
  }

  /**
   * Returns a <em>mutable</em> {@code ConcurrentMap} that is empty.
   *
   * @return the created {@code ConcurrentMap}.
   */
  public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<>();
  }

  /**
   * Returns a <em>mutable</em> {@code WeakHashMap} that is empty.
   *
   * @return the created {@code WeakHashMap}.
   */
  public static <K, V> WeakHashMap<K, V> newWeakHashMap() {
    return new WeakHashMap<>();
  }

  /**
   * Indicates whether the given {@code Map} is {@code null} or empty.
   *
   * @param map the map to check.
   * @return {@code true} if the given {@code Map} is {@code null} or empty, otherwise {@code false}.
   */
  public static boolean isNullOrEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }
}
