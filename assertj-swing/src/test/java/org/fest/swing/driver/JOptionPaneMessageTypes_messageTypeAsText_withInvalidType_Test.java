/*
 * Created on Jul 22, 2008
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

import org.fest.swing.exception.ActionFailedException;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneMessageTypes#messageTypeAsText(int)}.
 * 
 * @author Alex Ruiz
 */
public class JOptionPaneMessageTypes_messageTypeAsText_withInvalidType_Test {
  @Test(expected = ActionFailedException.class)
  public void should_throw_error_if_message_type_value_is_invalid() {
    JOptionPaneMessageTypes.messageTypeAsText(Integer.MIN_VALUE);
  }
}
