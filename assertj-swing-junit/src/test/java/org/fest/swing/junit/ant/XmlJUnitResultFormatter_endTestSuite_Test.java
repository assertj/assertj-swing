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

import static java.lang.Double.parseDouble;
import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.util.DOMElementWriter;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlJUnitResultFormatter#endTestSuite(JUnitTest)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter_endTestSuite_Test extends XmlJUnitResultFormatter_TestCase {

  private JUnitTest suite;

  @Override void onSetUp() {
    suite = startTestSuiteWithStatistics();
  }

  @Test
  public void should_write_suite_statisticsAndWriteXmlToOutputStream() {
    formatter.endTestSuite(suite);
    assertThatStatisticsWereAddedToXml();
    XmlNode root = root();
    assertThatThereAreNoPropertiesIn(root);
    assertThat(textIn(output)).isEqualTo(textOf(root));
  }

  @Test
  public void should_write_XML_to_OutputStream() {
    formatter.endTestSuite(suite);
    assertThat(textIn(output)).isEqualTo(textOf(root()));
  }

  @Test
  public void should_not_throw_error_if_output_is_null() {
    formatter.setOutput(null);
    formatter.endTestSuite(suite);
    assertThatThereAreNoPropertiesIn(root());
    assertThat(output.toByteArray()).isEmpty();
  }

  private void assertThatStatisticsWereAddedToXml() {
    XmlNode root = root();
    assertThat(root.attributeCount()).isEqualTo(7);
    assertThat(root.valueOfAttribute("errors")).isEqualTo("6");
    assertThat(root.valueOfAttribute("failures")).isEqualTo("8");
    assertThat(root.valueOfAttribute("tests")).isEqualTo("18");
    double time = parseDouble(root.valueOfAttribute("time"));
    assertThat(time).isGreaterThanOrEqualTo(0d);
    assertThatSuiteAndEnvironmentInfoWereAddedTo(root);
  }

  private JUnitTest startTestSuiteWithStatistics() {
    JUnitTest s = startSuite();
    s.setCounts(18, 8, 6);
    return s;
  }

  private static String textOf(XmlNode xml) {
    ByteArrayOutputStream o = new ByteArrayOutputStream();
    new XmlOutputWriter().write(xml, o, new DOMElementWriter());
    return textIn(o);
  }

  private static String textIn(ByteArrayOutputStream o) {
    return new String(o.toByteArray());
  }

}
