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

import javax.swing.JTextField;

import org.fest.swing.exception.ComponentLookupException;
import org.junit.Test;

/**
 * Tests for {@link BasicComponentFinder#find(ComponentMatcher)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicComponentFinder_findUsingComponentMatcher_Test extends BasicComponentFinder_TestCase {
  @Test
  public void should_throw_error_if_more_than_one_Component_matched() {
    try {
      finder.find(new TypeMatcher(JTextField.class));
      failWhenExpectingException();
    } catch (ComponentLookupException e) {
      assertThat(e.getMessage()).contains("Found:").contains("text='TextField 1'").contains("text='TextField 2'");
    }
  }
}
