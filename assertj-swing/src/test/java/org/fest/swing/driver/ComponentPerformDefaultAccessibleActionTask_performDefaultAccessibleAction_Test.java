/*
 * Created on Feb 23, 2008
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
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.Locale;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleAction;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.accessibility.AccessibleStateSet;
import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentPerformDefaultAccessibleActionTask#performDefaultAccessibleAction(Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ComponentPerformDefaultAccessibleActionTask_performDefaultAccessibleAction_Test extends RobotBasedTestCase {
  private AccessibleAction accessibleAction;
  private AccessibleContextStub accessibleContext;
  private Component component;

  @Override
  protected void onSetUp() {
    accessibleAction = mock(AccessibleAction.class);
    accessibleContext = new AccessibleContextStub(accessibleAction);
    MyWindow window = MyWindow.createNew(accessibleContext);
    component = window.component;
  }

  @Test
  public void should_execute_first_Action_in_AccessibleAction() {
    when(accessibleAction.getAccessibleActionCount()).thenReturn(1);
    when(accessibleAction.doAccessibleAction(0)).thenReturn(true);
    ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction(component);
    robot.waitForIdle();
  }

  @Test
  public void should_throw_error_if_AccessibleAction_is_null() {
    accessibleContext.accessibleAction(null);
    try {
      ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction(component);
      robot.waitForIdle();
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailedThrown(e);
    }
    verifyZeroInteractions(accessibleAction);
  }

  @Test
  public void should_throw_error_if_AccessibleAction_is_empty() {
    when(accessibleAction.getAccessibleActionCount()).thenReturn(0);
    try {
      ComponentPerformDefaultAccessibleActionTask.performDefaultAccessibleAction(component);
      robot.waitForIdle();
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertActionFailedThrown(e);
    }
  }

  private void assertActionFailedThrown(ActionFailedException e) {
    assertThat(e.getMessage()).contains("Unable to perform accessible action for");
  }

  private static class MyWindow extends TestWindow {
    final MyComponent component;

    @RunsInEDT
    static MyWindow createNew(final AccessibleContext accessibleContext) {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow(accessibleContext);
        }
      });
    }

    private MyWindow(AccessibleContext accessibleContext) {
      super(ComponentPerformDefaultAccessibleActionTask_performDefaultAccessibleAction_Test.class);
      component = new MyComponent(accessibleContext);
      addComponents(component);
    }
  }

  private static class MyComponent extends JTextField {
    private final AccessibleContext accessibleContext;

    MyComponent(AccessibleContext accessibleContext) {
      super(20);
      this.accessibleContext = accessibleContext;
    }

    @Override
    public AccessibleContext getAccessibleContext() {
      return accessibleContext;
    }
  }

  private static class AccessibleContextStub extends AccessibleContext {
    private AccessibleAction accessibleAction;

    AccessibleContextStub(AccessibleAction newAccessibleAction) {
      accessibleAction(newAccessibleAction);
    }

    void accessibleAction(AccessibleAction newAccessibleAction) {
      this.accessibleAction = newAccessibleAction;
    }

    @Override
    public AccessibleAction getAccessibleAction() {
      return accessibleAction;
    }

    @Override
    public Accessible getAccessibleChild(int i) {
      return null;
    }

    @Override
    public int getAccessibleChildrenCount() {
      return 0;
    }

    @Override
    public int getAccessibleIndexInParent() {
      return 0;
    }

    @Override
    public AccessibleRole getAccessibleRole() {
      return null;
    }

    @Override
    public AccessibleStateSet getAccessibleStateSet() {
      return null;
    }

    @Override
    public Locale getLocale() {
      return null;
    }
  }
}
