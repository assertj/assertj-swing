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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_MESSAGE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.junit.xml.XmlAttribute.name;
import static org.easymock.EasyMock.expectLastCall;

import org.assertj.swing.junit.xml.XmlNode;
import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for <code>{@link TestXmlNodeWriter#writeError(XmlNode, Throwable)}</code>.
 * 
 * @author Alex Ruiz
 */
public class TestXmlNodeWriter_writeError_Test extends TestXmlNodeWriter_TestCase {

  @Test
  public void should_Write_Error_Type_And_Message_As_Attributes() {
    final String errorMsg = "Thrown on purpose";
    final Exception e = new Exception(errorMsg);
    new EasyMockTemplate(targetNode) {
      @Override
      protected void expectations() {
        targetNode.addAttribute(name(ATTR_MESSAGE).value(errorMsg));
        expectLastCall().once();
        targetNode.addAttribute(name(ATTR_TYPE).value(Exception.class.getName()));
        expectLastCall().once();
      }

      @Override
      protected void codeToTest() {
        assertThat(writer.writeError(targetNode, e)).isSameAs(writer);
      }
    }.run();
  }

  @Test
  public void should_Write_Only_Error_Type_As_Attribute_When_Error_Message_Is_Empty() {
    final Exception e = new Exception("");
    new EasyMockTemplate(targetNode) {
      @Override
      protected void expectations() {
        targetNode.addAttribute(name(ATTR_TYPE).value(Exception.class.getName()));
        expectLastCall().once();
      }

      @Override
      protected void codeToTest() {
        assertThat(writer.writeError(targetNode, e)).isSameAs(writer);
      }
    }.run();
  }
}
