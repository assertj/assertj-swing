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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.util.Collection;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JTreeDriver#unselectPath(javax.swing.JTree, String)}.
 *
 * @author Christian Rösch
 */
@RunWith(Parameterized.class)
public class JTreeDriver_unselectPath_withManyPaths_Test extends JTreeDriver_selectCell_TestCase {
  private final String treePath;

  @Parameters
  public static Collection<Object[]> paths() {
    return newArrayList(new Object[][] { { "root/branch1" }, { "root/branch1/branch1.2" }, { "root" } });
  }

  public JTreeDriver_unselectPath_withManyPaths_Test(String treePath) {
    this.treePath = treePath;
  }

  @Test
  public void should_Unelect_Cell() {
    showWindow();
    clearTreeSelection();
    driver.selectPath(tree, treePath);
    requireThatPathIsSelected();
    driver.unselectPath(tree, treePath);
    requireNoSelection();
  }

  @RunsInEDT
  private void requireThatPathIsSelected() {
    assertThat(textOf(selectionPathOf(tree))).isEqualTo(treePath);
  }

  @RunsInEDT
  private static TreePath selectionPathOf(final JTree tree) {
    return execute(() -> tree.getSelectionPath());
  }
}
