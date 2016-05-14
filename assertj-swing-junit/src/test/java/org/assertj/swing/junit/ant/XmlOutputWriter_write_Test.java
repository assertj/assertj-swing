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

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.assertj.swing.junit.xml.XmlDocument;
import org.assertj.swing.junit.xml.XmlNode;
import org.fest.mocks.EasyMockTemplate;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlOutputWriter#write(org.assertj.swing.junit.xml.XmlNode, java.io.OutputStream)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlOutputWriter_write_Test extends XmlOutputWriter_TestCase {

  @Test
  public void should_Write_XML_To_OutputStream() throws Exception {
    MyOutputStream out = new MyOutputStream();
    writer.write(xml(), out);
    String actual = new String(out.toByteArray());
    assertThat(actual).isEqualTo(expectedXml());
    assertThat(out.closed).isTrue();
  }

  @Test
  public void should_Not_Close_OutputStream_When_Using_SystemOut_Or_SystemErr() {
    final StandardOutputStreams streams = createMock(StandardOutputStreams.class);
    writer = new XmlOutputWriter(streams);
    final MyOutputStream out = new MyOutputStream();
    new EasyMockTemplate(streams) {
      @Override
      protected void expectations() {
        expect(streams.isStandardOutOrErr(out)).andReturn(true);
      }

      @Override
      protected void codeToTest() throws Exception {
        writer.write(xml(), out);
      }
    }.run();
    String actual = new String(out.toByteArray());
    assertThat(actual).isEqualTo(expectedXml());
    assertThat(out.closed).isFalse();
  }

  private XmlNode xml() throws Exception {
    XmlNode root = new XmlDocument().newRoot("root");
    root.addNewNode("child");
    return root;
  }

  private String expectedXml() {
    StringBuilder expected = new StringBuilder();
    expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(lineSeparator()).append("<root>")
            .append(lineSeparator()).append("  <child />").append(lineSeparator()).append("</root>")
            .append(lineSeparator());
    return expected.toString();
  }

  private static class MyOutputStream extends ByteArrayOutputStream {
    boolean closed;

    @Override
    public void close() throws IOException {
      closed = true;
      super.close();
    }
  }
}
