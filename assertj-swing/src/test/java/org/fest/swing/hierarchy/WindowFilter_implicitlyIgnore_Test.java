/*
 * Created on Nov 1, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;

import java.awt.Component;

import org.junit.Test;

/**
 * Tests for {@link WindowFilter#implicitlyIgnore(Component)}.
 * 
 * @author Alex Ruiz
 */
public class WindowFilter_implicitlyIgnore_Test extends WindowFilter_TestCase {
  @Test
  public void should_implicit_ignore() {
    Component c = button().createNew();
    filter.implicitlyIgnore(c);
    assertThat(allImplicityIgnored()).containsOnly(c);
  }
}
