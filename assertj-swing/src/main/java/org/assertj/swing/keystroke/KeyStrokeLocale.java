/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.keystroke;

import java.awt.im.InputContext;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * The locale to use for mapping keystrokes.
 *
 * @author Christian Roesch
 */
class KeyStrokeLocale {
  static final String ASSERTJ_SWING_KEYBOARD_LOCALE = "assertj.swing.keyboard.locale";
  private static Logger LOGGER = Logger.getLogger(KeyStrokeLocale.class.getName());
  private static Locale locale;

  static {
    reloadFromSystemSettings();
  }

  /** Reloads the key stroke locale for the language using the current system settings. */
  static void reloadFromSystemSettings() {
    LOGGER.finer("reload locale from settings");
    String propertyLocale = System.getProperty(ASSERTJ_SWING_KEYBOARD_LOCALE);
    LOGGER.finer("system property >" + ASSERTJ_SWING_KEYBOARD_LOCALE + "<: " + propertyLocale);
    if (propertyLocale != null) {
      locale = Locale.forLanguageTag(propertyLocale);
      LOGGER.finer("using locale from system property: " + locale);
    }
    if (locale == null) {
      locale = InputContext.getInstance().getLocale();
      LOGGER.finer("using locale from input context: " + locale);
    }
    if (locale == null) {
      locale = Locale.getDefault();
      LOGGER.finer("using default locale: " + locale);
    }
  }

  static Locale get() {
    return locale;
  }

  private KeyStrokeLocale() {
  }
}
