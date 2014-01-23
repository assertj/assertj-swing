/*
 * Created on Mar 18, 2009
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

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Files.currentFolder;

import java.io.File;

import org.fest.mocks.EasyMockTemplate;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ImageFolderCreator#createImageFolder()}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageFolderCreator_createImageFolder_Test {

  private FolderCreator folderCreator;
  private ImageFolderCreator imageFolderCreator;

  @Before
  public void setUp() {
    folderCreator = createMock(FolderCreator.class);
    imageFolderCreator = new ImageFolderCreator(folderCreator);
  }

  @Test
  public void should_create_image_folder() {
    final File createdFolder = new File("fake");
    new EasyMockTemplate(folderCreator) {
      @Override protected void expectations() {
        expect(folderCreator.createFolder(currentFolder(), "failed-gui-tests")).andReturn(createdFolder);
      }

      @Override protected void codeToTest() {
        assertThat(imageFolderCreator.createImageFolder()).isSameAs(createdFolder);
      }
    }.run();
  }
}
