/*
 * Created on Aug 24, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import org.junit.Test;

/**
 * Tests for <code>{@link XmlJUnitResultFormatter#endTest(junit.framework.Test)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter_endTest_Test extends XmlJUnitResultFormatter_TestCase {

  @Test
  public void should_write_execution_time_for_successful_and_notS_started_test() {
    startSuite();
    junit.framework.Test test = mockTest();
    formatter.endTest(test);
    assertThatTestWasStarted(test);
    assertThatTestCaseNodeWasAddedTo(root());
  }
}
