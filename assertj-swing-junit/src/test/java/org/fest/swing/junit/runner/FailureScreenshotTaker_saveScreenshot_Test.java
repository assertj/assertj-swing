/*
 * Created on Mar 17, 2009
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
package org.fest.swing.junit.runner;

import static java.io.File.separator;
import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.util.Strings.concat;

import java.io.File;
import java.io.IOException;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.image.ScreenshotTaker;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link FailureScreenshotTaker}</code>.
 *
 * @author Alex Ruiz
 */
public class FailureScreenshotTaker_saveScreenshot_Test {

  private ScreenshotTaker screenshotTaker;
  private File imageFolder;
  private FailureScreenshotTaker failureScreenshotTaker;

  @Before public void setUp() {
    screenshotTaker = createMock(ScreenshotTaker.class);
    imageFolder = createMock(File.class);
    failureScreenshotTaker = new FailureScreenshotTaker(imageFolder, screenshotTaker);
  }

  @Test
  public void should_save_screenshot_with_given_test_name_at_given_folder() {
    new EasyMockTemplate(screenshotTaker, imageFolder) {
      @Override protected void expectations() throws Exception {
        expect(imageFolder.getCanonicalPath()).andReturn("myPath");
        screenshotTaker.saveDesktopAsPng(concat("myPath", separator, "testName.png"));
        expectLastCall().once();
      }

      @Override protected void codeToTest() {
        failureScreenshotTaker.saveScreenshot("testName");
      }
    }.run();
  }

  @Test
  public void should_not_rethrow_Exceptions() {
    new EasyMockTemplate(screenshotTaker, imageFolder) {
      @Override protected void expectations() throws Exception {
        expect(imageFolder.getCanonicalPath()).andThrow(new IOException("Thrown on purpose"));
      }

      @Override protected void codeToTest() {
        failureScreenshotTaker.saveScreenshot("testName");
      }
    }.run();
  }
}
