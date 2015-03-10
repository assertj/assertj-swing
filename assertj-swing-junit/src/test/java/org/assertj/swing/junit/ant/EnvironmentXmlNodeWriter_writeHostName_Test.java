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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.HOSTNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.junit.xml.XmlAttribute.name;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import java.net.UnknownHostException;

import org.assertj.swing.junit.xml.XmlNode;
import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for <code>{@link EnvironmentXmlNodeWriter#writeHostName(XmlNode)}</code>.
 * 
 * @author Alex Ruiz
 */
public class EnvironmentXmlNodeWriter_writeHostName_Test extends EnvironmentXmlNodeWriter_TestCase {

  @Test
  public void should_Write_Host_Name_As_Attribute() {
    final String hostName = "myHost";
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      @Override
      protected void expectations() throws Exception {
        expect(hostNameReader.localHostName()).andReturn(hostName);
        targetNode.addAttribute(name(HOSTNAME).value(hostName));
        expectLastCall().once();
      }

      @Override
      protected void codeToTest() {
        assertThat(writer.writeHostName(targetNode)).isSameAs(writer);
      }
    }.run();
  }

  @Test
  public void should_Write_Local_Host_As_Attribute_If_Host_Name_Could_Not_Be_Obtained() {
    final UnknownHostException e = new UnknownHostException();
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      @Override
      protected void expectations() throws Exception {
        expect(hostNameReader.localHostName()).andThrow(e);
        targetNode.addAttribute(name(HOSTNAME).value("localhost"));
        expectLastCall().once();
      }

      @Override
      protected void codeToTest() {
        assertThat(writer.writeHostName(targetNode)).isSameAs(writer);
      }
    }.run();
  }
}
