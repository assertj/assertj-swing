/*
 * Created on Jan 25, 2008
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
package org.fest.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.fest.util.ToString.toStringOf;

/**
 * Utility methods related to {@code java.util.Map}s.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Maps {
  private Maps() {
  }

  /**
   * Returns a <em>mutable</em> {@code HashMap}.
   *
   * @return the created {@code Map}.
   */
  public static @Nonnull <K, V> Map<K, V> newHashMap() {
    return new HashMap<K, V>();
  }

  /**
   * Returns a <em>mutable</em> {@code ConcurrentMap}.
   *
   * @return the created {@code Map}.
   */
  public static @Nonnull <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
    return new ConcurrentHashMap<K, V>();
  }

  /**
   * Returns a <em>mutable</em> {@code WeakHashMap}.
   *
   * @return the created {@code Map}.
   */
  public static @Nonnull <K, V> WeakHashMap<K, V> newWeakHashMap() {
    return new WeakHashMap<K, V>();
  }

  /**
   * Indicates whether the given {@code Map} is {@code null} or empty.
   *
   * @param map the map to check.
   * @return {@code true} if the given {@code Map} is {@code null} or empty, otherwise {@code false}.
   */
  public static boolean isNullOrEmpty(@Nullable Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  /**
   * Returns the {@code String} representation of the given map, or {@code null} if the given map is {@code null}.
   *
   * @param map the map to format.
   * @return the {@code String} representation of the given map.
   */
  public static @Nullable String format(@Nullable Map<?, ?> map) {
    if (map == null) {
      return null;
    }
    Iterator<?> i = map.entrySet().iterator();
    if (!i.hasNext()) {
      return "{}";
    }
    StringBuilder buffer = new StringBuilder();
    buffer.append("{");
    for (; ; ) {
      Entry<?, ?> e = (Entry<?, ?>) i.next();
      buffer.append(format(map, e.getKey()));
      buffer.append('=');
      buffer.append(format(map, e.getValue()));
      if (!i.hasNext()) {
        return buffer.append("}").toString();
      }
      buffer.append(", ");
    }
  }

  private static @Nullable Object format(@Nonnull Map<?, ?> map, @Nullable Object o) {
    return o == map ? "(this Map)" : toStringOf(o);
  }
}
