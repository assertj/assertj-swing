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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.util.Files.newTemporaryFolder;
import static org.easymock.classextension.EasyMock.createMock;

import java.io.File;
import java.io.IOException;

import org.assertj.core.api.exception.RuntimeIOException;
import org.easymock.EasyMock;
import org.fest.mocks.EasyMockTemplate;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link FolderCreator#createFolder(File, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class FolderCreator_createFolder_Test {

  private static FolderCreator creator;

  @BeforeClass
  public static void setUpOnce() {
    creator = new FolderCreator();
  }

  @Test
  public void should_Create_Folder_In_Given_Parent() throws IOException {
    File parent = null;
    File child = null;
    try {
      parent = newTemporaryFolder();
      String childName = "child";
      child = creator.createFolder(parent, childName, true);
      assertThat(child).isDirectory();
      assertThat(child.getName()).isEqualTo(childName);
      assertThat(pathOf(child.getParentFile())).isEqualTo(pathOf(parent));
    } finally {
      delete(child, parent);
    }
  }

  private static String pathOf(File f) throws IOException {
    return f.getCanonicalPath();
  }

  private static void delete(File... files) {
    for (File f : files)
      if (f != null)
        f.delete();
  }

  @Test
  public void should_Throw_Exception_In_Case_Of_Error() {
    final File f = createMock(File.class);
    final RuntimeException error = new RuntimeException();
    new EasyMockTemplate(f) {
      @Override
      protected void expectations() throws Exception {
        EasyMock.expect(f.getCanonicalPath()).andThrow(error);
      }

      @Override
      protected void codeToTest() {
        try {
          creator.createFolder(f, "hello", true);
          fail("expecting exception");
        } catch (RuntimeIOException e) {
          assertThat(e).hasMessage("Unable to create directory 'hello'");
          assertThat(e.getCause()).isSameAs(error);
        }
      }
    }.run();
  }
}
