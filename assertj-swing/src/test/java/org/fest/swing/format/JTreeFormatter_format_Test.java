/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import static javax.swing.tree.TreeSelectionModel.CONTIGUOUS_TREE_SELECTION;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.task.JTreeSelectRowTask.selectRow;
import static org.fest.swing.test.task.JTreeSetSelectionModelTask.setSelectionModel;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.test.builder.JTrees;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Base test case for {@link JTreeFormatter#format(java.awt.Component)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFormatter_format_Test extends EDTSafeTestCase {
  private JTree tree;
  private JTreeFormatter formatter;

  @Before
  public void setUp() {
    tree = JTrees.tree().withName("tree").withValues("One", "Two", "Three").createNew();
    formatter = new JTreeFormatter();
  }

  @Test
  public void should_format_JTree() {
    setContiguousSelectionMode();
    selectRow(tree, 1);
    String formatted = formatter.format(tree);
    assertThat(formatted).contains("javax.swing.JTree")
                        .contains("name='tree'")
                        .contains("selectionCount=1")
                        .contains("selectionPaths=['[root, Two]']")
                        .contains("selectionMode=CONTIGUOUS_TREE_SELECTION")
                        .contains("enabled=true")
                        .contains("visible=true")
                        .contains("showing=false");
  }

  @RunsInEDT
  private void setContiguousSelectionMode() {
    TreeSelectionModel selectionModel = new DefaultTreeSelectionModel();
    selectionModel.setSelectionMode(CONTIGUOUS_TREE_SELECTION);
    setSelectionModel(tree, selectionModel);
  }

  @Test
  public void should_format_JTree_without_TreeSelectionModel() {
    setSelectionModel(tree, null);
    String formatted = formatter.format(tree);
    assertThat(formatted).contains("javax.swing.JTree")
                         .contains("name='tree'")
                         .contains("selectionCount=0")
                         .contains("selectionPaths=[]")
                         .contains("selectionMode=DISCONTIGUOUS_TREE_SELECTION")
                         .contains("enabled=true")
                         .contains("visible=true")
                         .contains("showing=false");
  }
}
