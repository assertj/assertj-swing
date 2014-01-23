/*
 * Created on Mar 20, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.fest.swing.image.ImageFileExtensions.PNG;
import static org.fest.swing.junit.ant.ImageHandler.encodeBase64;
import static org.fest.swing.junit.ant.Tests.*;
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.util.Strings.*;

import java.awt.image.BufferedImage;

import junit.framework.Test;

import org.fest.swing.image.ScreenshotTaker;
import org.fest.swing.junit.xml.XmlNode;

/**
 * Understands taking a screenshot of the desktop and, encoding and writing the resulting image into a XML element.
 *
 * @author Alex Ruiz
 */
class ScreenshotXmlWriter {

  private static final String SCREENSHOT_ELEMENT = "screenshot";
  private static final String SCREENSHOT_FILE_ATTRIBUTE = "file";

  private final ScreenshotTaker screenshotTaker;
  private final GUITestRecognizer guiTestRecognizer;

  ScreenshotXmlWriter() {
    this(new ScreenshotTaker(), new GUITestRecognizer());
  }

  ScreenshotXmlWriter(ScreenshotTaker screenshotTaker, GUITestRecognizer guiTestRecognizer) {
    this.screenshotTaker = screenshotTaker;
    this.guiTestRecognizer = guiTestRecognizer;
  }

  void writeScreenshot(XmlNode target, Test test) {
    String testClass = testClassNameFrom(test);
    String testMethod = testMethodNameFrom(test);
    if (!guiTestRecognizer.isGUITest(testClass, testMethod)) return;
    String image = takeScreenshotAndReturnEncoded();
    if (isEmpty(image)) return;
    writeScreenshotFileName(target, image, imageFileName(testClass, testMethod));
  }

  private String takeScreenshotAndReturnEncoded() {
    BufferedImage image = screenshotTaker.takeDesktopScreenshot();
    return encodeBase64(image);
  }

  private void writeScreenshotFileName(XmlNode target, String encodedImage, String imageFileName) {
    XmlNode screenshotNode = target.parentNode().addNewNode(SCREENSHOT_ELEMENT);
    screenshotNode.addAttribute(name(SCREENSHOT_FILE_ATTRIBUTE).value(imageFileName));
    screenshotNode.addText(encodedImage);
  }

  private String imageFileName(String testClass, String testMethod) {
    return join(testClass, testMethod, PNG).with(".");
  }
}
