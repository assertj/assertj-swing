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
package org.assertj.swing.testng.listener;

import static java.io.File.separator;
import static java.util.logging.Level.SEVERE;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;
import static org.assertj.swing.testng.listener.ScreenshotFileNameGenerator.screenshotFileNameFrom;
import static org.assertj.swing.util.Strings.isNullOrEmpty;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.annotation.GUITestFinder;
import org.assertj.swing.image.ImageException;
import org.assertj.swing.image.NoopScreenshotTaker;
import org.assertj.swing.image.ScreenshotTaker;
import org.assertj.swing.image.ScreenshotTakerIF;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Understands a <a href="http://testng.org" target="_blank">TestNG</a> listener that takes a screenshot when a GUI test
 * fails.
 * <p>
 * <strong>Note:</strong> A test is consider a GUI test if it is marked with the annotation
 * <code>{@link org.assertj.swing.annotation.GUITest}</code>.
 * </p>
 * <p>
 * To use this listener, we just need to make TestNG aware of it. The following is an example using Ant:
 * </p>
 *
 * <pre>
 * &lt;testng <strong><span style="text-decoration: underline">listeners=&quot;org.assertj.swing.testng.listener.ScreenshotOnFailureListener&quot;</span></strong> outputDir=&quot;${target.test.results.dir}&quot; haltOnFailure=&quot;true&quot; verbose=&quot;2&quot;&gt;
 *   &lt;classfileset dir=&quot;${target.test.classes.dir}&quot; includes=&quot;&#42;&#42;/&#42;Test.class&quot; /&gt;
 *   &lt;classpath location=&quot;${target.test.classes.dir}&quot; /&gt;
 *   &lt;classpath location=&quot;${target.classes.dir}&quot; /&gt;
 *   &lt;classpath refid=&quot;test.classpath&quot; /&gt;
 * &lt;/testng&gt;
 * </pre>
 *
 * <p>
 * You can find more information <a href="http://www.jroller.com/page/alexRuiz?entry=screenshots_of_failures_in_test"
 * target="_blank">here</a>.
 * </p>
 *
 * @author Alex Ruiz
 */
public class ScreenshotOnFailureListener extends AbstractTestListener {

  private static Logger logger = Logger.getAnonymousLogger();

  private ScreenshotTakerIF screenshotTaker;
  private OutputDirectory output;
  private boolean ready;

  /**
   * Creates a new <code>{@link ScreenshotOnFailureListener}</code>.
   */
  public ScreenshotOnFailureListener() {
    try {
      screenshotTaker = GraphicsEnvironment.isHeadless() ? new NoopScreenshotTaker() : new ScreenshotTaker();
    } catch (ImageException e) {
      logger.log(SEVERE, "Unable to create ScreenshotTaker", e);
    }
  }

  @VisibleForTesting
  String output() {
    return output.path();
  }

  /**
   * Gets the output directory from the given context after the test class is instantiated and before any configuration
   * method is called.
   *
   * @param context the given method context.
   */
  @Override
  public void onStart(ITestContext context) {
    output = new OutputDirectory(context);
    logger.info(concat("TestNG output directory: ", quote(output.path())));
    ready = output.hasPath() && screenshotTaker != null;
  }

  /**
   * When a test fails, this method takes a screenshot of the desktop and adds an hyperlink to the screenshot it in the
   * HTML test report.
   *
   * @param result contains information about the failing test.
   */
  @Override
  public void onTestFailure(ITestResult result) {
    if (!ready || !isGUITest(result))
      return;
    String screenshotFileName = takeScreenshotAndReturnFileName(result);
    if (isNullOrEmpty(screenshotFileName))
      return;
    logger.info(concat("Screenshot of desktop saved as: ", quote(screenshotFileName)));
    Reporter.setCurrentTestResult(result);
    Reporter.log(concat("<a href=\"", screenshotFileName, "\">Screenshot</a>"));
  }

  private static boolean isGUITest(ITestResult testResult) {
    Class<?> realClass = testResult.getTestClass().getRealClass();
    Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
    return GUITestFinder.isGUITest(realClass, testMethod);
  }

  private String takeScreenshotAndReturnFileName(ITestResult result) {
    String imageName = screenshotFileNameFrom(result);
    String imagePath = concat(output(), separator, imageName);
    try {
      output.createIfNecessary();
      screenshotTaker.saveDesktopAsPng(imagePath);
    } catch (Exception e) {
      logger.log(SEVERE, e.getMessage(), e);
      return null;
    }
    return imageName;
  }
}
