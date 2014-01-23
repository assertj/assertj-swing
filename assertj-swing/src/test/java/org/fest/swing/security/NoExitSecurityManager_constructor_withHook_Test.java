/*
 * Created on Dec 21, 2009
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
package org.fest.swing.security;

import static org.junit.rules.ExpectedException.none;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link NoExitSecurityManager#NoExitSecurityManager(ExitCallHook)}.
 * 
 * @author Alex Ruiz
 */
public class NoExitSecurityManager_constructor_withHook_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_throw_error_if_hook_is_null() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("The given ExitCallHook should not be null");
    new NoExitSecurityManager(null);
  }
}
