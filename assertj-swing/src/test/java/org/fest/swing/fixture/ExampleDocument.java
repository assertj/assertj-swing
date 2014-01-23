/*
 * Created on Nov 29, 2008
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
package org.fest.swing.fixture;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Example document used to reproduce <a href="http://code.google.com/p/fest/issues/detail?id=219" target="_blank">issue
 * 219</a>.
 * 
 * @author matthiaso
 */
public class ExampleDocument extends PlainDocument {
  /*
   * Inserts a text with a maximum length of 10 characters.
   */
  @Override
  public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
    // If length is less than 10 characters, insert it
    if (s != null && getLength() + s.length() <= 10) {
      super.insertString(offs, s, a);
    } else {
      showMessageDialog(null, "Maximum length: 10 characters", "Maximum length exceeded", ERROR_MESSAGE);
    }
  }
}
