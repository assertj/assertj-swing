/*
 * Created on Apr 1, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.swing.image;

import static org.mockito.Mockito.mock;

/**
 * Implementations of {@link ImageFileWriter} to be used for testing.
 *
 * @author Alex Ruiz
 */
public final class TestImageFileWriters {
  public static ImageFileWriter singletonImageFileWriterMock() {
    return LazyLoadedSingleton.INSTANCE;
  }

  private static class LazyLoadedSingleton {
    static final ImageFileWriter INSTANCE = newImageFileWriterMock();
  }

  public static ImageFileWriter newImageFileWriterMock() {
    return mock(ImageFileWriter.class);
  }

  private TestImageFileWriters() {}
}
