/*
 * Created on Sep 26, 2007
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseButton.MIDDLE_BUTTON;
import static org.fest.swing.core.MouseButton.RIGHT_BUTTON;

import org.fest.swing.core.MouseClickInfo;
import org.junit.Test;

/**
 * Tests for {@link MouseClickInfo}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class MouseClickInfoTest {
  @Test(expected = NullPointerException.class)
  public void shouldThrowErrorIfMouseButtonIsNull() {
    MouseClickInfo.button(null);
  }

  @Test
  public void shouldCreateLeftButtonClickOneTime() {
    MouseClickInfo button = MouseClickInfo.leftButton();
    assertThat(button.button()).isEqualTo(LEFT_BUTTON);
    assertThat(button.times()).isEqualTo(1);
  }

  @Test
  public void shouldCreateMiddleButtonClickOneTime() {
    MouseClickInfo button = MouseClickInfo.middleButton();
    assertThat(button.button()).isEqualTo(MIDDLE_BUTTON);
    assertThat(button.times()).isEqualTo(1);
  }

  @Test
  public void shouldCreateRightButtonClickOneTime() {
    MouseClickInfo button = MouseClickInfo.rightButton();
    assertThat(button.button()).isEqualTo(RIGHT_BUTTON);
    assertThat(button.times()).isEqualTo(1);
  }

  @Test
  public void shouldIncludeButtonAndTimesPressedInToString() {
    MouseClickInfo button = MouseClickInfo.rightButton();
    assertThat(button.toString()).contains("button=RIGHT_BUTTON").contains("times=1");
  }
}
