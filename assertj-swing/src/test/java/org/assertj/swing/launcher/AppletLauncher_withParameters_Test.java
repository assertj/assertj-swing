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
import static org.assertj.swing.launcher.AppletParameter.name;

import org.assertj.swing.test.swing.TestApplet;
import org.junit.Test;

/**
 * Tests for {@link AppletLauncher#withParameters(AppletParameter...)}.
 * 
 * @author Yvonne Wang
 */
public class AppletLauncher_withParameters_Test extends AppletLauncher_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Parameter_Array_Is_Null() {
    AppletParameter[] parameters = null;
    AppletLauncher.launcherFor(TestApplet.createNew()).withParameters(parameters);
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Parameter__In_Array_Is_Null() {
    AppletParameter[] parameters = new AppletParameter[2];
    parameters[0] = name("bgcolor").value("blue");
    parameters[1] = null;
    AppletLauncher.launcherFor(TestApplet.createNew()).withParameters(parameters);
  }

  @Test
  public void should_Set_Given_Parameters() {
    applet = TestApplet.createNew();
    viewer = AppletLauncher.launcherFor(applet)
        .withParameters(name("bgcolor").value("blue"), name("color").value("red")).start();
    assertThatAppletWasLaunched();
    assertThat(applet.getParameter("bgcolor")).isEqualTo("blue");
    assertThat(applet.getParameter("color")).isEqualTo("red");
  }
}
