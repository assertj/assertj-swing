/*
 * Created on Jun 4, 2007
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

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ERROR;
import junit.framework.Test;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.fest.swing.image.ImageException;
import org.fest.swing.junit.xml.XmlNode;

/**
 * Understands a JUnit XML report formatter that takes a screenshot when a GUI test fails.
 * <p>
 * <strong>Note:</strong> A test is consider a GUI test if it is marked with the annotation
 * <code>{@link org.fest.swing.annotation.GUITest}</code>.
 * </p>
 *
 * @author Alex Ruiz
 */
public final class ScreenshotOnFailureResultFormatter extends XmlJUnitResultFormatter {

  private ScreenshotXmlWriter screenshotXmlWriter;

  /**
   * Execution of the JUnit test suite started. Internally, this method creates the writer responsible for embedding
   * a screenshot of the desktop in the XML report.
   * @param suite the JUnit test suite.
   */
  @Override protected void onStartTestSuite(JUnitTest suite) {
    try {
      screenshotXmlWriter = new ScreenshotXmlWriter();
    } catch (ImageException e) {
      informCannotTakeScreenshots(e);
    }
  }

  private void informCannotTakeScreenshots(ImageException error) {
    XmlNode errorNode = xmlRootNode().addNewNode(ERROR);
    writeErrorAndStackTrace(error, errorNode);
  }

  /**
   * A test failed. This method embeds a screenshot of the desktop if the failing test is a GUI test.
   * @param test the failing test.
   * @param error the cause of the failure or error.
   * @param target the element in the XML report containing information about the failure.
   */
  @Override protected void onFailureOrError(Test test, Throwable error, XmlNode target) {
    if (screenshotXmlWriter == null) return;
    screenshotXmlWriter.writeScreenshot(target, test);
  }
}
