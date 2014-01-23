/*
 * Created on Oct 13, 2008
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
package org.fest.swing.query;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.MethodInvocations.Args.args;

import org.junit.Test;

/**
 * Tests for {@link JTableColumnByIdentifierQuery#columnIndexByIdentifier(javax.swing.JTable, Object)}.
 * 
 * @author Alex Ruiz
 */
public class JTableColumnByIdentifierQuery_columnIndexByIdentifier_withInvalidId_Test extends
JTableColumnByIdentifierQuery_TestCase {
  @Test
  public void should_return_negative_one_if_column_index_not_found() {
    String invalid = "Hello World";
    table.startRecording();
    assertThat(columnIndexByIdentifier(invalid)).isEqualTo(-1);
    table.requireInvoked("getColumn", args(invalid));
  }
}
