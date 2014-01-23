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
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Component;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.swing.JTextField;

import org.junit.Test;

/**
 * Tests for {@link BasicComponentFinder#findAll(ComponentMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicComponentFinder_findAllUsingComponentMatcher_Test extends BasicComponentFinder_TestCase {
  @Test
  public void should_return_all_matching_Components() {
    Collection<Component> found = finder.findAll(new ComponentMatcher() {
      @Override
      public boolean matches(@Nullable Component c) {
        return c instanceof JTextField;
      }
    });
    assertThat(found).containsOnly(window.textField1, window.textField2);
  }
}
