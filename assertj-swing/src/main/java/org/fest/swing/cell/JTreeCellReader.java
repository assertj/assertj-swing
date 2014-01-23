/*
 * Created on Apr 14, 2008
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
package org.fest.swing.cell;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Reads the content, as shown to the user, of a cell in a {@code JTable}.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@RunsInCurrentThread
public interface JTreeCellReader {
  /**
   * <p>
   * Returns the internal value of a cell in a {@code JTree} as expected in a test.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param tree the given {@code JTree}.
   * @param modelValue the value of a cell, retrieved from the model.
   * @return the internal value of a cell in a {@code JTree} as expected in a test.
   */
  @Nullable String valueAt(@Nonnull JTree tree, @Nullable Object modelValue);
}
