/*
 * Created on Jul 16, 2009
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#drag(javax.swing.JTree, int)} and {@link JTreeDriver#drop(javax.swing.JTree, int)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_dragRow_dropRow_Test extends JTreeDriver_dragAndDrop_TestCase {
  @Test
  public void should_drag_and_drop() {
    showWindow();
    driver.drag(tree, 2);
    driver.drop(dropTree, 0);
    DefaultMutableTreeNode sourceRoot = rootOf(tree);
    assertThat(childCountOf(sourceRoot)).isEqualTo(4);
    DefaultMutableTreeNode targetRoot = rootOf(dropTree);
    assertThat(childCountOf(targetRoot)).isEqualTo(1);
    assertThat(textOf(firstChildOf(targetRoot))).isEqualTo("branch2");
  }
}
