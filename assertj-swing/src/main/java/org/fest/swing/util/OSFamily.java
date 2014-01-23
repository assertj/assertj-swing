/*
 * Created on Mar 25, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.util;

import javax.annotation.Nonnull;

/**
 * Operating System families.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
public enum OSFamily {
  WINDOWS("win"), MAC("mac"), LINUX("linux"), UNIX("unix");

  private final String key;

  private OSFamily(@Nonnull String key) {
    this.key = key;
  }

  /**
   * @return the key (or abbreviation) of this OS family.
   */
  public @Nonnull String key() {
    return key;
  }
}
