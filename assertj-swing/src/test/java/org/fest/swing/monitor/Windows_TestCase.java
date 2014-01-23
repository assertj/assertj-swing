/*
 * Created on Oct 9, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestWindow;

/**
 * Base test case for {@link Windows}.
 * 
 * @author Alex Ruiz
 */
public abstract class Windows_TestCase extends SequentialEDTSafeTestCase {
  TestWindow window;
  Windows windows;

  @Override
  protected final void onSetUp() {
    windows = new Windows();
    window = TestWindow.createNewWindow(getClass());
  }

  @Override
  protected final void onTearDown() {
    window.destroy();
  }

  final WindowStateAssert windowState() {
    return new WindowStateAssert(windows, window);
  }
}
