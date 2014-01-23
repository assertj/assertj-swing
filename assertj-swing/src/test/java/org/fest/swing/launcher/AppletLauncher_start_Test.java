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

import org.fest.swing.test.swing.TestApplet;
import org.junit.Test;

/**
 * Tests for {@link AppletLauncher#start()}.
 * 
 * @author Yvonne Wang
 */
public class AppletLauncher_start_Test extends AppletLauncher_TestCase {
  @Test
  public void should_launch_given_Applet() {
    applet = TestApplet.createNew();
    viewer = AppletLauncher.launcherFor(applet).start();
    assertThatAppletWasLaunched();
    assertThat(viewer.getApplet()).isSameAs(applet);
  }

  @Test
  public void should_instantiate_Applet_and_launch_it() {
    viewer = AppletLauncher.applet(TestApplet.class).start();
    assertThatAppletWasLaunched();
  }

  @Test
  public void should_load_Applet_type_and_launch_Applet() {
    viewer = AppletLauncher.applet(TestApplet.class.getName()).start();
    assertThatAppletWasLaunched();
  }
}
