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

import static org.fest.assertions.Assertions.assertThat;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlJUnitResultFormatter#setSystemOutput(String)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter_setSystemOutput_Test extends XmlJUnitResultFormatter_TestCase {

  @Test
  public void should_add_system_output() {
    formatter.startTestSuite(new JUnitTest("test"));
    formatter.setSystemOutput(CONSOLE_OUTPUT);
    XmlNode systemOutNode = root().child(1);
    assertThat(systemOutNode.name()).isEqualTo("system-out");
    assertThat(systemOutNode.text()).isEqualTo(CONSOLE_OUTPUT);
  }
}
