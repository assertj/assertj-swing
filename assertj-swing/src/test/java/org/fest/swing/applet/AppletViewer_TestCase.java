/*
 * Created on Jul 24, 2009
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
package org.fest.swing.applet;

import static javax.swing.SwingUtilities.invokeLater;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.timing.Pause.pause;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.TestApplet;
import org.fest.swing.timing.Condition;
import org.junit.After;
import org.junit.Before;

/**
 * Base class for GUI tests for {@link AppletViewer}.
 * 
 * @author Alex Ruiz
 */
public abstract class AppletViewer_TestCase extends EDTSafeTestCase {
  TestApplet applet;
  FrameFixture fixture; // TODO should not depend on fixture
  AppletViewer viewer;

  @Before
  public final void setUp() {
    applet = createApplet();
    viewer = createViewer();
    fixture = new FrameFixture(viewer);
    fixture.show();
    assertThatIsInitializedAndStarted(applet);
  }

  @RunsInEDT
  final AppletViewer createViewer() {
    return AppletViewer.newViewer(applet);
  }

  @RunsInEDT
  private static TestApplet createApplet() {
    return execute(new GuiQuery<TestApplet>() {
      @Override
      protected TestApplet executeInEDT() {
        return new TestApplet();
      }
    });
  }

  @RunsInEDT
  private static void assertThatIsInitializedAndStarted(final TestApplet applet) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        assertThat(applet.initialized()).isTrue();
        assertThat(applet.started()).isTrue();
      }
    });
  }

  @After
  public final void tearDown() {
    unloadAppletIn(viewer);
    assertThatAppletIsStoppedAndDestroyed();
    fixture.cleanUp();
  }

  @RunsInEDT
  private static void unloadAppletIn(final AppletViewer viewer) {
    invokeLater(new Runnable() {
      @Override
      public void run() {
        viewer.unloadApplet();
      }
    });
    pause(new Condition("applet is unloaded") {
      @Override
      public boolean test() {
        return !viewer.appletLoaded();
      }
    });
  }

  final void assertThatAppletIsStoppedAndDestroyed() {
    assertThat(applet.stopped()).isTrue();
    assertThat(applet.destroyed()).isTrue();
  }

  final void assertThatAppletIsShowingAndViewerIsLoaded() {
    assertThat(applet.isShowing()).isTrue();
    assertThat(viewer.appletLoaded()).isTrue();
  }
}
