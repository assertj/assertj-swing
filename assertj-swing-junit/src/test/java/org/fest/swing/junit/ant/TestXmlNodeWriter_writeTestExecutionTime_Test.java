/*
 * Created on Apr 7, 2009
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

import static java.lang.Double.parseDouble;
import static java.lang.System.currentTimeMillis;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_TIME;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.junit.xml.XmlDocument;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link TestXmlNodeWriter#writeError(XmlNode, Throwable)}</code>.
 *
 * @author Alex Ruiz
 */
public class TestXmlNodeWriter_writeTestExecutionTime_Test extends TestXmlNodeWriter_TestCase {

  @Test
  public void should_add_test_execution_time_as_attribute() {
    XmlNode root = new XmlDocument().newRoot("root");
    assertThat(writer.writeTestExecutionTime(root, currentTimeMillis() - 3000)).isSameAs(writer);
    double time = parseDouble(root.valueOfAttribute(ATTR_TIME));
    assertThat(time).isGreaterThan(0d);
  }
}
