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

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import java.io.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.util.DOMElementWriter;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link XmlOutputWriter#write(XmlNode, java.io.OutputStream, org.apache.tools.ant.util.DOMElementWriter)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlOutputWriter_writeWithDOMElementWriter_Test extends XmlOutputWriter_TestCase {

  @Test
  public void should_throw_BuildException_if_something_goes_wrong() {
    XmlNode xmlNode = createMock(XmlNode.class);
    MyDOMElementWriter xmlWriter = new MyDOMElementWriter();
    try {
      writer.write(xmlNode, new ByteArrayOutputStream(), xmlWriter);
      fail("expecting exception");
    } catch (BuildException expected) {
      assertThat(expected.getMessage()).isEqualTo("Unable to write log file");
      assertThat(expected.getCause()).isSameAs(xmlWriter.error);
    }
  }

  private static class MyDOMElementWriter extends DOMElementWriter {
    final IOException error = new IOException("Thrown on purpose");

    @Override public void write(Element element, Writer out, int indent, String indentWith) throws IOException {
      throw error;
    }
  }
}
