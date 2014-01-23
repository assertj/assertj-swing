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

import static org.fest.swing.test.awt.TestAppletContexts.singletonAppletContextMock;
import static org.fest.swing.test.awt.TestWindows.singletonWindowMock;

import java.applet.AppletContext;
import java.awt.Window;

import org.junit.Before;

/**
 * Tests for {@link BasicAppletStub}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class BasicAppletStub_TestCase {
  Window viewer;
  AppletContext context;
  BasicAppletStub stub;

  @Before
  public final void setUp() {
    viewer = singletonWindowMock();
    context = singletonAppletContextMock();
    stub = new BasicAppletStub(viewer, context);
  }
}
