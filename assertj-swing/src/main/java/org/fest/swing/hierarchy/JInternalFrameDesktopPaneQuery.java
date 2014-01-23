/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * <p>
 * Returns the desktop the given {@code JInternalFrame} belongs to when iconified.
 * </p>
 * 
 * <p>
 * <b>Note:</b> Methods in this class are accessed in the current executing thread. Such thread may or may not be the
 * event dispatch thread (EDT.) Client code must call methods in this class from the EDT.
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JInternalFrameDesktopPaneQuery {
  @RunsInCurrentThread
  static @Nullable JDesktopPane desktopPaneOf(@Nonnull JInternalFrame internalFrame) {
    JDesktopIcon icon = internalFrame.getDesktopIcon();
    if (icon != null) {
      return icon.getDesktopPane();
    }
    return null;
  }

  private JInternalFrameDesktopPaneQuery() {}
}