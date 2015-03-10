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

import org.assertj.swing.hierarchy.SingleComponentHierarchy;
import org.junit.Test;

/**
 * Tests for {@link SingleComponentHierarchy#roots()}.
 * 
 * @author Alex Ruiz
 */
public class SingleComponentHierarchy_roots_Test extends SingleComponentHierarchy_TestCase {
  @Test
  public void should_Return_Collection_Of_Roots_That_Contains_Single_Hierarchy_Root() {
    assertThat(hierarchy.roots()).containsOnly(root);
  }
}
