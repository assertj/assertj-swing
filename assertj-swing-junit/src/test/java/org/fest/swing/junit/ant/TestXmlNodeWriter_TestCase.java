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

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import junit.framework.TestResult;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Before;

/**
 * Base test case for <code>{@link TestXmlNodeWriter}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class TestXmlNodeWriter_TestCase {

  XmlNode targetNode;
  TestXmlNodeWriter writer;

  @Before public final void setUp() {
    targetNode = mockXmlNode();
    writer = new TestXmlNodeWriter();
  }

  public void shouldWriteStackTraceAsTextNode() {
    final Exception error = new Exception();
    final StackTraceFilter filter = createMock(StackTraceFilter.class);
    writer = new TestXmlNodeWriter(filter);
    new EasyMockTemplate(filter, targetNode) {
      @Override protected void expectations() {
        expect(filter.filter(error)).andReturn("Hello");
        targetNode.addText("Hello");
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        assertThat(writer.writeStackTrace(targetNode, error)).isSameAs(writer);
      }
    }.run();

  }

  final XmlNode mockXmlNode() {
    return createMock(XmlNode.class);
  }

  static class TestStub implements junit.framework.Test {
    private final String name;

    TestStub(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public int countTestCases() { return 0; }

    public void run(TestResult result) {}
  }
}
