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

import static org.assertj.swing.driver.JProgressBarSetIndetermintateTask.setIntedeterminate;

import javax.swing.JProgressBar;

import org.junit.Test;

/**
 * Tests for {@link JProgressBarDriver#requireDeterminate(JProgressBar)}.
 * 
 * @author Alex Ruiz
 */
public class JProgressBarDriver_requireDeterminate_Test extends JProgressBarDriver_TestCase {
  @Test
  public void should_Pass_If_JProgressBar_Is_Determinate() {
    setIntedeterminate(progressBar, false);
    robot.waitForIdle();
    driver.requireDeterminate(progressBar);
  }

  @Test
  public void should_Fail_If_JProgressBar_Is_Not_Determinate() {
    makeIndeterminate();
    thrown.expectAssertionError("property:'indeterminate'");
    thrown.expectMessageToContain("expected:<[fals]e> but was:<[tru]e>");
    driver.requireDeterminate(progressBar);
  }
}
