/*
 * Created on Feb 17, 2013
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
 * Copyright @2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static java.awt.Color.BLUE;
import static java.awt.event.KeyEvent.VK_C;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.KeyPressInfo.keyCode;
import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.MouseClickInfo.leftButton;
import static org.fest.swing.timing.Timeout.timeout;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Font;

import javax.annotation.Nonnull;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.timing.Timeout;
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
  public void should_call_click_in_driver_and_return_self() {
    assertThat(fixture.click()).isSameAs(fixture);
    verify(fixture.driver()).click(fixture.target());
  }

  @Test
  public void should_call_click_with_MouseButton_in_driver_and_return_self() {
    assertThat(fixture.click(LEFT_BUTTON)).isSameAs(fixture);
    verify(fixture.driver()).click(fixture.target(), LEFT_BUTTON);
  }

  @Test
  public void should_call_click_with_MouseClickInfo_in_driver_and_return_self() {
    MouseClickInfo clickInfo = leftButton().times(2);
    assertThat(fixture.click(clickInfo)).isSameAs(fixture);
    verify(fixture.driver()).click(fixture.target(), clickInfo);
  }

  @Test
  public void should_call_doubleClick_in_driver_and_return_self() {
    assertThat(fixture.doubleClick()).isSameAs(fixture);
    verify(fixture.driver()).doubleClick(fixture.target());
  }

  @Test
  public void should_call_rightClick_in_driver_and_return_self() {
    assertThat(fixture.rightClick()).isSameAs(fixture);
    verify(fixture.driver()).rightClick(fixture.target());
  }

  @Test
  public void should_call_focus_in_driver_and_return_self() {
    assertThat(fixture.focus()).isSameAs(fixture);
    verify(fixture.driver()).focus(fixture.target());
  }

  @Test
  public void should_call_pressAndReleaseKey_in_driver_and_return_self() {
    KeyPressInfo info = keyCode(VK_C);
    assertThat(fixture.pressAndReleaseKey(info)).isSameAs(fixture);
    verify(fixture.driver()).pressAndReleaseKey(fixture.target(), info);
  }

  @Test
  public void should_call_pressAndReleaseKeys_in_driver_and_return_self() {
    int[] keyCodes = { VK_C };
    assertThat(fixture.pressAndReleaseKeys(keyCodes)).isSameAs(fixture);
    verify(fixture.driver()).pressAndReleaseKeys(fixture.target(), keyCodes);
  }

  @Test
  public void should_call_pressKey_in_driver_and_return_self() {
    assertThat(fixture.pressKey(VK_C)).isSameAs(fixture);
    verify(fixture.driver()).pressKey(fixture.target(), VK_C);
  }

  @Test
  public void should_call_releaseKey_in_driver_and_return_self() {
    assertThat(fixture.releaseKey(VK_C)).isSameAs(fixture);
    verify(fixture.driver()).releaseKey(fixture.target(), VK_C);
  }

  @Test
  public void should_call_requireFocused_in_driver_and_return_self() {
    assertThat(fixture.requireFocused()).isSameAs(fixture);
    verify(fixture.driver()).requireFocused(fixture.target());
  }

  @Test
  public void should_call_requireEnabled_in_driver_and_return_self() {
    assertThat(fixture.requireEnabled()).isSameAs(fixture);
    verify(fixture.driver()).requireEnabled(fixture.target());
  }

  @Test
  public void should_call_requireEnabled_with_Timeout_in_driver_and_return_self() {
    Timeout timeout = timeout(100);
    assertThat(fixture.requireEnabled(timeout)).isSameAs(fixture);
    verify(fixture.driver()).requireEnabled(fixture.target(), timeout);
  }

  @Test
  public void should_call_requireDisabled_in_driver_and_return_self() {
    assertThat(fixture.requireDisabled()).isSameAs(fixture);
    verify(fixture.driver()).requireDisabled(fixture.target());
  }

  @Test
  public void should_call_requireVisible_in_driver_and_return_self() {
    assertThat(fixture.requireVisible()).isSameAs(fixture);
    verify(fixture.driver()).requireVisible(fixture.target());
  }

  @Test
  public void should_call_requireNotVisible_in_driver_and_return_self() {
    assertThat(fixture.requireNotVisible()).isSameAs(fixture);
    verify(fixture.driver()).requireNotVisible(fixture.target());
  }

  @Test
  public void should_delegate_to_fontOf_in_driver() {
    Font font = mock(Font.class);
    ComponentDriver driver = fixture.driver();
    Component component = fixture.target();
    when(driver.fontOf(component)).thenReturn(font);
    assertThat(fixture.font().target()).isSameAs(font);
    verify(driver).fontOf(component);
  }

  @Test
  public void should_delegate_to_backgroundOf_in_driver() {
    ComponentDriver driver = fixture.driver();
    Component component = fixture.target();
    when(driver.backgroundOf(component)).thenReturn(BLUE);
    assertThat(fixture.background().target()).isSameAs(BLUE);
    verify(driver).backgroundOf(component);
  }

  @Test
  public void should_delegate_to_foregroundOf_in_driver() {
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
