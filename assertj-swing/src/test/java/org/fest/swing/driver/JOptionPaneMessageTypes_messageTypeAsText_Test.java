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

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JOptionPaneMessageTypes#messageTypeAsText(int)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class JOptionPaneMessageTypes_messageTypeAsText_Test {
  private final int messageType;
  private final String expected;

  @Parameters
  public static Collection<Object[]> messageTypes() {
    return newArrayList(new Object[][] {
        { ERROR_MESSAGE, "Error Message" },
        { INFORMATION_MESSAGE, "Information Message" },
        { WARNING_MESSAGE, "Warning Message" },
        { QUESTION_MESSAGE, "Question Message" },
        { PLAIN_MESSAGE, "Plain Message" }
      });
  }

  public JOptionPaneMessageTypes_messageTypeAsText_Test(int messageType, String expected) {
    this.messageType = messageType;
    this.expected = expected;
  }

  @Test
  public void should_return_text_for_given_message_type_value() {
    assertThat(JOptionPaneMessageTypes.messageTypeAsText(messageType)).isEqualTo(expected);
  }
}
