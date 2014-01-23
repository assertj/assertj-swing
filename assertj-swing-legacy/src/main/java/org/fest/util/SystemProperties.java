/*
 * Created on Feb 10, 2008
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

import static org.fest.util.Preconditions.checkNotNull;

/**
 * System properties.
 *
 * @author Yvonne Wang
 */
public final class SystemProperties {
  private static final String LINE_SEPARATOR = getlineSeparator();

  private SystemProperties() {
  }

  private static @Nonnull String getlineSeparator() {
    try {
      return checkNotNull(System.getProperty("line.separator"));
    } catch (Exception e) {
      return "\n";
    }
  }

  public static @Nonnull String lineSeparator() {
    return LINE_SEPARATOR;
  }
}
