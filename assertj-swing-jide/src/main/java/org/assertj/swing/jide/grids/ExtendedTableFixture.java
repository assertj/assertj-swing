/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.grids;

/**
 * An interface for defining advanced table fixture methods that will be useful for
 * driving table like components in FEST tests.
 * @author Peter Murray
 */
public interface ExtendedTableFixture {

  int rows();

  int columns();

  int selectedRow();

  int[] selectedRows();

  ExtendedTableFixture requireSelection(int... rows);
}
