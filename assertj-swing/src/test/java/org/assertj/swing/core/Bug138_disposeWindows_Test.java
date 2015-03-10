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
package org.assertj.swing.core;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.core.TestComponentHierarchies.newComponentHierarchyMock;
import static org.assertj.swing.test.builder.JFrames.frame;
import static org.assertj.swing.test.task.WindowDestroyTask.hideAndDisposeInEDT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.awt.Container;
import java.util.List;

import javax.swing.JFrame;

import org.assertj.swing.hierarchy.ComponentHierarchy;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=138">Bug 138</a>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Bug138_disposeWindows_Test extends EDTSafeTestCase {
  private ComponentHierarchy hierarchy;
  private BasicRobot robot;
  private JFrame frame;

  @Before
  public void setUp() {
    hierarchy = newComponentHierarchyMock();
    robot = new TestRobotFixture(hierarchy);
  }

  @After
  public void tearDown() {
    if (frame != null) {
      hideAndDisposeInEDT(frame);
    }
  }

  @Test
  public void should_Dispose_Windows() {
    frame = frame().withTitle("Hello").createNew();
    List<Container> roots = newArrayList((Container) frame);
    when(hierarchy.roots()).thenReturn(roots);
    robot.cleanUp();
    verify(hierarchy).dispose(frame);
  }

  @Test
  public void should_Not_Dispose_Windows() {
    robot.cleanUpWithoutDisposingWindows();
    verifyZeroInteractions(hierarchy);
  }
}
