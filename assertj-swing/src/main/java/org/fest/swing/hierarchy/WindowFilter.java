/*
 * Created on Oct 31, 2007
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
package org.fest.swing.hierarchy;

import static org.fest.swing.awt.AWT.isSharedInvisibleFrame;
import static org.fest.util.Maps.newWeakHashMap;

import java.awt.Component;
import java.awt.Window;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * A filter of windows to ignore in a component hierarchy.
 * 
 * @author Alex Ruiz
 */
class WindowFilter {
  private final ParentFinder parentFinder;
  private final ChildrenFinder childrenFinder;

  WindowFilter() {
    this(new ParentFinder(), new ChildrenFinder());
  }

  WindowFilter(@Nonnull ParentFinder parentFinder, @Nonnull ChildrenFinder childrenFinder) {
    this.parentFinder = parentFinder;
    this.childrenFinder = childrenFinder;
  }

  // Map of components to ignore
  final Map<Component, Boolean> ignored = newWeakHashMap();

  // Map of components implicitly ignored; these will be removed if they are re-shown.
  final Map<Component, Boolean> implicitlyIgnored = newWeakHashMap();

  boolean isImplicitlyIgnored(@Nonnull Component c) {
    return implicitlyIgnored.containsKey(c);
  }

  @RunsInCurrentThread
  boolean isIgnored(@Nullable Component c) {
    if (c == null) {
      return false;
    }
    // TODO if ("sun.plugin.ConsoleWindow".equals(c.getClass().getName())) return !trackAppletConsole;
    if (ignored.containsKey(c)) {
      return true;
    }
    if (c instanceof Window && isIgnored(c.getParent())) {
      return true;
    }
    return !(c instanceof Window) && isWindowIgnored(c);
  }

  private boolean isWindowIgnored(@Nullable Component c) {
    Window w = parentFinder.windowFor(c);
    return w != null && isIgnored(w);
  }

  void implicitlyIgnore(@Nonnull Component c) {
    implicitlyIgnored.put(c, true);
  }

  @RunsInCurrentThread
  void ignore(@Nonnull Component c) {
    filter(c, true);
  }

  @RunsInCurrentThread
  void recognize(@Nonnull Component c) {
    filter(c, false);
  }

  @Nonnull Collection<Component> filtered() {
    return ignored.keySet();
  }

  private void filter(@Nonnull Component c, boolean ignore) {
    // Never filter the shared frame
    if (isSharedInvisibleFrame(c)) {
      for (Component child : childrenFinder.childrenOf(c)) {
        filter(child, ignore);
      }
      return;
    }
    doFilter(c, ignore);
    implicitlyIgnored.remove(c);
    if (!(c instanceof Window)) {
      return;
    }
    for (Window owned : ((Window) c).getOwnedWindows()) {
      if (owned != null) {
        filter(owned, ignore);
      }
    }
  }

  private void doFilter(@Nonnull Component c, boolean ignore) {
    if (ignore) {
      ignored.put(c, true);
      return;
    }
    ignored.remove(c);
  }
}
