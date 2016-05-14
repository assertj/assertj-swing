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

import static org.assertj.swing.driver.JTreeDriver_TestCase.MyWindow.decorate;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.swing.TreeNodeFactory.node;

import javax.swing.tree.DefaultTreeModel;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.swing.TestTree;

/**
 * Base test case related to drag and drop in a {@code JTree} using {@link JTreeDriver}.
 *
 * @author Alex Ruiz
 */
public abstract class JTreeDriver_dragAndDrop_TestCase extends JTreeDriver_TestCase {
  TestTree dropTree;

  @RunsInEDT
  @Override
  final void extraSetUp() {
    execute(() -> {
      dropTree = new TestTree(new DefaultTreeModel(node("root")));
      window.add(decorate(dropTree));
    });
  }
}
