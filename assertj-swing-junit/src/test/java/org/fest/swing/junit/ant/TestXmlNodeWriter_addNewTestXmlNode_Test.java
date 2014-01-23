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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;
import static org.easymock.EasyMock.expect;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.swing.junit.xml.XmlAttributes.attributes;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlAttributes;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link TestXmlNodeWriter#addNewTestXmlNode(org.fest.swing.junit.xml.XmlNode, junit.framework.Test)}</code>
 *
 * @author Alex Ruiz
 */
public class TestXmlNodeWriter_addNewTestXmlNode_Test extends TestXmlNodeWriter_TestCase {

  @Test
  public void should_add_test_node_as_child() {
    final TestStub test = new TestStub("hello");
    final XmlNode newNode = mockXmlNode();
    new EasyMockTemplate(targetNode) {
      @Override protected void expectations() {
        XmlAttributes attributes = attributes(name(ATTR_NAME).value("hello"),
                                              name(ATTR_CLASSNAME).value(TestStub.class.getName()));
        expect(targetNode.addNewNode(TESTCASE, attributes)).andReturn(newNode);
      }

      @Override protected void codeToTest() {
        assertThat(writer.addNewTestXmlNode(targetNode, test)).isSameAs(newNode);
      }
    }.run();
  }

  @Test
  public void should_add_test_node_as_child_and_set_test_name_to_unknown_if_test_name_is_null() {
    final TestStub test = new TestStub(null);
    final XmlNode newNode = mockXmlNode();
    new EasyMockTemplate(targetNode) {
      @Override protected void expectations() {
        XmlAttributes attributes = attributes(name(ATTR_NAME).value("unknown"),
                                              name(ATTR_CLASSNAME).value(TestStub.class.getName()));
        expect(targetNode.addNewNode(TESTCASE, attributes)).andReturn(newNode);
      }

      @Override protected void codeToTest() {
        assertThat(writer.addNewTestXmlNode(targetNode, test)).isSameAs(newNode);
      }
    }.run();
  }
}
