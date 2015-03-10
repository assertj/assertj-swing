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
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.hierarchy.SingleComponentHierarchy;
import org.junit.After;
import org.junit.Test;

/**
 * Tests for {@link SingleComponentHierarchy#childrenOf(Component)}.
 * 
 * @author Alex Ruiz
 */
public class SingleComponentHierarchy_childrenOf_Test extends SingleComponentHierarchy_TestCase {
  private FrameWithButton parent;

  @Override
  void onSetUp() {
    parent = FrameWithButton.createNew();
  }

  @After
  public void tearDown() {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        parent.setVisible(false);
        parent.dispose();
      }
    });
  }

  @Test
  public void should_Return_Children_Of_Component() {
    List<Component> children = newArrayList((Component) parent.button);
    when(hierarchyDelegate.childrenOf(parent)).thenReturn(children);
    Collection<Component> foundChildren = hierarchy.childrenOf(parent);
    assertThat(foundChildren).isSameAs(children);
  }

  private static class FrameWithButton extends JFrame {
    final JButton button = new JButton();

    static FrameWithButton createNew() {
      return execute(new GuiQuery<FrameWithButton>() {
        @Override
        protected FrameWithButton executeInEDT() {
          return new FrameWithButton();
        }
      });
    }

    private FrameWithButton() {
      add(button);
    }
  }
}
