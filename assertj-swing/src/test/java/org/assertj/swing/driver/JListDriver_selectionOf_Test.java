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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#selectionOf(javax.swing.JList)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_selectionOf_Test extends JListDriver_TestCase {
  @Test
  public void should_Return_Selection() {
    select(0, 2);
    String[] selection = driver.selectionOf(list);
    assertThat(selection).containsOnly("one", "three");
    assertThatCellReaderWasCalled();
  }

  @Test
  public void should_Empty_Array_If_JList_Does_Not_Have_Selection() {
    assertThat(driver.selectionOf(list)).isEmpty();
  }
}
