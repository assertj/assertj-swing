/*
 * Created on Jan 15, 2008
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
import java.io.Flushable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility methods related to {@link Flushable}.
 *
 * @author Yvonne Wang
 */
public class Flushables {
  private static Logger logger = Logger.getLogger(Flushables.class.getCanonicalName());

  private Flushables() {
  }

  /**
   * Flushes the given {@link Flushable}s, ignoring any thrown exceptions.
   *
   * @param flushables the {@code Flushable}s to flush.
   */
  public static void flushQuietly(@Nonnull Flushable... flushables) {
    for (Flushable f : flushables) {
      flush(f);
    }
  }

  private static void flush(@Nullable Flushable f) {
    if (f == null) {
      return;
    }
    try {
      f.flush();
    } catch (Throwable t) {
      logger.log(Level.WARNING, "Error occurred while flushing " + f, t);
    }
  }
}
