/*
 * Created on Jul 17, 2008
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

import javax.swing.JTextField;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link WaitForComponentToShowCondition#test()}.
 * 
 * @author Yvonne Wang
 */
public class WaitForComponentToShowCondition_test_Test extends EDTSafeTestCase {
  private WaitForComponentToShowCondition condition;
  private ComponentStub c;

  @Before
  public void setUp() {
    c = ComponentStub.createNew();
    condition = WaitForComponentToShowCondition.untilIsShowing(c);
  }

  @Test
  public void should_return_true_if_Component_is_showing() {
    c.showing(true);
    assertThat(condition.test()).isTrue();
  }

  @Test
  public void should_return_false_if_Component_is_not_showing() {
    assertThat(condition.test()).isFalse();
  }

  private static class ComponentStub extends JTextField {
    private boolean showing;

    @RunsInEDT
    static ComponentStub createNew() {
      return execute(new GuiQuery<ComponentStub>() {
        @Override
        protected ComponentStub executeInEDT() {
          return new ComponentStub();
        }
      });
    }

    private ComponentStub() {
    }

    void showing(boolean isShowing) {
      this.showing = isShowing;
    }

    @Override
    public boolean isShowing() {
      return showing;
    }
  }
}
