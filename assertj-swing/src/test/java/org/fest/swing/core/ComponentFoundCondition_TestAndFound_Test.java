/*
 * Created on Nov 15, 2007
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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;
import static org.fest.swing.test.swing.TestWindow.createNewWindow;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTextField;

import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link ComponentFoundCondition#test()} and {@link ComponentFoundCondition#found()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ComponentFoundCondition_TestAndFound_Test extends RobotBasedTestCase {
  private TypeMatcher matcher;
  private TestWindow toFind;

  private ComponentFoundCondition condition;

  @Override
  protected void onSetUp() {
    matcher = new TypeMatcher();
    condition = new ComponentFoundCondition("", finderWithNewAwtHierarchy(), matcher);
    toFind = createNewWindow(getClass());
  }

  @Test
  public void should_return_true_in_test_and_reference_found_Component() {
    matcher.typeToMatch(TestWindow.class);
    assertThat(condition.test()).isTrue();
    assertThat(condition.found()).isSameAs(toFind);
  }

  @Test
  public void should_return_false_if_Component_not_found() {
    matcher.typeToMatch(JTextField.class);
    assertThat(condition.test()).isFalse();
    assertThat(condition.found()).isNull();
  }

  private static class TypeMatcher implements ComponentMatcher {
    private Class<? extends Component> type;

    TypeMatcher() {}

    void typeToMatch(@Nonnull Class<? extends Component> newType) {
      this.type = newType;
    }

    @Override
    public boolean matches(@Nullable Component c) {
      return c != null && type.isAssignableFrom(c.getClass());
    }
  }
}
