/*
 * Created on Feb 25, 2008
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

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.data.TableCell.row;
import static org.fest.swing.test.data.ZeroAndNegativeProvider.zeroAndNegative;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for
 * {@link JTableDriver#click(javax.swing.JTable, org.fest.swing.data.TableCell, org.fest.swing.core.MouseButton, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTableDriver_clickCell_withInvalidInput_Test extends JTableDriver_TestCase {
  private final int index;

  @Parameters
  public static Collection<Object[]> index() {
    return newArrayList(zeroAndNegative());
  }

  public JTableDriver_clickCell_withInvalidInput_Test(int index) {
    this.index = index;
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowErrorIfNumberOfTimesToClickCellIsZeroOrNegative() {
    driver.click(table, row(0).column(1), LEFT_BUTTON, index);
  }
}
