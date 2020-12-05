/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.swing.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.util.ComponentNamer.namer;

import java.awt.*;

import javax.swing.*;

import org.assertj.swing.fixture.ContainerFixture;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests and demonstrates application of {@link ComponentNamer#namer(Container)}
 *
 * @author Beirti O'Nunain
 */
public class ComponentNamerTest extends RobotBasedTestCase {
  @Rule
  public  ExpectedException thrown = none();

  private SimpleWindow      simpleWindow;
  private ContainerFixture simpleFixture;

  private NestedWindow nestedWindow;
  private ContainerFixture nestedFixture;

  private SimpleWindowAlreadyNamed simpleAlreadyNamedWindow;
  private ContainerFixture simpleAlreadyNamedFixture;

  private SimpleWindowAnonymousField simpleAnonymousFieldWindow;
  private ContainerFixture simpleAnonymousFieldFixture;

 private  ParentReferenceWindow parentReferenceWindow;
  private ContainerFixture      parentReferenceFixture;

  @Override
  protected final void onSetUp() {
    simpleWindow = SimpleWindow.createNew(getClass());
    simpleFixture = new ContainerFixture(robot, simpleWindow);

    nestedWindow = NestedWindow.createNew(getClass());
    nestedFixture = new ContainerFixture(robot, nestedWindow);

    simpleAlreadyNamedWindow = SimpleWindowAlreadyNamed.createNew(getClass());
    simpleAlreadyNamedFixture = new ContainerFixture(robot, simpleAlreadyNamedWindow);

    simpleAnonymousFieldWindow = SimpleWindowAnonymousField.createNew(getClass());
    simpleAnonymousFieldFixture = new ContainerFixture(robot, simpleAnonymousFieldWindow);

    parentReferenceWindow  = ParentReferenceWindow.createNew(getClass());
    parentReferenceFixture = new ContainerFixture(robot, parentReferenceWindow);
  }

  @Test
  public void should_name_field_with_declared_name() {
    robot.showWindow(simpleWindow);
    namer(simpleWindow).setMissingNames();
    assertThat(simpleFixture.textBox("specialText").target()).isSameAs(simpleWindow.specialText);
  }

  @Test
  public void should_name_field_with_declared_name_on_nested_window() {
    robot.showWindow(nestedWindow);
    namer(nestedWindow).setMissingNames();
    assertThat(nestedFixture.textBox("specialText").target()).isSameAs(nestedWindow.specialText);
  }

  @Test
  public void should_not_rename_already_named_field() {
    robot.showWindow(simpleAlreadyNamedWindow);
    namer(simpleAlreadyNamedWindow).setMissingNames();
    assertThat(simpleAlreadyNamedFixture.textBox("notReallyThatSpecial").target()).isSameAs(simpleAlreadyNamedWindow.specialText);
  }

  @Test
  public void should_name_anonymous_field_with_generated_name() {
    robot.showWindow(simpleAnonymousFieldWindow);
    namer(simpleAnonymousFieldWindow).setMissingNames();
    simpleAnonymousFieldFixture.textBox("JTextField-2").requireText("b");
    simpleAnonymousFieldFixture.textBox("JTextField-1").requireText("a");
  }

  @Test
  public void should_throw_IllegalArgumentException_if_attempting_to_name_null() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessageToContain("Container cannot be null");
    namer(null);
  }

  @Test
  public void should_handle_circular_reference() {
    robot.showWindow(parentReferenceWindow);
    namer(parentReferenceWindow).setMissingNames();
    parentReferenceFixture.textBox("JTextField-1").requireText("child");
  }

  @Test
  public void multiple_calls_are_idempotent() {
    robot.showWindow(simpleAnonymousFieldWindow);
    // First naming attempt
    ComponentNamer namer = namer(simpleAnonymousFieldWindow);
    namer.setMissingNames();
    simpleAnonymousFieldFixture.textBox("JTextField-2").requireText("b");
    simpleAnonymousFieldFixture.textBox("JTextField-1").requireText("a");

    // Second naming attempt
    namer.setMissingNames();
    simpleAnonymousFieldFixture.textBox("JTextField-2").requireText("b");
    simpleAnonymousFieldFixture.textBox("JTextField-1").requireText("a");
  }

  private static class SimpleWindow extends TestWindow {
    final JTextField specialText = new JTextField();

    static SimpleWindow createNew(final Class<?> testClass) {
      return execute(() -> new SimpleWindow(testClass));
    }

    private SimpleWindow(Class<?> testClass) {
      super(testClass);
      addComponents(specialText);
    }
  }

  private static class NestedWindow extends TestWindow {
    final JTextField specialText = new JTextField();

    static NestedWindow createNew(final Class<?> testClass) {
      return execute(() -> new NestedWindow(testClass));
    }

    private NestedWindow(Class<?> testClass) {
      super(testClass);
      JPanel nestedPanel = new JPanel();
      nestedPanel.add(specialText);
      addComponents(nestedPanel);
    }
  }

  private static class SimpleWindowAlreadyNamed extends TestWindow {
    final JTextField specialText = new JTextField();

    static SimpleWindowAlreadyNamed createNew(final Class<?> testClass) {
      return execute(() -> new SimpleWindowAlreadyNamed(testClass));
    }

    private SimpleWindowAlreadyNamed(Class<?> testClass) {
      super(testClass);
      specialText.setName("notReallyThatSpecial");
      addComponents(specialText);
    }
  }

  private static class SimpleWindowAnonymousField extends TestWindow {
    static SimpleWindowAnonymousField createNew(final Class<?> testClass) {
      return execute(() -> new SimpleWindowAnonymousField(testClass));
    }

    private SimpleWindowAnonymousField(Class<?> testClass) {
      super(testClass);
      addComponents(new JTextField("a"), new JTextField("b"));
    }
  }

  private static class ParentReferenceWindow extends TestWindow {
    static ParentReferenceWindow createNew(final Class<?> testClass) {
      return execute(() -> new ParentReferenceWindow(testClass));
    }

    private ChildReferenceWindow child;

    private ParentReferenceWindow(Class<?> testClass) {
      super(testClass);
      child = new ChildReferenceWindow(this);
      addComponents(child);
    }
  }

  private static class ChildReferenceWindow extends JPanel {
    private final ParentReferenceWindow parentWindow;

    private ChildReferenceWindow(ParentReferenceWindow parentWindow) {
      this.parentWindow = parentWindow;
      add(new JTextField("child"));
    }
  }
}
