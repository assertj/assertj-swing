/*
 * Created on Oct 24, 2007
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

import static org.fest.util.Lists.emptyList;
import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.awt.Container;
import java.util.Collection;

import javax.annotation.Nonnull;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JInternalFrame.JDesktopIcon;

import org.fest.swing.annotation.RunsInCurrentThread;

/**
 * Finds children {@code Component}s in a {@code JDesktopPane}.
 *
 * @author Yvonne Wang
 */
final class JDesktopPaneChildrenFinder implements ChildrenFinderStrategy {
  @Override
  @RunsInCurrentThread
  public @Nonnull Collection<Component> nonExplicitChildrenOf(@Nonnull Container c) {
    if (!(c instanceof JDesktopPane)) {
      return emptyList();
    }
    return internalFramesFromIcons(c);
  }

  // From Abbot: add iconified frames, which are otherwise unreachable. For consistency, they are still considered
  // children of the desktop pane.
  @RunsInCurrentThread
  private @Nonnull Collection<Component> internalFramesFromIcons(@Nonnull Container c) {
    Collection<Component> frames = newArrayList();
    for (Component child : c.getComponents()) {
      if (child instanceof JDesktopIcon) {
        JInternalFrame frame = ((JDesktopIcon) child).getInternalFrame();
        if (frame != null) {
          frames.add(frame);
        }
        continue;
      }
      // OSX puts icons into a dock; handle icon manager situations here
      if (child instanceof Container) {
        frames.addAll(internalFramesFromIcons((Container) child));
      }
    }
    return frames;
  }
}
