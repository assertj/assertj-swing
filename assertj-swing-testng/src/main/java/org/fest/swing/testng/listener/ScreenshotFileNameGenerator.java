/*
 * Created on Feb 15, 2009
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
package org.fest.swing.testng.listener;

import static org.fest.swing.image.ImageFileExtensions.PNG;
import static org.fest.util.Strings.join;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;

/**
 * Understands generation of the name of the image file containing the screenshot of the desktop.
 *
 * @author Alex Ruiz
 */
final class ScreenshotFileNameGenerator {

  private static final String NULL_PARAMETER_VALUE = "[null]";

  static String screenshotFileNameFrom(ITestResult result) {
    String[] parts = namePartsFrom(result);
    return join(parts).with(".");
  }

  private static String[] namePartsFrom(ITestResult result) {
    List<String> parts = new ArrayList<String>();
    parts.add(result.getTestClass().getName());
    parts.add(result.getMethod().getMethodName());
    addParameters(result, parts);
    parts.add(PNG);
    return parts.toArray(new String[parts.size()]);
  }

  private static void addParameters(ITestResult result, List<String> parts) {
    Object[] parameters = result.getParameters();
    if (parameters == null) return;
    for (Object parameter: parameters)
      parts.add(parameter != null ? parameter.toString() : NULL_PARAMETER_VALUE);
  }

  private ScreenshotFileNameGenerator() {}
}
