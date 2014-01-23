/*
 * Created on Jul 15, 2008
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
package org.fest.swing.launcher;

import static org.fest.assertions.Assertions.assertThat;

import java.applet.Applet;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.test.core.SequentialEDTSafeTestCase;
import org.fest.swing.test.swing.TestApplet;

/**
 * Base test case for {@link AppletLauncher}.
 * 
 * @author Yvonne Wang
 */
public abstract class AppletLauncher_TestCase extends SequentialEDTSafeTestCase {
  TestApplet applet;
  AppletViewer viewer;

  @Override
  protected final void onTearDown() {
    try {
      disposeViewer();
    } finally {
      disposeApplet();
    }
  }

  private void disposeViewer() {
    if (viewer == null) {
      return;
    }
    viewer.dispose();
  }

  private void disposeApplet() {
    if (applet == null) {
      return;
    }
    applet.stop();
    applet.destroy();
  }

  static class AnApplet extends Applet {
    AnApplet(String name) {
      if (name == null) {
        return;
      }
    }
  }

  final void assertThatAppletWasLaunched() {
    assertThat(viewer.getApplet()).isInstanceOf(TestApplet.class);
  }
}
