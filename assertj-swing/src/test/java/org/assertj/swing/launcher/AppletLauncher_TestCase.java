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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.ExpectedException.none;

import java.applet.Applet;

import org.assertj.swing.applet.AppletViewer;
import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.SequentialEDTSafeTestCase;
import org.assertj.swing.test.swing.TestApplet;
import org.junit.Rule;

/**
 * Base test case for {@link AppletLauncher}.
 * 
 * @author Yvonne Wang
 */
public abstract class AppletLauncher_TestCase extends SequentialEDTSafeTestCase {
  TestApplet applet;
  AppletViewer viewer;

  @Rule
  public ExpectedException thrown = none();

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
