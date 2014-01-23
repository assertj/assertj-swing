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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Systems.LINE_SEPARATOR;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlDocument;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlOutputWriter#write(org.fest.swing.junit.xml.XmlNode, java.io.OutputStream)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlOutputWriter_write_Test extends XmlOutputWriter_TestCase {

  @Test
  public void should_write_XML_to_OutputStream() throws Exception {
    MyOutputStream out = new MyOutputStream();
    writer.write(xml(), out);
    String actual = new String(out.toByteArray());
    assertThat(actual).isEqualTo(expectedXml());
    assertThat(out.closed).isTrue();
  }


  @Test
  public void should_not_close_OutputStream_when_using_SystemOut_or_SystemErr() {
    final StandardOutputStreams streams = createMock(StandardOutputStreams.class);
    writer = new XmlOutputWriter(streams);
    final MyOutputStream out = new MyOutputStream();
    new EasyMockTemplate(streams) {
      @Override protected void expectations() {
        expect(streams.isStandardOutOrErr(out)).andReturn(true);
      }

      @Override protected void codeToTest() throws Exception {
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
    expected.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(LINE_SEPARATOR)
            .append("<root>").append(LINE_SEPARATOR)
            .append("  <child />").append(LINE_SEPARATOR)
            .append("</root>").append(LINE_SEPARATOR);
    return expected.toString();
  }

  private static class MyOutputStream extends ByteArrayOutputStream {
    boolean closed;

    @Override public void close() throws IOException {
      closed = true;
      super.close();
    }
  }
}
