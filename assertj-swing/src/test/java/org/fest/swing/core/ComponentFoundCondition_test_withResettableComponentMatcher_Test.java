/*
 * Created on Feb 4, 2010
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
package org.fest.swing.core;

import static org.fest.swing.test.awt.TestComponents.singletonComponentMock;
import static org.fest.swing.test.awt.TestContainers.singletonContainerMock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Container;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link ComponentFoundCondition#test()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ComponentFoundCondition_test_withResettableComponentMatcher_Test extends EDTSafeTestCase {
  private ComponentFinder finder;
  private ResettableComponentMatcher matcher;
  private static Container root;

  private ComponentFoundCondition condition;

  @BeforeClass
  public static void setUpOnce() {
    root = singletonContainerMock();
  }

  @Before
  public void setUp() {
    finder = mock(ComponentFinder.class);
    matcher = mock(ResettableComponentMatcher.class);
    condition = new ComponentFoundCondition("", finder, matcher, root);
  }

  @Test
  public void should_reset_matcher_when_match_not_found() {
    when(finder.find(root, matcher)).thenThrow(new ComponentLookupException("Thrown on purpose"));
    condition.test();
    verify(matcher).reset(false);
  }

  @Test
  public void should_reset_matcher_when_match_found() {
    when(finder.find(root, matcher)).thenReturn(singletonComponentMock());
    condition.test();
    verify(matcher).reset(true);
  }
}
