/*
 * Created on Jul 16, 2008
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
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link JTextComponentMatcher#toString()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTextComponentMatcher_toString_Test {
  @Test
  public void should_implement_toString() {
    JTextComponentMatcher matcher = JTextComponentMatcher.withName("textField").andText("Hello").andShowing();
    assertThat(matcher.toString()).isEqualTo(
        "org.fest.swing.core.matcher.JTextComponentMatcher[name='textField', text='Hello', requireShowing=true]");
  }
}
