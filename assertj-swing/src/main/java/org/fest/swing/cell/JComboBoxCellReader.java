/*
 * Created on Apr 13, 2008
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
import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Reads the content of a cell in {@code JComboBox}, as it appears to the user.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 */
@RunsInCurrentThread
public interface JComboBoxCellReader {
  /**
   * <p>
   * Returns the internal value of a cell in a {@code JComboBox} as expected in a test.
   * </p>
   * 
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT.) Client code must call this method from the EDT.
   * </p>
   * 
   * @param comboBox the given {@code JComboBox}.
   * @param index the index of the cell.
   * @return the internal value of a cell in a {@code JComboBox} as expected in a test.
   */
  @Nullable String valueAt(@Nonnull JComboBox comboBox, int index);
}
