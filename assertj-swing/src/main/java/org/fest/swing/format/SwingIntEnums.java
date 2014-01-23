/*
 * Created on Dec 24, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.format;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

/**
 * Common Swing {@code int} enumerations.
 *
 * @author Alex Ruiz
 */
final class SwingIntEnums {
  static final IntEnum SELECTION_MODES = new IntEnum();
  
  static {
    SELECTION_MODES.put(SINGLE_SELECTION, "SINGLE_SELECTION")
                   .put(SINGLE_INTERVAL_SELECTION, "SINGLE_INTERVAL_SELECTION")
                   .put(MULTIPLE_INTERVAL_SELECTION, "MULTIPLE_INTERVAL_SELECTION");
  }

  private SwingIntEnums() {}
}
