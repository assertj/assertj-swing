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
package org.assertj.swing.finder;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserFinder#findFileChooser()}.
 * 
 * @author Alex Ruiz
 */
public class JFileChooserFinder_findFileChooser_byType_Test extends JFileChooserFinder_TestCase {
  @Test
  public void should_Find_JFileChooser() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().using(robot);
    assertThat(found.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Find_JFileChooser_Before_Given_Timeout_Expires() {
    window.launchDelay(200);
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().withTimeout(500, MILLISECONDS).using(robot);
    assertThat(found.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_Find_JFileChooser_Before_Given_Timeout_In_Ms_Expires() {
    window.launchDelay(200);
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser().withTimeout(500).using(robot);
    assertThat(found.target()).isSameAs(window.fileChooser());
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_Fail_If_JFileChooser_Not_Found() {
    JFileChooserFinder.findFileChooser().using(robot);
  }
}
