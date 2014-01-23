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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TIMESTAMP;
import static org.easymock.EasyMock.*;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import org.easymock.IArgumentMatcher;
import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlAttribute;
import org.junit.Test;

/**
 * Test for <code>{@link EnvironmentXmlNodeWriter#writeTimestamp(org.fest.swing.junit.xml.XmlNode)}</code>.
 *
 * @author Alex Ruiz
 */
public class EnvironmentXmlNodeWriter_writeTimestamp_Test extends EnvironmentXmlNodeWriter_TestCase {

  private Date date;
  private String formatted;

  @Override void onSetUp() {
    date = new Date();
    formatted = "2009-06-13T15:06:10";
    reportMatcher(new BeforeOrEqualDateMatcher(date));
  }

  @Test
  public void shouldWriteFormattedCurrentDateAsAttribute() {
    new EasyMockTemplate(timeStampFormatter, hostNameReader, targetNode) {
      @Override protected void expectations() {
        expect(timeStampFormatter.format(date)).andReturn(formatted);
        targetNode.addAttribute(XmlAttribute.name(TIMESTAMP).value(formatted));
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeTimestamp(targetNode)).isSameAs(writer);
      }
    }.run();
  }

  private static class BeforeOrEqualDateMatcher implements IArgumentMatcher {
    private final Date date;

    BeforeOrEqualDateMatcher(Date date) {
      this.date = date;
    }

    public boolean matches(Object argument) {
      if (!(argument instanceof Date)) return false;
      Date other = (Date)argument;
      return date.before(other) || date.equals(other);
    }

    public void appendTo(StringBuffer buffer) {
      buffer.append(date);
    }
  }
}
