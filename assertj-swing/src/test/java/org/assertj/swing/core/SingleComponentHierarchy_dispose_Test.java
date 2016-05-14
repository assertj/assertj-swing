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

import static org.assertj.swing.test.builder.JFrames.frame;
import static org.mockito.Mockito.verify;

import javax.swing.JFrame;

import org.junit.Test;

/**
 * Tests for {@link SingleComponentHierarchy#dispose(java.awt.Window)}.
 * 
 * @author Alex Ruiz
 */
public class SingleComponentHierarchy_dispose_Test extends SingleComponentHierarchy_TestCase {
  @Test
  public void should_Dispose_Window() {
    final JFrame window = frame().createNew();
    hierarchy.dispose(window);
    verify(hierarchyDelegate).dispose(window);
  }
}
