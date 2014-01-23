/*
 * Created on Jul 12, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.applet;

import static org.mockito.Mockito.mock;

import org.junit.Before;

/**
 * Base class for tests for {@link BasicAppletContext}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class BasicAppletContext_TestCase {
  BasicAppletContext context;
  StatusDisplay statusDisplay;

  @Before
  public void setUp() {
    statusDisplay = mock(StatusDisplay.class);
    context = new BasicAppletContext(statusDisplay);
  }
}
