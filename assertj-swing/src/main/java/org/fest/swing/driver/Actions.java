/*
 * Created on Jan 27, 2008
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
package org.fest.swing.driver;

import static java.util.Collections.sort;
import static javax.swing.Action.NAME;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.Action;
import javax.swing.ActionMap;

/**
 * Utility methods related to Swing {@code Action}s.
 *
 * @author Alex Ruiz
 */
final class Actions {
  static @Nonnull Object findActionKey(@Nonnull String name, @Nonnull ActionMap actionMap) {
    Action action = actionMap.get(name);
    if (action != null) {
      return name;
    }
    Object[] allKeys = actionMap.allKeys();
    if (allKeys != null) {
      for (Object key : allKeys) {
        action = actionMap.get(key);
        if (name.equals(action.getValue(NAME))) {
          return checkNotNull(key);
        }
      }
    }
    String message = String.format("The action '%s' is not available", name);
    if (allKeys != null && allKeys.length > 0) {
      message = concat(message, ", available actions:", formatAllActionKeys(allKeys));
    }
    throw actionFailure(message);
  }

  private static @Nonnull List<String> formatAllActionKeys(@Nonnull Object[] keys) {
    List<String> formattedKeys = newArrayList();
    for (Object key : keys) {
      String keyAsString = keyAsString(key);
      if (keyAsString != null) {
        formattedKeys.add(keyAsString);
      }
    }
    sort(formattedKeys);
    return formattedKeys;
  }

  private static @Nullable String keyAsString(@Nullable Object key) {
    if (key == null) {
      return null;
    }
    if (key instanceof String) {
      return (String) quote(key);
    }
    return String.format("%s(%s)", key.toString(), key.getClass().getName());
  }

  private Actions() {}
}
