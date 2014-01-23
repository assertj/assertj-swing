/*
 * Created on Jul 16, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTreeDriver#showPopupMenu(javax.swing.JTree, int)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JTreeDriver_showPopupMenuAtRow_withInvalidIndex_Test extends JTreeDriver_TestCase {
  private final int invalidRow;

  @Parameters
  public static Collection<Object[]> invalidRows() {
    return newArrayList(outOfBoundRowIndices());
  }

  public JTreeDriver_showPopupMenuAtRow_withInvalidIndex_Test(int invalidRow) {
    this.invalidRow = invalidRow;
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_throw_error_if_given_row_index_is_out_of_bounds() {
    showWindow();
    driver.showPopupMenu(tree, invalidRow);
  }
}
