/*
 * Created on Feb 24, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsDisabledComponent;
import static org.fest.swing.test.core.CommonAssertions.assertThatErrorCauseIsNotShowingComponent;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.junit.Test;

/**
 * Tests for {@link JListDriver#drag(javax.swing.JList, String)} and {@link JListDriver#drop(javax.swing.JList, String)}
 * .
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JListDriver_drop_Test extends JListDriver_dragAndDrop_TestCase {
  @Test
  public void should_drop() {
    showWindow();
    driver.drag(list, "two");
    driver.drop(dropList);
    list.requireElements("one", "three");
    assertThat(dropList.elements()).hasSize(4);
  }

  @Test
  public void should_throw_error_if_JList_is_disabled() {
    disableList();
    try {
      driver.drop(list);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsDisabledComponent(e);
    }
  }

  @Test
  public void should_throw_error_if_JList_is_not_showing_on_the_screen() {
    try {
      driver.drop(dropList);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThatErrorCauseIsNotShowingComponent(e);
    }
  }

}
