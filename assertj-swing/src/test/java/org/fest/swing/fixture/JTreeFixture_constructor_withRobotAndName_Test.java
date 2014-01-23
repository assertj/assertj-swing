/*
 * Created on Nov 18, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.swing.TreeNodeFactory.node;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.fest.swing.test.swing.TestTree;
import org.fest.swing.test.swing.TestWindow;
import org.junit.Test;

/**
 * Tests for {@link JTreeFixture#JTreeFixture(org.fest.swing.core.Robot, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  private MyWindow window;

  @Override
  protected void onSetUp() {
    window = MyWindow.createNew();
  }

  @Test
  public void should_lookup_showing_JTree_by_name() {
    robot.showWindow(window);
    JTreeFixture fixture = new JTreeFixture(robot, "tree");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(window.tree);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_JTree_with_matching_name_is_not_showing() {
    new JTreeFixture(robot, "tree");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_a_JTree_with_matching_name_is_not_found() {
    new JTreeFixture(robot, "other");
  }

  private static class MyWindow extends TestWindow {
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        @Override
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    final TestTree tree = new TestTree(nodes());

    private static TreeModel nodes() {
      MutableTreeNode root = node("root",
          node("branch1", node("branch1.1", node("branch1.1.1"), node("branch1.1.2")), node("branch1.2")));
      return new DefaultTreeModel(root);
    }

    private MyWindow() {
      super(JTreeFixture_constructor_withRobotAndName_Test.class);
      tree.setName("tree");
      add(decorate(tree));
    }

    @RunsInCurrentThread
    private static Component decorate(JTree tree) {
      JScrollPane scrollPane = new JScrollPane(tree);
      scrollPane.setPreferredSize(new Dimension(200, 100));
      return scrollPane;
    }
  }
}
