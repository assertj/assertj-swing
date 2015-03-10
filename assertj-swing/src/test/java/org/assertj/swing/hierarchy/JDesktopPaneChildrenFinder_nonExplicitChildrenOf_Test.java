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
package org.assertj.swing.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.hierarchy.JInternalFrameIconifyTask.iconify;
import static org.assertj.swing.test.builder.JTextFields.textField;
import static org.assertj.swing.test.swing.TestMdiWindow.createAndShowNewWindow;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.TestMdiWindow;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JDesktopPaneChildrenFinder#nonExplicitChildrenOf(Container)}.
 * 
 * @author Alex Ruiz
 */
public class JDesktopPaneChildrenFinder_nonExplicitChildrenOf_Test extends EDTSafeTestCase {
  private JDesktopPaneChildrenFinder finder;

  @Before
  public void setUp() {
    finder = new JDesktopPaneChildrenFinder();
  }

  @Test
  public void should_Return_Empty_Collection_If_Component_Is_Not_JDesktopPane() {
    Container container = textField().createNew();
    assertThat(finder.nonExplicitChildrenOf(container)).isEmpty();
  }

  @Test
  public void should_Return_Empty_Collection_If_Component_Is_Null() {
    assertThat(finder.nonExplicitChildrenOf(null)).isEmpty();
  }

  @Test
  public void should_Return_Iconified_JInternalFrames_If_Component_Is_JDesktopPane() {
    ScreenLock.instance().acquire(this);
    final TestMdiWindow window = createAndShowNewWindow(getClass());
    iconify(window.internalFrame());
    Collection<Component> children = execute(new GuiQuery<Collection<Component>>() {
      @Override
      protected Collection<Component> executeInEDT() {
        return finder.nonExplicitChildrenOf(window.desktop());
      }
    });
    try {
      assertThat(children).containsOnly(window.internalFrame());
    } finally {
      try {
        window.destroy();
      } finally {
        ScreenLock.instance().release(this);
      }
    }
  }
}
