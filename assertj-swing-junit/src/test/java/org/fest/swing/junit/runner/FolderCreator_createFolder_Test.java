/*
 * Created on Apr 24, 2009
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

import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.util.Files.newTemporaryFolder;

import java.io.File;
import java.io.IOException;

import org.easymock.EasyMock;
import org.fest.mocks.EasyMockTemplate;
import org.fest.util.FilesException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link FolderCreator#createFolder(File, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class FolderCreator_createFolder_Test {

  private static FolderCreator creator;

  @BeforeClass public static void setUpOnce() {
    creator = new FolderCreator();
  }

  @Test
  public void should_create_folder_in_given_parent() throws IOException {
    File parent = null;
    File child = null;
    try {
      parent = newTemporaryFolder();
      String childName = "child";
      child = creator.createFolder(parent, childName);
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

  private static void delete(File...files) {
    for (File f : files) if (f != null) f.delete();
  }

  @Test
  public void should_throw_Exception_in_case_of_error() {
    final File f = createMock(File.class);
    final RuntimeException error = new RuntimeException();
    new EasyMockTemplate(f) {
      @Override protected void expectations() throws Exception {
        EasyMock.expect(f.getCanonicalPath()).andThrow(error);
      }

      @Override protected void codeToTest() {
        try {
          creator.createFolder(f, "hello");
          fail("expecting exception");
        } catch (FilesException e) {
          assertThat(e).hasMessage("Unable to create directory 'hello'");
          assertThat(e.getCause()).isSameAs(error);
        }
      }
    }.run();
  }
}
