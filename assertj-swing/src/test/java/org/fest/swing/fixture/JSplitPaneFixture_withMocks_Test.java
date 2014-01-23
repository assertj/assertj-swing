/*
 * Created on Nov 18, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.swing.JSplitPane;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JSplitPaneDriver;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JSplitPaneFixture}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSplitPaneFixture_withMocks_Test {
  private JSplitPaneFixture fixture;

  @Before
  public void setUp() {
    fixture = new JSplitPaneFixture(mock(Robot.class), mock(JSplitPane.class));
    fixture.replaceDriverWith(mock(JSplitPaneDriver.class));
  }

  @Test
  public void should_call_moveDividerTo_in_driver_and_return_self() {
    assertThat(fixture.moveDividerTo(6)).isSameAs(fixture);
    verify(fixture.driver()).moveDividerTo(fixture.target(), 6);
  }
}
