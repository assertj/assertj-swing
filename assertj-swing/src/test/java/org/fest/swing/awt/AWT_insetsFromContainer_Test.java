/*
 * Created on Jul 24, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.awt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.BasicRobot.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.query.ContainerInsetsQuery.insetsOf;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link AWT#insetsFrom(java.awt.Container)}.
 *
 * @author Alex Ruiz
 */
public class AWT_insetsFromContainer_Test {
  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

  @Test
  public void should_return_insets_from_container() {
    Robot robot = robotWithNewAwtHierarchy();
    TestWindow window = TestWindow.createNewWindow(getClass());
    try {
      robot.showWindow(window, new Dimension(500, 300));
      Insets insets = insetsFrom(window);
      assertThat(insets).isEqualTo(insetsOf(window));
    } finally {
      robot.cleanUp();
    }
  }

  @Test
  public void should_return_empty_insets_if_exception_thrown() {
    Insets insets = insetsFrom(null);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  @Test
  public void should_return_empty_insets_if_container_insets_is_null() {
    TestWindow window = WindowWithNullInsets.createNew();
    Insets insets = insetsFrom(window);
    assertThat(insets).isEqualTo(EMPTY_INSETS);
  }

  @RunsInEDT
  private static Insets insetsFrom(final Container c) {
    return execute(new GuiQuery<Insets>() {
      @Override
      protected Insets executeInEDT() {
        return AWT.insetsFrom(c);
      }
    });
  }

  private static class WindowWithNullInsets extends TestWindow {
    @RunsInEDT
    static WindowWithNullInsets createNew() {
      return execute(new GuiQuery<WindowWithNullInsets>() {
        @Override
        protected WindowWithNullInsets executeInEDT() {
          return new WindowWithNullInsets();
        }
      });
    }

    private WindowWithNullInsets() {
      super(AWT_insetsFromContainer_Test.class);
    }

    @Override
    public Insets getInsets() {
      return null;
    }
  }
}
