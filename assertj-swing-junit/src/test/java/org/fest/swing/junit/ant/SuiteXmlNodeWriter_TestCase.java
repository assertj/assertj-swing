/*
 * Created on Apr 6, 2009
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
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.swing.junit.xml.XmlAttributes.attributes;

import java.util.Properties;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.*;
import org.junit.Before;

/**
 * Base test case for <code>{@link SuiteXmlNodeWriter}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class SuiteXmlNodeWriter_TestCase {

  XmlNode targetNode;
  SuiteXmlNodeWriter writer;

  @Before
  public final void setUp() {
    targetNode = mockXmlNode();
    writer = new SuiteXmlNodeWriter();
    onSetUp();
  }

  void onSetUp() {}

  public void shouldWriterPropertiesAsAttributes() {
    Properties properties = new Properties();
    properties.setProperty("key1", "value1");
    properties.setProperty("key2", "value2");
    final JUnitTest suite = new JUnitTest("Hello");
    suite.setProperties(properties);
    final XmlNode propertiesNode = mockXmlNode();
    new EasyMockTemplate(targetNode, propertiesNode) {
      @Override protected void expectations() throws Exception {
        expect(targetNode.addNewNode(PROPERTIES)).andReturn(propertiesNode);
        XmlAttributes attributes1 = attributes(name(ATTR_NAME).value("key1"), name(ATTR_VALUE).value("value1"));
        expect(propertiesNode.addNewNode(PROPERTY, attributes1)).andReturn(mockXmlNode());
        XmlAttributes attributes2 = attributes(name(ATTR_NAME).value("key2"), name(ATTR_VALUE).value("value2"));
        expect(propertiesNode.addNewNode(PROPERTY, attributes2)).andReturn(mockXmlNode());
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeSuiteProperties(targetNode, suite)).isSameAs(writer);
      }
    }.run();
  }

  public void shouldNotAddPropertiesIfPropertiesIsNull() {
    final JUnitTest suite = new JUnitTest("Hello");
    assertThat(suite.getProperties()).isNull();
    final XmlNode propertiesNode = mockXmlNode();
    new EasyMockTemplate(targetNode, propertiesNode) {
      @Override protected void expectations() throws Exception {
        expect(targetNode.addNewNode(PROPERTIES)).andReturn(propertiesNode);
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeSuiteProperties(targetNode, suite)).isSameAs(writer);
      }
    }.run();
  }

  public void shouldNotAddPropertiesIfPropertiesIsEmpty() {
    final JUnitTest suite = new JUnitTest("Hello");
    suite.setProperties(new Properties());
    assertThat(suite.getProperties()).isEmpty();
    final XmlNode propertiesNode = mockXmlNode();
    new EasyMockTemplate(targetNode, propertiesNode) {
      @Override protected void expectations() throws Exception {
        expect(targetNode.addNewNode(PROPERTIES)).andReturn(propertiesNode);
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeSuiteProperties(targetNode, suite)).isSameAs(writer);
      }
    }.run();
  }

  private XmlNode mockXmlNode() {
    return createMock(XmlNode.class);
  }

  public void shouldWriteStatisticsAsAttribute() {
    final JUnitTest suite = new JUnitTest("Hello");
    suite.setCounts(6l, 2l, 1l);
    suite.setRunTime(8000l);
    new EasyMockTemplate(targetNode) {
      @Override protected void expectations() {
        expectAttributeAdded(name(ATTR_TESTS).value(6l));
        expectAttributeAdded(name(ATTR_FAILURES).value(2l));
        expectAttributeAdded(name(ATTR_ERRORS).value(1l));
        expectAttributeAdded(name(ATTR_TIME).value(8.0));
      }

      private void expectAttributeAdded(XmlAttribute a) {
        targetNode.addAttribute(a);
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeSuiteStatistics(targetNode, suite)).isSameAs(writer);
      }
    }.run();
  }
}
