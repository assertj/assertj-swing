/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.core.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTreeDriver#toggleRow(javax.swing.JTree, int)}.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JTreeDriver_toggleRow_withInvalidIndex_Test extends JTreeDriver_TestCase {
  private final int invalidRow;

  @Parameters
  public static Collection<Object[]> invalidRows() {
    return newArrayList(outOfBoundRowIndices());
  }

  public JTreeDriver_toggleRow_withInvalidIndex_Test(int invalidRow) {
    this.invalidRow = invalidRow;
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void should_Throw_Error_If_Given_Row_Index_Is_Out_Of_Bounds() {
    showWindow();
    driver.toggleRow(tree, invalidRow);
  }
}
