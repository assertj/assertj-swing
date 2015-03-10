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

import static java.lang.String.valueOf;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.util.Range.from;
import static org.assertj.swing.util.Range.to;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for
 * {@link JListDriver#selectItems(javax.swing.JList, org.assertj.swing.util.Range.From, org.assertj.swing.util.Range.To)}
 * .
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JListDriver_selectItemsByFluentRange_withInvalidIndex_Test extends JListDriver_TestCase {
  private final int index;

  @Parameters
  public static Collection<Object[]> indices() {
    return newArrayList(indicesOutOfBounds());
  }

  public JListDriver_selectItemsByFluentRange_withInvalidIndex_Test(int index) {
    this.index = index;
  }

  @Test
  public void should_Throw_Error_If_Starting_Index_Is_Out_Of_Bounds() {
    showWindow();
    thrown.expectIndexOutOfBoundsException(concat("Item index (", valueOf(index),
        ") should be between [0] and [2] (inclusive)"));
    driver.selectItems(list, from(index), to(1));
  }

  @Test
  public void should_Throw_Error_If_Ending_Index_Is_Out_Of_Bounds() {
    showWindow();
    thrown.expectIndexOutOfBoundsException(concat("Item index (", valueOf(index),
        ") should be between [0] and [2] (inclusive)"));
    driver.selectItems(list, from(0), to(index));
  }
}
