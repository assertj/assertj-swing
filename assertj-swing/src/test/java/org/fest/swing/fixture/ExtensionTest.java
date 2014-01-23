/*
 * Created on Jul 30, 2008
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.Container;

import javax.annotation.Nonnull;
import javax.swing.JTextField;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for issue <a href="http://code.google.com/p/fest/issues/detail?id=109" target="_blank">109</a>: Add support for
 * extension.
 *
 * @author Alex Ruiz
 */
public class ExtensionTest extends RobotBasedTestCase {
  private FrameFixture fixture;

  @Override
  protected void onSetUp() {
    fixture = new FrameFixture(robot, TestWindow.createNewWindow(ExtensionTest.class));
  }

  @Test
  public void shouldCreateFixtureUsingExtension() {
    JTextFieldFixture textField = fixture.with(JTextFieldFixtureExtension.textFieldWithName("hello"));
    assertThat(textField).isNotNull();
  }

  static class JTextFieldFixtureExtension extends ComponentFixtureExtension<JTextField, JTextFieldFixture> {
    final String name;

    JTextFieldFixtureExtension(String name) {
      this.name = name;
    }

    static JTextFieldFixtureExtension textFieldWithName(String name) {
      return new JTextFieldFixtureExtension(name);
    }

    @Override
    public JTextFieldFixture createFixture(@Nonnull Robot robot, @Nonnull Container root) {
      return new JTextFieldFixture(robot, textField().createNew());
    }
  }

  static class JTextFieldFixture extends AbstractComponentFixture<JTextFieldFixture, JTextField, JComponentDriver> {
    public JTextFieldFixture(Robot robot, JTextField target) {
      super(JTextFieldFixture.class, robot, target);
    }

    @Override
    protected @Nonnull JComponentDriver createDriver(@Nonnull Robot robot) {
      return new JComponentDriver(robot);
    }
  }
}
