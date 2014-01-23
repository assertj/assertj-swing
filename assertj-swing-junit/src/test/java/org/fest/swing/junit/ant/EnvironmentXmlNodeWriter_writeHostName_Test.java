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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.HOSTNAME;
import static org.easymock.EasyMock.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.junit.xml.XmlAttribute.name;

import java.net.UnknownHostException;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link EnvironmentXmlNodeWriter#writeHostName(XmlNode)}</code>.
 *
 * @author Alex Ruiz
 */
public class EnvironmentXmlNodeWriter_writeHostName_Test extends EnvironmentXmlNodeWriter_TestCase {

  @Test
  public void should_write_host_name_as_attribute() {
    final String hostName = "myHost";
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      @Override protected void expectations() throws Exception {
        expect(hostNameReader.localHostName()).andReturn(hostName);
        targetNode.addAttribute(name(HOSTNAME).value(hostName));
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeHostName(targetNode)).isSameAs(writer);
      }
    }.run();
  }

  @Test
  public void should_write_local_host_as_attribute_if_host_name_could_not_be_obtained() {
    final UnknownHostException e = new UnknownHostException();
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      @Override protected void expectations() throws Exception {
        expect(hostNameReader.localHostName()).andThrow(e);
        targetNode.addAttribute(name(HOSTNAME).value("localhost"));
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeHostName(targetNode)).isSameAs(writer);
      }
    }.run();
  }
}
