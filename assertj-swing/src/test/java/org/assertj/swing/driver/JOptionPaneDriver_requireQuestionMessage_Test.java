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
package org.assertj.swing.driver;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static org.assertj.swing.test.swing.JOptionPaneLauncher.pack;

import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;
import org.junit.Test;

/**
 * Tests for {@link JOptionPaneDriver#requireQuestionMessage(JOptionPane)}.
 * 
 * @author Alex Ruiz
 */
public class JOptionPaneDriver_requireQuestionMessage_Test extends JOptionPaneDriver_TestCase {
  @Test
  public void should_Pass_If_Error_Type_Is_Equal_To_Expected() {
    JOptionPane optionPane = questionMessage();
    pack(optionPane, title());
    driver.requireQuestionMessage(optionPane);
  }

  @RunsInEDT
  private static JOptionPane questionMessage() {
    return messageOfType(QUESTION_MESSAGE);
  }

  @Test
  public void should_Fail_If_Error_Type_Is_Not_Equal_To_Expected() {
    JOptionPane optionPane = errorMessage();
    pack(optionPane, title());
    thrown.expectAssertionError("messageType", "[Question] Message", "[Error] Message");
    driver.requireQuestionMessage(optionPane);
  }
}
