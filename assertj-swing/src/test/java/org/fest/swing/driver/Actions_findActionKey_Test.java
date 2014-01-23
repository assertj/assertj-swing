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

import static javax.swing.Action.NAME;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.Action;
import javax.swing.ActionMap;

import org.fest.swing.exception.ActionFailedException;
import org.fest.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link Actions#findActionKey(String, ActionMap)}.
 *
 * @author Alex Ruiz
 */
public class Actions_findActionKey_Test {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private ActionMap map;
  private Action action;

  @Before
  public void setUp() {
    map = new ActionMap();
    action = mock(Action.class);
    map.put("key", action);
  }

  @Test
  public void should_return_given_name_if_equal_to_key() {
    Object found = Actions.findActionKey("key", map);
    assertThat(found).isEqualTo("key");
  }

  @Test
  public void should_return_key_if_action_name_is_equal_to_given_name() {
    when(action.getValue(NAME)).thenReturn("name");
    Object found = Actions.findActionKey("name", map);
    assertThat(found).isEqualTo("key");
  }

  @Test
  public void should_throw_error_if_key_not_found() {
    when(action.getValue(NAME)).thenReturn("name");
    thrown.expect(ActionFailedException.class, "The action 'someName' is not available, available actions:['key']");
    Object found = Actions.findActionKey("someName", map);
    assertThat(found).isEqualTo("key");
  }
}
