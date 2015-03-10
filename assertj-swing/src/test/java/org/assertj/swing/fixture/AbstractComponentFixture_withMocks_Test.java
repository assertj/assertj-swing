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
package org.assertj.swing.fixture;

import static java.awt.Color.BLUE;
import static java.awt.event.KeyEvent.VK_C;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.KeyPressInfo.keyCode;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import static org.assertj.swing.core.MouseClickInfo.leftButton;
import static org.assertj.swing.timing.Timeout.timeout;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Font;

import javax.annotation.Nonnull;

import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.MouseClickInfo;
import org.assertj.swing.core.Robot;
import org.assertj.swing.driver.ComponentDriver;
import org.assertj.swing.timing.Timeout;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link AbstractComponentFixture}.
 * 
 * @author Alex Ruiz
 */
public class AbstractComponentFixture_withMocks_Test {
  private ComponentFixture fixture;

  @Before
  public void setUp() {
    fixture = new ComponentFixture();
  }

  @Test
  public void should_Call_Click_In_Driver_And_Return_Self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(fixture.driver()).click(fixture.target());
  }

  @Test
  public void should_Call_Click_With_MouseButton_In_Driver_And_Return_Self() {
    assertThat(fixture.click(LEFT_BUTTON)).isSameAs(fixture);
    verify(fixture.driver()).click(fixture.target(), LEFT_BUTTON);
  }

  @Test
  public void should_Call_Click_With_MouseClickInfo_In_Driver_And_Return_Self() {
    MouseClickInfo clickInfo = leftButton().times(2);
    assertThat(fixture.click(clickInfo)).isSameAs(fixture);
    verify(fixture.driver()).click(fixture.target(), clickInfo);
  }

  @Test
  public void should_Call_DoubleClick_In_Driver_And_Return_Self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(fixture.driver()).doubleClick(fixture.target());
  }

  @Test
  public void should_Call_RightClick_In_Driver_And_Return_Self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(fixture.driver()).rightClick(fixture.target());
  }

  @Test
  public void should_Call_Focus_In_Driver_And_Return_Self() {
    assertThat(fixture.focus()).isSameAs(fixture);
    verify(fixture.driver()).focus(fixture.target());
  }

  @Test
  public void should_Call_PressAndReleaseKey_In_Driver_And_Return_Self() {
    KeyPressInfo info = keyCode(VK_C);
    assertThat(fixture.pressAndReleaseKey(info)).isSameAs(fixture);
    verify(fixture.driver()).pressAndReleaseKey(fixture.target(), info);
  }

  @Test
  public void should_Call_PressAndReleaseKeys_In_Driver_And_Return_Self() {
    int[] keyCodes = { VK_C };
    assertThat(fixture.pressAndReleaseKeys(keyCodes)).isSameAs(fixture);
    verify(fixture.driver()).pressAndReleaseKeys(fixture.target(), keyCodes);
  }

  @Test
  public void should_Call_PressKey_In_Driver_And_Return_Self() {
    assertThat(fixture.pressKey(VK_C)).isSameAs(fixture);
    verify(fixture.driver()).pressKey(fixture.target(), VK_C);
  }

  @Test
  public void should_Call_ReleaseKey_In_Driver_And_Return_Self() {
    assertThat(fixture.releaseKey(VK_C)).isSameAs(fixture);
    verify(fixture.driver()).releaseKey(fixture.target(), VK_C);
  }

  @Test
  public void should_Call_RequireFocused_In_Driver_And_Return_Self() {
    assertThat(fixture.requireFocused()).isSameAs(fixture);
    verify(fixture.driver()).requireFocused(fixture.target());
  }

  @Test
  public void should_Call_RequireEnabled_In_Driver_And_Return_Self() {
    assertThat(fixture.requireEnabled()).isSameAs(fixture);
    verify(fixture.driver()).requireEnabled(fixture.target());
  }

  @Test
  public void should_Call_RequireEnabled_With_Timeout_In_Driver_And_Return_Self() {
    Timeout timeout = timeout(100);
    assertThat(fixture.requireEnabled(timeout)).isSameAs(fixture);
    verify(fixture.driver()).requireEnabled(fixture.target(), timeout);
  }

  @Test
  public void should_Call_RequireDisabled_In_Driver_And_Return_Self() {
    assertThat(fixture.requireDisabled()).isSameAs(fixture);
    verify(fixture.driver()).requireDisabled(fixture.target());
  }

  @Test
  public void should_Call_RequireVisible_In_Driver_And_Return_Self() {
    assertThat(fixture.requireVisible()).isSameAs(fixture);
    verify(fixture.driver()).requireVisible(fixture.target());
  }

  @Test
  public void should_Call_RequireNotVisible_In_Driver_And_Return_Self() {
    assertThat(fixture.requireNotVisible()).isSameAs(fixture);
    verify(fixture.driver()).requireNotVisible(fixture.target());
  }

  @Test
  public void should_Delegate_To_FontOf_In_Driver() {
    Font font = mock(Font.class);
    ComponentDriver driver = fixture.driver();
    Component component = fixture.target();
    when(driver.fontOf(component)).thenReturn(font);
    assertThat(fixture.font().target()).isSameAs(font);
    verify(driver).fontOf(component);
  }

  @Test
  public void should_Delegate_To_BackgroundOf_In_Driver() {
    ComponentDriver driver = fixture.driver();
    Component component = fixture.target();
    when(driver.backgroundOf(component)).thenReturn(BLUE);
    assertThat(fixture.background().target()).isSameAs(BLUE);
    verify(driver).backgroundOf(component);
  }

  @Test
  public void should_Delegate_To_ForegroundOf_In_Driver() {
    ComponentDriver driver = fixture.driver();
    Component component = fixture.target();
    when(driver.foregroundOf(component)).thenReturn(BLUE);
    assertThat(fixture.foreground().target()).isSameAs(BLUE);
    verify(driver).foregroundOf(component);
  }

  private static class ComponentFixture extends AbstractComponentFixture<ComponentFixture, Component, ComponentDriver> {
    ComponentFixture() {
      super(ComponentFixture.class, mock(Robot.class), mock(Component.class));
    }

    @Override
    protected @Nonnull ComponentDriver createDriver(@Nonnull Robot robot) {
      return mock(ComponentDriver.class);
    }
  }
}
