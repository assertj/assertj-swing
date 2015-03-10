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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.BasicComponentFinder.finderWithNewAwtHierarchy;
import static org.assertj.swing.test.swing.TestWindow.createNewWindow;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTextField;

import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestWindow;
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
  public void should_Return_True_In_Test_And_Reference_Found_Component() {
    matcher.typeToMatch(TestWindow.class);
    assertThat(condition.test()).isTrue();
    assertThat(condition.found()).isSameAs(toFind);
  }

  @Test
  public void should_Return_False_If_Component_Not_Found() {
    matcher.typeToMatch(JTextField.class);
    assertThat(condition.test()).isFalse();
    assertThat(condition.found()).isNull();
  }

  private static class TypeMatcher implements ComponentMatcher {
    private Class<? extends Component> type;

    TypeMatcher() {
    }

    void typeToMatch(@Nonnull Class<? extends Component> newType) {
      this.type = newType;
    }

    @Override
    public boolean matches(@Nullable Component c) {
      return c != null && type.isAssignableFrom(c.getClass());
    }
  }
}
