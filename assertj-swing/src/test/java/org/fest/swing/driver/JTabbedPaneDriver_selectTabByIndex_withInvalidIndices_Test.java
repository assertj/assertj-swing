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
 * Tests for {@link JTabbedPaneDriver#selectTab(javax.swing.JTabbedPane, int)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunWith(Parameterized.class)
public class JTabbedPaneDriver_selectTabByIndex_withInvalidIndices_Test extends JTabbedPaneDriver_TestCase {
  private final int index;

  @Parameters
  public static Collection<Object[]> indices() {
    return newArrayList(new Object[][] {
        { -1 }, { 2 }
      });
  }

  public JTabbedPaneDriver_selectTabByIndex_withInvalidIndices_Test(int index) {
    this.index = index;
  }

  @Test
  public void should_throw_error_if_index_is_out_of_bounds() {
    try {
      driver.selectTab(tabbedPane, index);
      failWhenExpectingException();
    } catch (IndexOutOfBoundsException expected) {
      assertThat(expected.getMessage()).isEqualTo(
          concat("Index <", index, "> is not within the JTabbedPane bounds of <0> and <1> (inclusive)"));
    }
  }
}
