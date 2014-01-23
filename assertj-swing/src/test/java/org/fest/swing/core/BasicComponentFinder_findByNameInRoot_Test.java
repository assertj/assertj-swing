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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Component;

import org.fest.swing.exception.ComponentLookupException;
import org.junit.Test;

/**
 * Tests for {@link BasicComponentFinder#findByName(java.awt.Container, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Price
 */
public class BasicComponentFinder_findByNameInRoot_Test extends BasicComponentFinder_TestCase {
  private MyWindow windowTwo;

  @Test
  public void should_find_Component() {
    windowTwo = MyWindow.createNew(getClass());
    windowTwo.button.setName("anotherButton");
    Component button = finder.findByName(windowTwo, "anotherButton");
    assertThat(button).isSameAs(windowTwo.button);
  }

  @Test
  public void should_throw_errror_if_Component_not_found() {
    try {
      finder.findByName(window, "label");
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("name='label'");
    }
  }

  @Override
  void beforeReleasingScreenLock() {
    if (windowTwo != null) {
      windowTwo.destroy();
    }
  }
}
