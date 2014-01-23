/*
 * Created on Jun 6, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.*;

import java.io.OutputStream;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.swing.junit.xml.XmlDocument;
import org.fest.swing.junit.xml.XmlNode;

/**
 * Understands a copy of the original <code>XMLJUnitResultFormatter</code>, with flexibility for extension.
 *
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter implements JUnitResultFormatter {

  private XmlNode xmlRoot;

  private OutputStream out;  // where to write the log to

  private final TestCollection tests;

  private final SuiteXmlNodeWriter suiteXmlNodeWriter;
  private final EnvironmentXmlNodeWriter environmentXmlNodeWriter;
  private final TestXmlNodeWriter testXmlNodeWriter;

  private final XmlOutputWriter xmlOutputWriter;

  /**
   * Creates a new </code>{@link XmlJUnitResultFormatter}</code>.
   */
  public XmlJUnitResultFormatter() {
    tests = new TestCollection();
    suiteXmlNodeWriter = new SuiteXmlNodeWriter();
    environmentXmlNodeWriter = new EnvironmentXmlNodeWriter();
    testXmlNodeWriter = new TestXmlNodeWriter();
    xmlOutputWriter = new XmlOutputWriter();
  }

  // for testing only
  final TestCollection tests() { return tests; }

  /**
   * Sets the stream the formatter is supposed to write its results to.
   * @param out the output stream to use.
   */
  public final void setOutput(OutputStream out) {
    this.out = out;
  }

  /**
   * This is what the test has written to <code>System.out</code>,
   * @param out the <code>String</code> to write.
   */
  public final void setSystemOutput(String out) {
    formatOutput(SYSTEM_OUT, out);
  }

  /**
   * This is what the test has written to <code>System.err</code>.
   * @param out the <code>String</code> to write.
   */
  public final void setSystemError(String out) {
    formatOutput(SYSTEM_ERR, out);
  }

  private void formatOutput(String type, String output) {
    xmlRoot.addNewNode(type).addCdata(output);
  }

  protected final XmlNode xmlRootNode() { return xmlRoot; }

  /**
   * The whole test suite started. This method starts creation of the XML report.
   * @param suite the test suite.
   * @throws ExceptionInInitializerError if the underlying XML document could not be created.
   */
  public final void startTestSuite(JUnitTest suite) {
    XmlDocument document = new XmlDocument();
    xmlRoot = document.newRoot(TESTSUITE);
    suiteXmlNodeWriter.writeSuiteName(xmlRoot, suite)
                      .writeSuiteProperties(xmlRoot, suite);
    environmentXmlNodeWriter.writeHostName(xmlRoot)
                            .writeTimestamp(xmlRoot);
    onStartTestSuite(suite);
  }

  /**
   * Hook for subclasses to add extra functionality after the whole test suite started.
   * @param suite the test suite.
   */
  protected void onStartTestSuite(JUnitTest suite) {}

  /**
   * The whole test suite ended. This method finishes writing the XML report and writes its contents to this
   * formatter's <code>{@link OutputStream}</code>.
   * @param suite the test suite.
   * @throws BuildException on error.
   */
  public final void endTestSuite(JUnitTest suite) {
    suiteXmlNodeWriter.writeSuiteStatistics(xmlRoot, suite);
    if (out == null) return;
    xmlOutputWriter.write(xmlRoot, out);
  }

  /**
   * A new test is started.
   * @param test the test.
   */
  public final void startTest(Test test) {
    tests.started(test);
  }

  /**
   * A test is finished.
   * @param test the test.
   */
  public final void endTest(Test test) {
    if (!tests.wasStarted(test)) startTest(test);
    XmlNode testNode = xmlNodeForFinished(test);
    testXmlNodeWriter.writeTestExecutionTime(testNode, tests.startTimeOf(test));
  }

  private XmlNode xmlNodeForFinished(Test test) {
    if (tests.wasFailed(test)) return tests.xmlNodeFor(test);
    XmlNode newTestXmlNode = testXmlNodeWriter.addNewTestXmlNode(xmlRoot, test);
    tests.addXmlNode(test, newTestXmlNode);
    return newTestXmlNode;
  }

  /**
   * A test failed.
   * @param test the test.
   * @param failedAssertion the failed assertion.
   */
  public final void addFailure(Test test, AssertionFailedError failedAssertion) {
    addFailure(test, (Throwable)failedAssertion);
  }

  /**
   * A test failed.
   * @param test the test.
   * @param error the exception.
   */
  public final void addFailure(Test test, Throwable error) {
    XmlNode errorXmlNode = formatError(FAILURE, test, error);
    onFailureOrError(test, error, errorXmlNode);
  }

  /**
   * An error occurred while running the test.
   * @param test the test.
   * @param error the error.
   */
  public final void addError(Test test, Throwable error) {
    XmlNode errorXmlNode = formatError(ERROR, test, error);
    onFailureOrError(test, error, errorXmlNode);
  }

  private XmlNode formatError(String type, Test test, Throwable error) {
    if (test != null) {
      endTest(test);
      tests.failed(test);
    }
    XmlNode errorXmlNode = xmlForFailed(test).addNewNode(type);
    writeErrorAndStackTrace(error, errorXmlNode);
    return errorXmlNode;
  }

  private XmlNode xmlForFailed(Test test) {
    if (test != null) return tests.xmlNodeFor(test);
    return xmlRoot;
  }

  /**
   * Writes the stack trace and message of the given error to the given XML node.
   * @param error the given error.
   * @param errorXmlNode the XML node to write to.
   */
  protected final void writeErrorAndStackTrace(Throwable error, XmlNode errorXmlNode) {
    testXmlNodeWriter.writeErrorAndStackTrace(errorXmlNode, error);
  }

  /**
   * Hook for subclasses to add extra functionality after a test failure or a test execution error.
   * @param test the executing test.
   * @param error the reason of the failure or error.
   * @param errorXmlNode the XML element containing information about the test failure or error.
   */
  protected void onFailureOrError(Test test, Throwable error, XmlNode errorXmlNode) {}
}
