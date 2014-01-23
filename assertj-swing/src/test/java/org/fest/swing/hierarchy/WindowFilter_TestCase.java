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

import java.awt.Component;
import java.util.Set;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;

/**
 * Base test case for {@link WindowFilter}.
 * 
 * @author Alex Ruiz
 */
public abstract class WindowFilter_TestCase extends EDTSafeTestCase {
  WindowFilter filter;

  @Before
  public final void setUp() {
    filter = new WindowFilter(new ParentFinder(), new ChildrenFinder());
    filter.ignored.clear();
    filter.implicitlyIgnored.clear();
  }

  final void addToIgnoredMap(Component... components) {
    for (Component c : components) {
      filter.ignored.put(c, true);
    }
  }

  final void addToImplicitlyIgnoredMap(Component... components) {
    for (Component c : components) {
      filter.implicitlyIgnored.put(c, true);
    }
  }

  final Set<Component> allIgnored() {
    return filter.ignored.keySet();
  }

  final Set<Component> allImplicityIgnored() {
    return filter.implicitlyIgnored.keySet();
  }

  final void assertThatNoComponentsAreIgnored() {
    assertThat(filter.ignored.size()).isZero();
  }

  final void assertThatNoComponentsAreImplicitlyIgnored() {
    assertThat(filter.implicitlyIgnored.size()).isZero();
  }
}
