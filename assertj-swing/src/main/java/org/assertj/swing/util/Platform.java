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
package org.assertj.swing.util;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.util.Modifiers.keysFor;

import java.awt.Toolkit;

import javax.annotation.Nonnull;

import org.assertj.core.util.VisibleForTesting;

/**
 * Platform-specific functionality.
 * 
 * @author Alex Ruiz
 */
public final class Platform {
  private static OSIdentifier osIdentifier;
  private static Toolkit toolkit;

  static {
    reload();
  }

  @VisibleForTesting
  static void reload() {
    initialize(new OSIdentifier(), ToolkitProvider.instance().defaultToolkit());
  }

  @VisibleForTesting
  static void initialize(@Nonnull OSIdentifier osIdentifier, @Nonnull Toolkit toolkit) {
    Platform.osIdentifier = osIdentifier;
    Platform.toolkit = toolkit;
  }

  /**
   * Return the modifier key for the appropriate accelerator key for menu shortcuts: {@link java.awt.event.KeyEvent#VK_CONTROL}
   * (default) or {@link java.awt.event.KeyEvent#VK_META} (MacOS).
   * 
   * @return the modifier key for the appropriate accelerator key for menu shortcuts.
   * @throws AssertionError if unable to find the appropriate key.
   * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()}.
   */
  public static int controlOrCommandKey() {
    int menuShortcutKeyMask = controlOrCommandMask();
    int[] keys = keysFor(menuShortcutKeyMask);
    assertThat(keys).as(concat("Key code for mask ", valueOf(menuShortcutKeyMask))).isNotNull().hasSize(1);
    return keys[0];
  }

  /**
   * Return the modifier mask for the appropriate accelerator key for menu shortcuts: {@link java.awt.Event#CTRL_MASK} (default)
   * or {@link java.awt.Event#META_MASK} (MacOS).
   * 
   * @return the modifier mask for the appropriate accelerator key for menu shortcuts.
   * @throws java.awt.HeadlessException if {@code GraphicsEnvironment.isHeadless()}.
   */
  public static int controlOrCommandMask() {
    return toolkit.getMenuShortcutKeyMask();
  }

  /**
   * Indicates whether it is possible to resize windows that are not an instance of {@link java.awt.Frame} or
   * {@link java.awt.Dialog}. Most X11 window managers will allow this, but stock Macintosh and Windows do not.
   * 
   * @return {@code true} if it is possible to resize windows other than {@code Frame}s or {@code Dialog}s,
   *         {@code false} otherwise.
   */
  public static boolean canResizeWindows() {
    return !isWindows() && !isMacintosh();
  }

  /**
   * Indicates whether it is possible to move windows that are not an instance of {@link java.awt.Frame} or
   * {@link java.awt.Dialog}. Most X11 window managers will allow this, but stock Macintosh and Windows do not.
   * 
   * @return {@code true} if it is possible to move windows other than {@code Frame}s or {@code Dialog}s, {@code false}
   *         otherwise.
   */
  public static boolean canMoveWindows() {
    return !isWindows() && !isMacintosh();
  }

  /**
   * Indicates whether the operating system is Windows.
   * 
   * @return {@code true} if the operation system is Windows, {@code false} otherwise.
   */
  public static boolean isWindows() {
    return osIdentifier.isWindows();
  }

  /**
   * Indicates whether the operating system is Windows 9x (95, 98 or ME).
   * 
   * @return {@code true} if the operating system is Windows 9x (95, 98 or ME), {@code false} otherwise.
   */
  public static boolean isWindows9x() {
    return osIdentifier.isWindows9x();
  }

  /**
   * Indicates whether the operating system is Windows XP.
   * 
   * @return {@code true} if the operating system is Windows XP, {@code false} otherwise.
   */
  public static boolean isWindowsXP() {
    return osIdentifier.isWindowsXP();
  }

  /**
   * Indicates whether the operating system is a Macintosh OS.
   * 
   * @return {@code true} is the operating system is a Macintosh OS, {@code false} otherwise.
   */
  public static boolean isMacintosh() {
    return osIdentifier.isMacintosh();
  }

  /**
   * Indicates whether the operating system is Mac OS X.
   * 
   * @return {@code true} if the operating system is Mac OS X, {@code false} otherwise.
   */
  public static boolean isOSX() {
    return osIdentifier.isOSX();
  }

  /**
   * Indicates whether the operating system is using the X11 Windowing system.
   * 
   * @return {@code true} if the operating system is using the X11 Windowing system, {@code false} otherwise.
   */
  public static boolean isX11() {
    return osIdentifier.isX11();
  }

  /**
   * Indicates whether the operating system is Solaris.
   * 
   * @return {@code true} if the operating system is Solaris, {@code false} otherwise.
   */
  public static boolean isSolaris() {
    return osIdentifier.isSolaris();
  }

  /**
   * Indicates whether the operating system is HP-UX.
   * 
   * @return {@code true} if the operating system is HP-UX, {@code false} otherwise.
   */
  public static boolean isHPUX() {
    return osIdentifier.isHPUX();
  }

  /**
   * Indicates whether the operating system is Linux.
   * 
   * @return {@code true} if the operating system is Linux, {@code false} otherwise.
   */
  public static boolean isLinux() {
    return osIdentifier.isLinux();
  }

  /**
   * @return the current operating system family.
   */
  @Nonnull public static OSFamily osFamily() {
    return osIdentifier.osFamily();
  }

  private Platform() {
  }
}
