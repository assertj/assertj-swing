/*
 * Created on Jul 16, 2009
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
package org.assertj.swing.driver;

import static org.assertj.swing.driver.JTreeSetEditableTask.setEditable;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#requireEditable(javax.swing.JTree)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_requireEditable_Test extends JTreeDriver_TestCase {
  @Test
  public void should_pass_if_JTree_is_editable() {
    setJTreeEditable(true);
    robot.waitForIdle();
    driver.requireEditable(tree);
  }

  @Test
  public void should_fail_if_JTree_is_not_editable() {
    setJTreeEditable(false);
    thrown.expectAssertionError("property:'editable'");
    thrown.expectMessageToContain("expected:<[tru]e> but was:<[fals]e>");
    driver.requireEditable(tree);
  }

  @RunsInEDT
  private void setJTreeEditable(boolean editable) {
    setEditable(tree, editable);
    robot.waitForIdle();
  }
}
