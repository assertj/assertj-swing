/*
 * Created on Nov 6, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.builder.JInternalFrames.internalFrame;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link ComponentMovableQuery#isUserMovable(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentMovableQuery_isUserMovable_Test extends EDTSafeTestCase {
  @Test
  public void should_return_true_if_Component_is_Frame() {
    assertThat(ComponentMovableQuery.isUserMovable(frame().createNew())).isTrue();
  }

  @Test
  public void should_return_true_if_Component_is_Dialog() {
    assertThat(ComponentMovableQuery.isUserMovable(dialog().createNew())).isTrue();
  }

  @Test
  public void should_return_true_if_Component_is_JInternalFrame() {
    assertThat(ComponentMovableQuery.isUserMovable(internalFrame().createNew())).isTrue();
  }

  @Test
  public void should_return_false_if_Component_is_not_Window() {
    assertThat(ComponentMovableQuery.isUserMovable(button().createNew())).isFalse();
  }
}
