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
package org.assertj.swing.keystroke;

import java.awt.im.InputContext;
import java.util.Locale;

/**
 * The locale to use for mapping keystrokes.
 * 
 * @author Christian Roesch
 */
class KeyStrokeLocale {
  static final String ASSERTJ_SWING_KEYBOARD_LOCALE = "assertj.swing.keyboard.locale";
  private static Locale locale;

  static {
    reloadFromSystemSettings();
  }

  /** Reloads the key stroke locale for the language using the current system settings. */
  static void reloadFromSystemSettings() {
    String propertyLocale = System.getProperty(ASSERTJ_SWING_KEYBOARD_LOCALE);
    if (propertyLocale != null) {
      locale = Locale.forLanguageTag(propertyLocale);
    }
    if (locale == null) {
      locale = InputContext.getInstance().getLocale();
    }
    if (locale == null) {
      locale = Locale.getDefault();
    }
  }

  static Locale get() {
    return locale;
  }

  private KeyStrokeLocale() {
  }
}
