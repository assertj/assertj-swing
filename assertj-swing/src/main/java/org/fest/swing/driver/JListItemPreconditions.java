/*
 * Created on Oct 31, 2008
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

import javax.annotation.Nonnull;
import javax.swing.JList;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Verifies correct state of items in a {@code JList}.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 */
final class JListItemPreconditions {
  @RunsInCurrentThread
  static void checkIndexInBounds(@Nonnull JList list, int index) {
    checkIndexInBounds(index, list.getModel().getSize());
  }

  @RunsInCurrentThread
  static void checkIndicesInBounds(@Nonnull JList list, @Nonnull int... indices) {
    int itemCount = list.getModel().getSize();
    for (int index : indices) {
      checkIndexInBounds(index, itemCount);
    }
  }

  private static void checkIndexInBounds(int index, int itemCount) {
    if (index >= 0 && index < itemCount) {
      return;
    }
    String format = "Item index (%d) should be between [0] and [%d] (inclusive)";
    String msg = String.format(format, index, itemCount - 1);
    throw new IndexOutOfBoundsException(msg);
  }

  private JListItemPreconditions() {}
}
