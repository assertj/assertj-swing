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
package org.assertj.swing.fixture;

import static org.assertj.swing.core.TestRobots.singletonRobotMock;
import static org.mockito.Mockito.mock;

import javax.swing.table.JTableHeader;

import org.junit.Test;

/**
 * Tests for {@link JTableHeaderFixture#JTableHeaderFixture(org.assertj.swing.core.Robot, JTableHeader)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableHeaderFixture_constructor_Test {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Robot_Is_Null() {
    new JTableHeaderFixture(null, mock(JTableHeader.class));
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Target_Is_Null() {
    new JTableHeaderFixture(singletonRobotMock(), null);
  }
}
