/*
 * Created on Apr 13, 2009
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

import static org.apache.tools.ant.util.DateUtils.parseIso8601DateTimeOrDate;
import static org.easymock.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.assertions.AssertExtension;
import org.fest.swing.junit.xml.XmlNode;
import org.junit.Before;

/**
 * Base test case for <code>{@link XmlJUnitResultFormatter}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class XmlJUnitResultFormatter_TestCase {

  static final String ERROR_OR_FAILURE_MESSAGE = "Thrown on purpose";
  static final String CONSOLE_OUTPUT = "Hello World!";

  ByteArrayOutputStream output;
  BasicXmlJUnitResultFormatter formatter;
  TestCollection tests;

  @Before public final void setUp() {
    formatter = new BasicXmlJUnitResultFormatter();
    tests = formatter.tests();
    output = new ByteArrayOutputStream();
    formatter.setOutput(output);
    onSetUp();
  }

  void onSetUp() {}

  static void assertThatThereAreNoPropertiesIn(XmlNode root) {
    XmlNode properties = root.child(0);
    assertThat(properties.attributeCount()).isEqualTo(0);
    assertThat(properties.size()).isEqualTo(0);
  }

  static void assertThatSuiteAndEnvironmentInfoWereAddedTo(XmlNode root) {
    assertThat(root.valueOfAttribute("hostname")).isEqualTo(localHostName());
    assertThat(root.valueOfAttribute("name")).isEqualTo("test");
    Date timestamp = parseDate(root.valueOfAttribute("timestamp"));
    assertThat(timestamp.before(now()));
  }

  private static Date parseDate(String s) {
    try {
      return parseIso8601DateTimeOrDate(s);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private static Date now() { return new Date(); }

  private static String localHostName() {
    try {
      return new HostNameReader().localHostName();
    } catch (UnknownHostException e) {
      return "localhost";
    }
  }

  final JUnitTest startSuite() {
    JUnitTest suite = new JUnitTest("test");
    formatter.startTestSuite(suite);
    return suite;
  }

  static junit.framework.Test mockTest() {
    return createMock(junit.framework.Test.class);
  }

  final void assertThatTestWasStarted(junit.framework.Test test) {
    Map<junit.framework.Test, Long> startedTests = tests.started;
    assertThat(startedTests).hasSize(1);
    assertThat(startedTests.keySet()).containsOnly(test);
  }

  final AssertionFailedError errorOrFailure() {
    return new AssertionFailedError(ERROR_OR_FAILURE_MESSAGE);
  }

  final XmlNode root() {
    return formatter.xmlRootNode();
  }

  final void assertThatTestCaseNodeWasAddedTo(XmlNode root) {
    XmlNode testNode = firstTestCaseNodeIn(root);
    assertThat(testNode.name()).isEqualTo("testcase");
    assertThat(testNode.valueOfAttribute("classname")).startsWith("$Proxy");
    assertThat(testNode.valueOfAttribute("name")).isEqualTo("unknown");
    double executionTime = Double.parseDouble(testNode.valueOfAttribute("time"));
    assertThat(executionTime).isGreaterThanOrEqualTo(0d);
  }

  XmlNode firstTestCaseNodeIn(XmlNode root) {
    return root.child(1);
  }

  static void assertThatErrorOrFailureWasWrittenTo(XmlNode errorNode) {
    assertThat(errorNode.valueOfAttribute("message")).isEqualTo(ERROR_OR_FAILURE_MESSAGE);
    assertThat(errorNode.valueOfAttribute("type")).isEqualTo("junit.framework.AssertionFailedError");
    assertThat(errorNode.text()).startsWith("junit.framework.AssertionFailedError: " + ERROR_OR_FAILURE_MESSAGE);
  }

  static class BasicXmlJUnitResultFormatter extends XmlJUnitResultFormatter implements AssertExtension {
    final OnStartTestSuiteAssert onStartTestSuiteMethod = new OnStartTestSuiteAssert();
    final OnFailureOrErrorAssert onFailureOrErrorMethod = new OnFailureOrErrorAssert();

    @Override protected void onStartTestSuite(JUnitTest suite) {
      onStartTestSuiteMethod.calledWith(suite);
    }

    @Override protected void onFailureOrError(junit.framework.Test test, Throwable error, XmlNode errorXmlNode) {
      onFailureOrErrorMethod.calledWith(test, error, errorXmlNode);
    }
  }

  static class OnStartTestSuiteAssert implements AssertExtension {
    private boolean called;
    private JUnitTest suite;

    void calledWith(JUnitTest suitePassed) {
      called = true;
      suite = suitePassed;
    }

    void wasCalledPassing(JUnitTest expectedSuite) {
      assertThat(called).isTrue();
      assertThat(suite).isSameAs(expectedSuite);
    }
  }

  static class OnFailureOrErrorAssert implements AssertExtension {
    private boolean called;
    private junit.framework.Test test;
    private Throwable error;
    private XmlNode errorXmlNode;

    void calledWith(junit.framework.Test testPassed, Throwable errorPassed, XmlNode errorXmlNodePassed) {
      errorXmlNode = errorXmlNodePassed;
      called = true;
      test = testPassed;
      error = errorPassed;
    }

    void wasCalledPassing(junit.framework.Test expectedTest, Throwable expectedError, XmlNode expectedErrorXmlNode) {
      assertThat(called).isTrue();
      assertThat(test).isSameAs(expectedTest);
      assertThat(error).isSameAs(expectedError);
      assertThat(errorXmlNode).isEqualTo(expectedErrorXmlNode);
    }
  }
}
