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
import javax.swing.JLabel;

import org.assertj.swing.exception.ComponentLookupException;
import org.junit.Test;

/**
 * Tests for {@link BasicComponentFinder#findByName(java.awt.Container, String, Class, boolean)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Price
 */
public class BasicComponentFinder_findByNameAndTypeInRoot_Test extends BasicComponentFinder_TestCase {
  private MyWindow windowTwo;

  @Test
  public void should_Find_Component() {
    windowTwo = MyWindow.createNew(getClass());
    JButton button = finder.findByName(windowTwo, "button", JButton.class);
    assertThat(button).isSameAs(windowTwo.button);
  }

  @Test
  public void should_Throw_Error_If_Component_Not_Found() {
    thrown.expect(ComponentLookupException.class, "name='list'");
    thrown.expectMessageToContain("type=javax.swing.JLabel");
    finder.findByName(window, "list", JLabel.class);
  }

  @Test
  public void should_Throw_Error_If_Component_Found_By_Name_And_Container_But_Not_By_Type() {
    thrown.expect(ComponentLookupException.class, "name='button'");
    thrown.expectMessageToContain("type=javax.swing.JLabel");
    finder.findByName(window, "button", JLabel.class);
  }

  @Override
  void beforeReleasingScreenLock() {
    if (windowTwo != null) {
      windowTwo.destroy();
    }
  }
}
