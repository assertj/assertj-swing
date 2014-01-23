/*
 * Created on Oct 29, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.finder;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneFinder#findOptionPane()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JOptionPaneFinder_findOptionPane_byType_Test extends JOptionPaneFinder_TestCase {
  @Test
  public void should_find_JOptionPane() {
    clickMessageButton();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().using(robot);
    assertThat(found.target()).isNotNull();
  }

  @Test
  public void should_find_JOptionPane_before_given_timeout_expires() {
    window.launchDelay(200);
    clickMessageButton();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().withTimeout(500, MILLISECONDS).using(robot);
    assertThat(found.target()).isNotNull();
  }

  @Test
  public void should_find_JOptionPane_before_given_timeout_in_ms_expires() {
    window.launchDelay(200);
    clickMessageButton();
    JOptionPaneFixture found = JOptionPaneFinder.findOptionPane().withTimeout(500).using(robot);
    assertThat(found.target()).isNotNull();
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_fail_if_JOptionPane_not_found() {
    JOptionPaneFinder.findOptionPane().using(robot);
  }
}
