/*
 * Created on Jul 31, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.when;

import java.awt.Window;
import java.util.Collection;

import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link Context#rootWindows()}.
 *
 * @author Alex Ruiz
 */
public class Context_rootWindows_Test extends Context_TestCase {
  @Test
  public void should_return_root_windows() {
    TestWindow anotherFrame = TestWindow.createNewWindow(getClass());
    when(windowEventQueueMapping.windows()).thenReturn(newArrayList((Window) window));
    Collection<Window> rootWindows = context.rootWindows();
    assertThat(rootWindows).contains(window);
    assertThat(rootWindows).contains(anotherFrame);
  }
}
