/*
 * Created on Jul 8, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.util;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import javax.annotation.Nonnull;

/**
 * @author Alex Ruiz
 */
class OSIdentifierStub extends OSIdentifier {
  static OSIdentifier windows9x() {
    return new OSIdentifierStub("Windows 95");
  }

  static OSIdentifier windowsXP() {
    return new OSIdentifierStub("Windows XP");
  }

  static OSIdentifier macintosh() {
    return macBasedOS("Mac OS");
  }

  static OSIdentifier osX() {
    return macBasedOS("OS X");
  }

  private static OSIdentifier macBasedOS(String osName) {
    return new OSIdentifierStub(osName, "someVersion");
  }

  static OSIdentifier x11() {
    return new OSIdentifierStub("X11");
  }

  static OSIdentifier solaris() {
    return new OSIdentifierStub("Solaris");
  }

  static OSIdentifier hpUX() {
    return new OSIdentifierStub("HP-UX");
  }

  static OSIdentifier linux() {
    return new OSIdentifierStub("Linux");
  }

  private OSIdentifierStub(String osName) {
    this(osName, null);
  }

  private OSIdentifierStub(String osName, String mrjVersion) {
    super(new SystemPropertyReaderStub(osName, mrjVersion));
  }

  private static class SystemPropertyReaderStub extends SystemPropertyReader {
    private final String osName;
    private final String mrjVersion;

    SystemPropertyReaderStub(String osName, String mrjVersion) {
      this.osName = osName;
      this.mrjVersion = mrjVersion;
    }

    @Override
    String systemProperty(@Nonnull String propertyName) {
      if ("os.name".equals(propertyName)) {
        return osName;
      }
      if ("mrj.version".equals(propertyName)) {
        return mrjVersion;
      }
      throw new IllegalArgumentException(concat("Unexpected property name ", quote(propertyName)));
    }
  }
}
