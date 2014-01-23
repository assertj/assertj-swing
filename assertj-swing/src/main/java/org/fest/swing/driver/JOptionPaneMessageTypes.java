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
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.concat;

import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Message types of a {@code JOptionPane}.
 * 
 * @author Alex Ruiz
 */
final class JOptionPaneMessageTypes {
  private static final Map<Integer, String> messageMap = newHashMap();
  static {
    messageMap.put(ERROR_MESSAGE, "Error Message");
    messageMap.put(INFORMATION_MESSAGE, "Information Message");
    messageMap.put(WARNING_MESSAGE, "Warning Message");
    messageMap.put(QUESTION_MESSAGE, "Question Message");
    messageMap.put(PLAIN_MESSAGE, "Plain Message");
  }

  static @Nonnull String messageTypeAsText(int messageType) {
    if (messageMap.containsKey(messageType)) {
      return checkNotNullOrEmpty(messageMap.get(messageType));
    }
    throw actionFailure(concat("The message type <", messageType, "> is not valid"));
  }

  private JOptionPaneMessageTypes() {}
}
