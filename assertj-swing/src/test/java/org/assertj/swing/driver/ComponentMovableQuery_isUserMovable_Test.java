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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JButtons.button;
import static org.assertj.swing.test.builder.JDialogs.dialog;
import static org.assertj.swing.test.builder.JFrames.frame;
import static org.assertj.swing.test.builder.JInternalFrames.internalFrame;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link ComponentMovableQuery#isUserMovable(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class ComponentMovableQuery_isUserMovable_Test extends EDTSafeTestCase {
  @Test
  public void should_Return_True_If_Component_Is_Frame() {
    assertThat(ComponentMovableQuery.isUserMovable(frame().createNew())).isTrue();
  }

  @Test
  public void should_Return_True_If_Component_Is_Dialog() {
    assertThat(ComponentMovableQuery.isUserMovable(dialog().createNew())).isTrue();
  }

  @Test
  public void should_Return_True_If_Component_Is_JInternalFrame() {
    assertThat(ComponentMovableQuery.isUserMovable(internalFrame().createNew())).isTrue();
  }

  @Test
  public void should_Return_False_If_Component_Is_Not_Window() {
    assertThat(ComponentMovableQuery.isUserMovable(button().createNew())).isFalse();
  }
}
