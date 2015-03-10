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
package org.assertj.swing.junit.runner;

import static java.io.File.separator;
import static org.assertj.core.util.Strings.concat;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import java.io.File;
import java.io.IOException;

import org.assertj.swing.image.ScreenshotTaker;
import org.fest.mocks.EasyMockTemplate;
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

  @Before
  public void setUp() {
    screenshotTaker = createMock(ScreenshotTaker.class);
    imageFolder = createMock(File.class);
    failureScreenshotTaker = new FailureScreenshotTaker(imageFolder, screenshotTaker);
  }

  @Test
  public void should_Save_Screenshot_With_Given_Test_Name_At_Given_Folder() {
    new EasyMockTemplate(screenshotTaker, imageFolder) {
      @Override
      protected void expectations() throws Exception {
        expect(imageFolder.getCanonicalPath()).andReturn("myPath");
        screenshotTaker.saveDesktopAsPng(concat("myPath", separator, "testName.png"));
        expectLastCall().once();
      }

      @Override
      protected void codeToTest() {
        failureScreenshotTaker.saveScreenshot("testName");
      }
    }.run();
  }

  @Test
  public void should_Not_Rethrow_Exceptions() {
    new EasyMockTemplate(screenshotTaker, imageFolder) {
      @Override
      protected void expectations() throws Exception {
        expect(imageFolder.getCanonicalPath()).andThrow(new IOException("Thrown on purpose"));
      }

      @Override
      protected void codeToTest() {
        failureScreenshotTaker.saveScreenshot("testName");
      }
    }.run();
  }
}
