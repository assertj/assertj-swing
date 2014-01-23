/*
 * Created on May 16, 2009
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

import static java.util.Locale.ENGLISH;
import static org.fest.swing.util.OSFamily.LINUX;
import static org.fest.swing.util.OSFamily.MAC;
import static org.fest.swing.util.OSFamily.UNIX;
import static org.fest.swing.util.OSFamily.WINDOWS;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.fest.util.VisibleForTesting;

/**
 * Identifies the current Operating System.
 *
 * @author Alex Ruiz
 */
class OSIdentifier {
  private final boolean isWindows;
  private final boolean isWindows9x;
  private final boolean isWindowsXP;
  private final boolean isMacintosh;
  private final boolean isOSX;
  private final boolean isX11;
  private final boolean isSolaris;
  private final boolean isHPUX;
  private final boolean isLinux;
  private final OSFamily osFamily;

  OSIdentifier() {
    this(new SystemPropertyReader());
  }

  @VisibleForTesting
  OSIdentifier(@Nonnull SystemPropertyReader reader) {
    String osName = checkNotNull(reader.systemProperty("os.name")).toLowerCase(ENGLISH);
    String mrjVersion = reader.systemProperty("mrj.version");
    isWindows = osName.startsWith("windows");
    isWindows9x = isWindows && containsAny(osName, "95", "98", "me");
    isWindowsXP = isWindows && osName.contains("xp");
    isMacintosh = mrjVersion != null;
    isOSX = isMacintosh && osName.contains("os x");
    isX11 = !isOSX && !isWindows;
    isSolaris = osName.startsWith("sunos") || osName.startsWith("solaris");
    isHPUX = osName.equals("hp-ux");
    isLinux = osName.equals("linux");
    osFamily = findOSFamily();
  }

  private static boolean containsAny(@Nonnull String target, @Nonnull String... subs) {
    for (String sub : subs) {
      if (target.contains(sub)) {
        return true;
      }
    }
    return false;
  }

  private @Nonnull OSFamily findOSFamily() {
    if (isWindows()) {
      return WINDOWS;
    }
    if (isMacintosh() || isOSX()) {
      return MAC;
    }
    if (isLinux()) {
      return LINUX;
    }
    return UNIX;
  }

  boolean isWindows() {
    return isWindows;
  }

  boolean isWindows9x() {
    return isWindows9x;
  }

  boolean isWindowsXP() {
    return isWindowsXP;
  }

  boolean isMacintosh() {
    return isMacintosh;
  }

  boolean isOSX() {
    return isOSX;
  }

  boolean isX11() {
    return isX11;
  }

  boolean isSolaris() {
    return isSolaris;
  }

  boolean isHPUX() {
    return isHPUX;
  }

  boolean isLinux() {
    return isLinux;
  }

  /* Since 1.2 */
  @Nonnull OSFamily osFamily() {
    return osFamily;
  }
}
