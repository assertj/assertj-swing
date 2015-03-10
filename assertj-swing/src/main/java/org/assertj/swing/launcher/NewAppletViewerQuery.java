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
package org.assertj.swing.launcher;

import static org.assertj.swing.applet.AppletViewer.newViewer;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.timing.Pause.pause;

import java.applet.Applet;
import java.util.Map;

import javax.annotation.Nonnull;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.applet.AppletViewer;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.timing.Condition;

/**
 * Creates and shows a new {@link AppletViewer}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class NewAppletViewerQuery {
  // TODO test
  @RunsInEDT
  static @Nonnull AppletViewer showAppletViewerWith(final @Nonnull Applet applet,
      final @Nonnull Map<String, String> parameters) {
    final AppletViewer viewer = newViewer(applet, parameters);
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        viewer.pack();
        viewer.setVisible(true);
      }
    });
    pause(new Condition("new AppletViewer to be showing") {
      @Override
      public boolean test() {
        return viewer.isShowing();
      }
    });
    return viewer;
  }

  private NewAppletViewerQuery() {
  }
}
