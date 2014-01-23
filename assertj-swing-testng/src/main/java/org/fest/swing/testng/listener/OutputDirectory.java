/*
 * Created on Mar 9, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.swing.testng.listener;

import static org.fest.util.Strings.*;

import java.io.File;

import org.fest.util.FilesException;
import org.testng.ITestContext;

/**
 * Understands TestNG's output directory.
 *
 * @author Alex Ruiz
 */
class OutputDirectory {

  private final String path;

  OutputDirectory(ITestContext context) {
    path = context.getOutputDirectory();
  }

  boolean hasPath() {
    return !isEmpty(path);
  }

  String path() { return path; }

  void createIfNecessary() {
    File f = new File(path);
    if (f.isDirectory()) return;
    if (f.mkdirs()) return;
    throw new FilesException(concat("Unable to create output directory ", path));
  }
}
