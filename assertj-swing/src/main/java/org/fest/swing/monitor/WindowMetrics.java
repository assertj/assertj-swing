/*
 * Created on Oct 18, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * @author Alex Ruiz
 */
final class WindowMetrics {
  @RunsInCurrentThread
  static @Nonnull Point absoluteCenterOf(@Nonnull Window window) {
    Insets insets = window.getInsets();
    int w = window.getWidth() - (insets.left + insets.right);
    int h = window.getHeight() - (insets.top + insets.bottom);
    int x = window.getX() + insets.left;
    int y = window.getY() + insets.top;
    return new Point(x + (w / 2), y + (h / 2));
  }

  private WindowMetrics() {}
}
