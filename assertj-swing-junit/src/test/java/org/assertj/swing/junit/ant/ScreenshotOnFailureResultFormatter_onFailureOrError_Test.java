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

import static java.lang.String.valueOf;
import static java.security.AccessController.doPrivileged;
import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.reflect.core.Reflection.field;

import java.security.PrivilegedAction;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.assertj.swing.junit.xml.XmlNode;
import org.fest.mocks.EasyMockTemplate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for
 * <code>{@link ScreenshotOnFailureResultFormatter#onFailureOrError(junit.framework.Test, Throwable, XmlNode)}</code>.
 * 
 * @author Alex Ruiz
 */
public class ScreenshotOnFailureResultFormatter_onFailureOrError_Test {

  private static final String HEADLESS_MODE_PROPERTY = "java.awt.headless";

  private ScreenshotOnFailureResultFormatter formatter;

  @Before
  public void setUp() {
    formatter = new ScreenshotOnFailureResultFormatter();
  }

  @Ignore("this test passes only when run individually")
  public void should_Write_Error_In_XML_Document_If_ScreenshotWriter_Could_Not_Be_Created() {
    headlessAWT(true); // force an ImageException to be thrown
    try {
      formatter.startTestSuite(new JUnitTest());
      XmlNode root = formatter.xmlRootNode();
      assertThat(root.size()).isEqualTo(2);
      XmlNode errorNode = root.child(1);
      assertThat(errorNode.name()).isEqualTo("error");
    } finally {
      headlessAWT(false);
    }
  }

  private void headlessAWT(final boolean headless) {
    doPrivileged(new PrivilegedAction<Void>() {
      @Override
      public Void run() {
        System.setProperty(HEADLESS_MODE_PROPERTY, valueOf(headless));
        return null;
      }
    });
  }

  @Test
  public void should_Take_Screenshot_When_Test_Fails() {
    final ScreenshotXmlWriter writer = createMock(ScreenshotXmlWriter.class);
    updateWriterInFormatter(writer);
    final junit.framework.Test test = failingTest();
    final XmlNode errorElement = createMock(XmlNode.class);
    new EasyMockTemplate(writer) {
      @Override
      protected void expectations() {
        writer.writeScreenshot(errorElement, test);
        expectLastCall().once();
      }

      @Override
      protected void codeToTest() {
        formatter.onFailureOrError(test, new Throwable(), errorElement);
      }
    }.run();
  }

  @Test
  public void should_Not_Take_Screenshot_When_Test_Fails_If_ScreenshotWriter_Is_Null() {
    updateWriterInFormatter(null);
    formatter.onFailureOrError(failingTest(), new Throwable(), createMock(XmlNode.class));
    // no assertions to be made...are we sure this test is meaningful?
  }

  private void updateWriterInFormatter(final ScreenshotXmlWriter writer) {
    field("screenshotXmlWriter").ofType(ScreenshotXmlWriter.class).in(formatter).set(writer);
  }

  private junit.framework.Test failingTest() {
    return createMock(junit.framework.Test.class);
  }
}
