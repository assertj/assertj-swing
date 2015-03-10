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
package org.assertj.swing.junit.ant;

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_CLASSNAME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_NAME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TESTCASE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.junit.xml.XmlAttribute.name;
import static org.assertj.swing.junit.xml.XmlAttributes.attributes;
import static org.easymock.EasyMock.expect;

import org.assertj.swing.junit.xml.XmlAttributes;
import org.assertj.swing.junit.xml.XmlNode;
import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for
 * <code>{@link TestXmlNodeWriter#addNewTestXmlNode(org.assertj.swing.junit.xml.XmlNode, junit.framework.Test)}</code>
 * 
 * @author Alex Ruiz
 */
public class TestXmlNodeWriter_addNewTestXmlNode_Test extends TestXmlNodeWriter_TestCase {

  @Test
  public void should_Add_Test_Node_As_Child() {
    final TestStub test = new TestStub("hello");
    final XmlNode newNode = mockXmlNode();
    new EasyMockTemplate(targetNode) {
      @Override
      protected void expectations() {
        XmlAttributes attributes = attributes(name(ATTR_NAME).value("hello"),
            name(ATTR_CLASSNAME).value(TestStub.class.getName()));
        expect(targetNode.addNewNode(TESTCASE, attributes)).andReturn(newNode);
      }

      @Override
      protected void codeToTest() {
        assertThat(writer.addNewTestXmlNode(targetNode, test)).isSameAs(newNode);
      }
    }.run();
  }

  @Test
  public void should_Add_Test_Node_As_Child_And_Set_Test_Name_To_Unknown_If_Test_Name_Is_Null() {
    final TestStub test = new TestStub(null);
    final XmlNode newNode = mockXmlNode();
    new EasyMockTemplate(targetNode) {
      @Override
      protected void expectations() {
        XmlAttributes attributes = attributes(name(ATTR_NAME).value("unknown"),
            name(ATTR_CLASSNAME).value(TestStub.class.getName()));
        expect(targetNode.addNewNode(TESTCASE, attributes)).andReturn(newNode);
      }

      @Override
      protected void codeToTest() {
        assertThat(writer.addNewTestXmlNode(targetNode, test)).isSameAs(newNode);
      }
    }.run();
  }
}
