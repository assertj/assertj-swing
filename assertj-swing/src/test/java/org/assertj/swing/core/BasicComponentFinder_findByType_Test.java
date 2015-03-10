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

import javax.swing.JButton;
import javax.swing.JTree;

import org.assertj.swing.exception.ComponentLookupException;
import org.junit.Test;

/**
 * Tests for {@link BasicComponentFinder#findByType(Class)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicComponentFinder_findByType_Test extends BasicComponentFinder_TestCase {
  @Test
  public void should_Find_Component() {
    JButton button = finder.findByType(JButton.class);
    assertThat(button).isSameAs(window.button);
  }

  @Test
  public void should_Throw_Error_If_Component_Not_Found() {
    thrown.expect(ComponentLookupException.class, "type=javax.swing.JTree");
    finder.findByType(JTree.class);
  }
}
