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
import static org.assertj.core.util.Files.delete;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;

import java.io.File;

import org.assertj.core.util.FilesException;

/**
 * Understands creation of folders.
 * 
 * @author Alex Ruiz
 */
class FolderCreator {

  File createFolder(File parent, String name) {
    try {
      String canonicalPath = parent.getCanonicalPath();
      File imageFolder = new File(concat(canonicalPath, separator, name));
      delete(imageFolder);
      imageFolder.mkdir();
      return imageFolder;
    } catch (Exception e) {
      throw new FilesException(concat("Unable to create directory ", quote(name)), e);
    }
  }
}
