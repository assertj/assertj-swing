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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;
import static org.easymock.EasyMock.expectLastCall;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link TestXmlNodeWriter#writeError(XmlNode, Throwable)}</code>.
 *
 * @author Alex Ruiz
 */
public class TestXmlNodeWriter_writeError_Test extends TestXmlNodeWriter_TestCase {

  @Test
  public void should_write_error_type_and_message_as_attributes() {
    final String errorMsg = "Thrown on purpose";
    final Exception e = new Exception(errorMsg);
    new EasyMockTemplate(targetNode) {
      @Override protected void expectations() {
        targetNode.addAttribute(name(ATTR_MESSAGE).value(errorMsg));
        expectLastCall().once();
        targetNode.addAttribute(name(ATTR_TYPE).value(Exception.class.getName()));
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeError(targetNode, e)).isSameAs(writer);
      }
    }.run();
  }

  @Test
  public void should_write_only_error_type_as_attribute_when_error_message_is_empty() {
    final Exception e = new Exception("");
    new EasyMockTemplate(targetNode) {
      @Override protected void expectations() {
        targetNode.addAttribute(name(ATTR_TYPE).value(Exception.class.getName()));
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeError(targetNode, e)).isSameAs(writer);
      }
    }.run();
  }
}
