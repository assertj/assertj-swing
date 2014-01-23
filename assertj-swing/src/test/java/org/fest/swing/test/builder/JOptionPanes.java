/*
 * Created on Aug 28, 2008
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
package org.fest.swing.test.builder;

import javax.swing.JOptionPane;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;

/**
 * Factory of {@code JOptionPane}s.
 * 
 * @author Alex Ruiz
 */
public final class JOptionPanes {
  private JOptionPanes() {}

  public static JOptionPaneFactory optionPane() {
    return new JOptionPaneFactory();
  }

  public static class JOptionPaneFactory {
    Object message;
    int messageType;
    String name;
    int optionType;

    public JOptionPaneFactory withMessage(Object newMessage) {
      message = newMessage;
      return this;
    }

    public JOptionPaneFactory withMessageType(int newMessageType) {
      messageType = newMessageType;
      return this;
    }

    public JOptionPaneFactory withName(String newName) {
      name = newName;
      return this;
    }

    public JOptionPaneFactory withOptionType(int newOptionType) {
      optionType = newOptionType;
      return this;
    }

    @RunsInEDT
    public JOptionPane createNew() {
      return GuiActionRunner.execute(new GuiQuery<JOptionPane>() {
        @Override
        protected JOptionPane executeInEDT() {
          JOptionPane optionPane = new JOptionPane();
          optionPane.setMessage(message);
          optionPane.setMessageType(messageType);
          optionPane.setName(name);
          optionPane.setOptionType(optionType);
          return optionPane;
        }
      });
    }
  }
}