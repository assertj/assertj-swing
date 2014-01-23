/*
 * Created on Oct 21, 2008
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

import static org.fest.swing.util.Strings.isDefaultToString;

import javax.annotation.Nullable;

/**
 * Converts a value from a model into a {@code String}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class ModelValueToString {
  /**
   * Returns the {@code toString} value from the given object. If the given object does not implement {@code toString},
   * this method will return {@code null}.
   * 
   * @param o the given object.
   * @return the {@code toString} value from the given object, or {@code null} if the given object does not implement
   *         {@code toString}.
   */
  static @Nullable String asText(@Nullable Object o) {
    if (o == null) {
      return null;
    }
    String text = o.toString();
    if (!isDefaultToString(text)) {
      return text;
    }
    return null;
  }

  private ModelValueToString() {}
}
