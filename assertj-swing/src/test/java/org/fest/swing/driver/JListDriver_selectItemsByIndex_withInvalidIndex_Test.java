/*
 * Created on Feb 24, 2008
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

import static java.lang.String.valueOf;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.fest.util.Lists.newArrayList;
import static org.fest.util.Strings.concat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JListDriver#selectItems(javax.swing.JList, int[])}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JListDriver_selectItemsByIndex_withInvalidIndex_Test extends JListDriver_TestCase {
  private final int index;

  @Parameters
  public static Collection<Object[]> indices() {
    return newArrayList(indicesOutOfBounds());
  }

  public JListDriver_selectItemsByIndex_withInvalidIndex_Test(int index) {
    this.index = index;
  }

  @Test
  public void should_throw_error_if_index_is_out_of_bounds() {
    showWindow();
    try {
      driver.selectItems(list, new int[] { index });
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException e) {
      assertThat(e.getMessage()).isEqualTo(
          concat("Item index (", valueOf(index), ") should be between [0] and [2] (inclusive)"));
    }
  }
}
