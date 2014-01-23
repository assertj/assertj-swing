/*
 * Created on Feb 25, 2008
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
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import org.fest.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link JTableDriver#columnIndex(javax.swing.JTable, Object)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTableDriver_columnIndex_withInvalidInput_Test extends JTableDriver_TestCase {
  @Test
  public void should_throw_error_if_column_with_given_id_was_not_found() {
    try {
      driver.columnIndex(table, "Hello World");
      failWhenExpectingException();
    } catch (ActionFailedException e) {
      assertThat(e.getMessage()).contains("Unable to find a column with id 'Hello World");
    }
  }
}
