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

import static org.fest.util.Files.currentFolder;

import java.io.File;

import org.fest.util.FilesException;

/**
 * Understands creation of the folder where screenshots of failed GUI tests will be saved to.
 *
 * @author Alex Ruiz
 */
public class ImageFolderCreator {

  private static final String FAILED_GUI_TESTS_FOLDER = "failed-gui-tests";

  private final FolderCreator folderCreator;

  public ImageFolderCreator() {
    this(new FolderCreator());
  }

  ImageFolderCreator(FolderCreator folderCreator) {
    this.folderCreator = folderCreator;
  }

  /**
   * Creates the folder where to save screenshots of failing GUI tests. The name of the folder to create is
   * 'failed-gui-tests'. If the folder already exists, it is deleted and recreated again.
   * @return the created folder.
   * @throws FilesException if any error occurs when creating the folder.
   */
  public File createImageFolder() {
    return folderCreator.createFolder(currentFolder(), FAILED_GUI_TESTS_FOLDER);
  }
}
