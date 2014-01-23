/*
 * Created on Nov 12, 2007
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
package org.fest.swing.hierarchy;

import static org.mockito.Mockito.mock;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestDialog;
import org.fest.swing.test.swing.TestWindow;
import org.junit.After;
import org.junit.Before;

/**
 * Base test case for {@link TransientWindowListener}.
 *
 * @author Alex Ruiz
 */
public class TransientWindowListener_eventDispatched_TestCase extends EDTSafeTestCase {
  TransientWindowListener listener;
  WindowFilter windowFilter;
  TestDialog eventSource;
  TestWindow parent;

  @Before
  public final void setUp() {
    windowFilter = mock(WindowFilter.class);
    listener = new TransientWindowListener(windowFilter);
    parent = TestWindow.createNewWindow(getClass());
    eventSource = TestDialog.createNewDialog(parent);
    onSetUp();
  }

  void onSetUp() {}

  @After
  public final void tearDown() {
    try {
      eventSource.destroy();
    } finally {
      parent.destroy();
    }
  }
}
