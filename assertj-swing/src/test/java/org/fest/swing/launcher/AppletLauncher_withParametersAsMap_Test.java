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
import static org.fest.util.Maps.newHashMap;

import java.util.Map;

import org.fest.swing.test.swing.TestApplet;
import org.junit.Test;

/**
 * Tests for {@link AppletLauncher#withParameters(Map)}.
 * 
 * @author Yvonne Wang
 */
public class AppletLauncher_withParametersAsMap_Test extends AppletLauncher_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_parameter_map_is_null() {
    Map<String, String> parameters = null;
    AppletLauncher.launcherFor(TestApplet.createNew()).withParameters(parameters);
  }

  @Test
  public void should_set_parameters_in_given_map() {
    Map<String, String> parameters = newHashMap();
    parameters.put("bgcolor", "blue");
    parameters.put("color", "red");
    applet = TestApplet.createNew();
    viewer = AppletLauncher.launcherFor(applet).withParameters(parameters).start();
    assertThatAppletWasLaunched();
    assertThat(applet.getParameter("bgcolor")).isEqualTo("blue");
    assertThat(applet.getParameter("color")).isEqualTo("red");
  }
}
